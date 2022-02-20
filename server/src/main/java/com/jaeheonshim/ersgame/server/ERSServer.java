package com.jaeheonshim.ersgame.server;

import com.jaeheonshim.ersgame.game.util.GameStateUtil;
import com.jaeheonshim.ersgame.server.cli.CommandParser;
import com.jaeheonshim.ersgame.server.listener.*;
import com.jaeheonshim.ersgame.util.ERSException;
import com.jaeheonshim.ersgame.game.model.GameState;
import com.jaeheonshim.ersgame.game.model.Player;
import com.jaeheonshim.ersgame.net.model.UIMessageType;
import com.jaeheonshim.ersgame.net.packet.*;
import com.jaeheonshim.ersgame.server.action.ScheduleGameAction;
import org.java_websocket.WebSocket;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.server.WebSocketServer;

import java.net.InetSocketAddress;
import java.util.*;
import java.util.concurrent.*;

public class ERSServer extends WebSocketServer {
    private final ConcurrentHashMap<String, WebSocket> connectedClients = new ConcurrentHashMap<>();
    private List<ServerPacketListener> socketPacketListenerList = new ArrayList<>();

    private ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();

    private CommandParser parser = new CommandParser(this);

    public ERSServer(InetSocketAddress address) {
        super(address);
        setupCommandThread();
    }

    @Override
    public void onOpen(WebSocket conn, ClientHandshake handshake) {
        UUID clientUUID = UUID.randomUUID();
        connectedClients.put(clientUUID.toString(), conn);
        conn.setAttachment(clientUUID.toString());

        System.out.printf("%s connected. Server handling %d clients%n", ((String) conn.getAttachment()), connectedClients.size());

        conn.send(new SocketConnectPacket(clientUUID.toString()).serialize());
        conn.send(new UIMessagePacket(UIMessageType.SUCCESS, "Connected to server!").serialize());
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
        if(message.equals("PING")) {
            conn.send("PONG");
            return;
        }

        System.out.println("PACKET RECEIVE");
        try {
            SocketPacket deserialized = SocketPacket.deserialize(message);
            for (ServerPacketListener listener : socketPacketListenerList) {
                if (listener.receive(conn, deserialized)) break;
            }
        } catch (ERSException e) {
            conn.send(new UIMessagePacket(UIMessageType.ERROR, e.getMessage()).serialize());
        } catch (Exception e) {
            e.printStackTrace();
            conn.send(new UIMessagePacket(UIMessageType.ERROR, "A server error occurred").serialize());
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
            client.send(packet.serialize());
        }
    }

    public void broadcast(SocketPacket packet, GameState game) {
        for(String uuid : game.getPlayerList()) {
            WebSocket socket = connectedClients.get(uuid);

            if(socket != null) {
                socket.send(packet.serialize());
            }
        }
    }

    public void broadcastExcept(SocketPacket packet, GameState game, String except) {
        for(String uuid : game.getPlayerList()) {
            if(uuid.equals(except)) continue;
            WebSocket socket = connectedClients.get(uuid);

            if(socket != null) {
                socket.send(packet.serialize());
            }
        }
    }

    public ScheduledExecutorService getScheduler() {
        return scheduler;
    }

    public static void main(String[] args) {
        ERSServer server = new ERSServer(new InetSocketAddress("10.0.0.101", 8887));
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
}
