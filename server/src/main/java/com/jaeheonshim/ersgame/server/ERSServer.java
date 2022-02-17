package com.jaeheonshim.ersgame.server;

import com.jaeheonshim.ersgame.net.listener.SocketPacketListener;
import com.jaeheonshim.ersgame.net.packet.SocketConnectPacket;
import com.jaeheonshim.ersgame.net.packet.SocketPacket;
import com.jaeheonshim.ersgame.server.listener.CreateGameListener;
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
    }

    @Override
    public void onClose(WebSocket conn, int code, String reason, boolean remote) {
        System.out.printf("%s disconnected.%n", ((String) conn.getAttachment()));
        connectedClients.remove(((String) conn.getAttachment()));
    }

    @Override
    public void onMessage(WebSocket conn, String message) {
        SocketPacket deserialized = SocketPacket.deserialize(message);
        for(ServerPacketListener listener : socketPacketListenerList) {
            if(listener.receive(conn, deserialized)) break;
        }
    }

    @Override
    public void onError(WebSocket conn, Exception ex) {

    }

    @Override
    public void onStart() {

    }

    public void registerListener(ServerPacketListener listener) {
        socketPacketListenerList.add(listener);
    }

    public static void main(String[] args) {
        ERSServer server = new ERSServer(new InetSocketAddress("localhost", 8887));
        server.registerListener(new CreateGameListener());
        server.run();
    }
}
