package com.jaeheonshim.ersgame.server;

import com.jaeheonshim.ersgame.game.model.GameState;
import com.jaeheonshim.ersgame.game.model.Player;
import com.jaeheonshim.ersgame.game.util.GameStateUtil;
import com.jaeheonshim.ersgame.net.model.UIMessageType;
import com.jaeheonshim.ersgame.net.packet.*;
import com.jaeheonshim.ersgame.net.util.PacketObfuscator;
import com.jaeheonshim.ersgame.server.cli.CommandParser;
import com.jaeheonshim.ersgame.server.listener.*;
import com.jaeheonshim.ersgame.util.ERSException;
import org.apache.commons.cli.*;
import org.java_websocket.WebSocket;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.server.DefaultSSLWebSocketServerFactory;
import org.java_websocket.server.WebSocketServer;

import javax.net.ssl.KeyManager;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.xml.bind.DatatypeConverter;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.file.Files;
import java.security.*;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.security.interfaces.RSAPrivateKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ERSServer extends WebSocketServer {
    private final ConcurrentHashMap<String, WebSocket> connectedClients = new ConcurrentHashMap<>();
    private List<ServerPacketListener> socketPacketListenerList = new ArrayList<>();

    private ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();

    private CommandParser parser = new CommandParser(this);

    public ERSServer(InetSocketAddress address) {
        super(address);
        setTcpNoDelay(true);
        setupCommandThread();
    }

    @Override
    public void onOpen(WebSocket conn, ClientHandshake handshake) {
        UUID clientUUID = UUID.randomUUID();
        connectedClients.put(clientUUID.toString(), conn);
        conn.setAttachment(clientUUID.toString());

        System.out.printf("%s connected. Server handling %d clients%n", ((String) conn.getAttachment()), connectedClients.size());

        conn.send(PacketObfuscator.applyMask(new SocketConnectPacket(clientUUID.toString()).serialize()));
        conn.send(PacketObfuscator.applyMask(new UIMessagePacket(UIMessageType.SUCCESS, "Connected to server!").serialize()));
    }

    @Override
    public void onClose(WebSocket conn, int code, String reason, boolean remote) {
        System.out.printf("%s disconnected.%n", ((String) conn.getAttachment()));

        GameState gameState = GameManager.getInstance().getGameOfPlayer(conn.getAttachment());
        if(gameState != null) {
            Player player = gameState.getPlayer(conn.getAttachment());
            boolean isEmpty = GameStateUtil.removePlayer(gameState, conn.getAttachment());

            if(isEmpty) {
                GameManager.getInstance().removeGame(gameState.getGameCode());
                return;
            }

            OverlayMessagePacket messagePacket = new OverlayMessagePacket(player.getUsername() + " left");
            GameStatePacket gameStatePacket = new GameStatePacket(gameState);

            broadcast(messagePacket, gameState);
            broadcast(gameStatePacket, gameState);
        }

        connectedClients.remove(((String) conn.getAttachment()));
    }

    @Override
    public void onMessage(WebSocket conn, String message) {
        message = PacketObfuscator.applyMask(message);

        if(message.equals("PING")) {
            conn.send(PacketObfuscator.applyMask("PONG"));
            return;
        }

        try {
            SocketPacket deserialized = SocketPacket.deserialize(message);
            for (ServerPacketListener listener : socketPacketListenerList) {
                if (listener.receive(conn, deserialized)) break;
            }
        } catch (ERSException e) {
            conn.send(PacketObfuscator.applyMask(new UIMessagePacket(UIMessageType.ERROR, e.getMessage()).serialize()));
        } catch (Exception e) {
            e.printStackTrace();
            conn.send(PacketObfuscator.applyMask(new UIMessagePacket(UIMessageType.ERROR, "A server error occurred").serialize()));
        }
    }

    @Override
    public void onError(WebSocket conn, Exception ex) {
        ex.printStackTrace();
    }

    @Override
    public void onStart() {
        scheduler.scheduleAtFixedRate(GameManager.getInstance(), 0, 250, TimeUnit.MILLISECONDS);
    }

    public void registerListener(ServerPacketListener listener) {
        socketPacketListenerList.add(listener);
    }

    public void send(SocketPacket packet, String uuid) {
        WebSocket client = connectedClients.get(uuid);
        if(client != null) {
            client.send(PacketObfuscator.applyMask(packet.serialize()));
        }
    }

    public void broadcast(SocketPacket packet, GameState game) {
        String serialized = packet.serialize();
        serialized = PacketObfuscator.applyMask(serialized);

        for(String uuid : game.getPlayerList()) {
            WebSocket socket = connectedClients.get(uuid);

            if(socket != null) {
                socket.send(serialized);
            }
        }
    }

    public void broadcastExcept(SocketPacket packet, GameState game, String except) {
        String serialized = packet.serialize();
        serialized = PacketObfuscator.applyMask(serialized);

        for(String uuid : game.getPlayerList()) {
            if(uuid.equals(except)) continue;
            WebSocket socket = connectedClients.get(uuid);

            if(socket != null) {
                socket.send(serialized);
            }
        }
    }

    public ScheduledExecutorService getScheduler() {
        return scheduler;
    }

    public static void main(String[] args) throws ParseException {
        Options options = new Options();
        options.addOption("p", "port",true, "server port");
        options.addOption("h", "hostname",true, "specify server hostname");
        options.addOption("c", "cert",true, "specify ssl cert path");

        CommandLineParser parser = new DefaultParser();
        CommandLine cmd = parser.parse(options, args);

        int port = 8887;
        String hostName = "localhost";

        if(cmd.hasOption("p")) {
            port = Integer.parseInt(cmd.getOptionValue("p"));
        }

        if(cmd.hasOption("h")) {
            hostName = cmd.getOptionValue("h").trim();
        }

        System.out.println("Running on " + hostName + ":" + port);

        ERSServer server = new ERSServer(new InetSocketAddress(hostName, port));

        if(cmd.hasOption("c")) {
            String pathTo = cmd.getOptionValue("c");
            SSLContext context = server.getSSLContextFromLetsEncrypt(pathTo);
            server.setWebSocketFactory(new DefaultSSLWebSocketServerFactory(context));
        }

        server.registerListener(new CreateGameListener(server));
        server.registerListener(new JoinGameListener(server));
        server.registerListener(new StartGameListener(server));
        server.registerListener(new CardActionListener(server));
        server.registerListener(new LeaveGameListener(server));
        server.run();
    }

    private void setupCommandThread() {
        Scanner scanner = new Scanner(System.in);
        Thread thread = new Thread(() -> {
           while(scanner.hasNextLine()) {
               String input = scanner.nextLine();
               String s = parser.getCommandResult(input);
               System.out.print(s);
           }
        });

        thread.start();
    }

    private SSLContext getSSLContextFromLetsEncrypt(String pathTo) {
        SSLContext context;
        String keyPassword = "";

        try {
            context = SSLContext.getInstance("TLS");

            byte[] certBytes = parseDERFromPEM(Files.readAllBytes(new File(pathTo + File.separator + "fullchain.pem").toPath()), "-----BEGIN CERTIFICATE-----", "-----END CERTIFICATE-----");
            byte[] keyBytes = parseDERFromPEM(Files.readAllBytes(new File(pathTo + File.separator + "privkey.pem").toPath()), "-----BEGIN PRIVATE KEY-----", "-----END PRIVATE KEY-----");

            X509Certificate cert = generateCertificateFromDER(certBytes);
            RSAPrivateKey key = generatePrivateKeyFromDER(keyBytes);

            KeyStore keystore = KeyStore.getInstance("JKS");
            keystore.load(null);
            keystore.setCertificateEntry("cert-alias", cert);
            keystore.setKeyEntry("key-alias", key, keyPassword.toCharArray(), new Certificate[]{cert});

            KeyManagerFactory kmf = KeyManagerFactory.getInstance("SunX509");
            kmf.init(keystore, keyPassword.toCharArray());

            KeyManager[] km = kmf.getKeyManagers();

            context.init(km, null, null);
        } catch (IOException | KeyManagementException | KeyStoreException | InvalidKeySpecException | UnrecoverableKeyException | NoSuchAlgorithmException | CertificateException e) {
            throw new IllegalArgumentException();
        }
        return context;
    }

    protected static byte[] parseDERFromPEM(byte[] pem, String beginDelimiter, String endDelimiter) {
        String data = new String(pem);
        String[] tokens = data.split(beginDelimiter);
        tokens = tokens[1].split(endDelimiter);
        return DatatypeConverter.parseBase64Binary(tokens[0]);
    }

    protected static RSAPrivateKey generatePrivateKeyFromDER(byte[] keyBytes) throws InvalidKeySpecException, NoSuchAlgorithmException {
        PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory factory = KeyFactory.getInstance("RSA");
        return (RSAPrivateKey) factory.generatePrivate(spec);
    }

    protected static X509Certificate generateCertificateFromDER(byte[] certBytes) throws CertificateException {
        CertificateFactory factory = CertificateFactory.getInstance("X.509");

        return (X509Certificate) factory.generateCertificate(new ByteArrayInputStream(certBytes));
    }
}
