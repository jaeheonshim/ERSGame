package com.jaeheonshim.ersgame.server;

import com.jaeheonshim.ersgame.game.GameState;
import com.jaeheonshim.ersgame.game.Player;
import com.jaeheonshim.ersgame.net.UIMessageType;
import com.jaeheonshim.ersgame.net.listener.SocketPacketListener;
import com.jaeheonshim.ersgame.net.packet.*;
import com.jaeheonshim.ersgame.server.listener.CreateGameListener;
import com.jaeheonshim.ersgame.server.listener.JoinGameListener;
import com.jaeheonshim.ersgame.server.listener.StartGameListener;
import org.java_websocket.WebSocket;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.server.WebSocketServer;

import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class ERSServer extends WebSocketServer {
    private final ConcurrentHashMap<String, WebSocket> connectedClients = new ConcurrentHashMap<>();
    private List<ServerPacketListener> socketPacketListenerList = new ArrayList<>();

    public ERSServer(InetSocketAddress address) {
        super(address);
    }

    @Override
    public void onOpen(WebSocket conn, ClientHandshake handshake) {
        UUID clientUUID = UUID.randomUUID();
        connectedClients.put(clientUUID.toString(), conn);
        conn.setAttachment(clientUUID.toString());

        conn.send(new SocketConnectPacket(clientUUID.toString()).serialize());
        conn.send(new UIMessagePacket(UIMessageType.SUCCESS, "Connected to server!").serialize());
    }

    @Override
    public void onClose(WebSocket conn, int code, String reason, boolean remote) {
        System.out.printf("%s disconnected.%n", ((String) conn.getAttachment()));

        GameState gameState = GameManager.getInstance().getGameOfPlayer(conn.getAttachment());
        if(gameState != null) {
            Player player = gameState.getPlayer(conn.getAttachment());
            gameState.removePlayer(conn.getAttachment());

            if(gameState.getPlayerList().size == 0) {
                GameManager.getInstance().removeGame(gameState.getGameCode());
                return;
            }

            if(gameState.getAdminPlayer() == player) {
                gameState.setAdminPlayer(gameState.getPlayerList().get(0));
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
        try {
            SocketPacket deserialized = SocketPacket.deserialize(message);
            for (ServerPacketListener listener : socketPacketListenerList) {
                if (listener.receive(conn, deserialized)) break;
            }
        } catch (ERSException e) {
            conn.send(new UIMessagePacket(UIMessageType.ERROR, e.getMessage()).serialize());
        }
    }

    @Override
    public void onError(WebSocket conn, Exception ex) {
        ex.printStackTrace();
    }

    @Override
    public void onStart() {

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
        for(Player player : game.getPlayerList()) {
            WebSocket socket = connectedClients.get(player.getUuid());

            if(socket != null) {
                socket.send(packet.serialize());
            }
        }
    }

    public static void main(String[] args) {
        ERSServer server = new ERSServer(new InetSocketAddress("localhost", 8887));
        server.registerListener(new CreateGameListener(server));
        server.registerListener(new JoinGameListener(server));
        server.registerListener(new StartGameListener(server));
        server.run();
    }
}
