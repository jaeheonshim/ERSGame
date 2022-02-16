package com.jaeheonshim.ersgame.net;

import com.github.czyzby.websocket.WebSocket;
import com.github.czyzby.websocket.WebSocketListener;
import com.github.czyzby.websocket.WebSockets;
import java.net.InetSocketAddress;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.ByteBuffer;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class NetClient implements WebSocketListener {
    private NetManager netManager;
    private WebSocket socket;

    public NetClient(NetManager netManager) throws URISyntaxException {
        this.netManager = netManager;

        socket = WebSockets.newSocket(WebSockets.toWebSocketUrl("localhost", 8887));
        socket.setSendGracefully(true);
        socket.addListener(this);
    }

    public void connect() {
        netManager.setConnectionStatus(ConnectionStatus.CONNECTING);
        socket.connect();
    }

    @Override
    public boolean onOpen(WebSocket webSocket) {
        netManager.setConnectionStatus(ConnectionStatus.CONNECTED);
        return true;
    }

    @Override
    public boolean onClose(WebSocket webSocket, int closeCode, String reason) {
        netManager.setConnectionStatus(ConnectionStatus.DISCONNECTED);
        return true;
    }

    @Override
    public boolean onMessage(WebSocket webSocket, String packet) {
        netManager.onMessage(webSocket, packet);
        return true;
    }

    @Override
    public boolean onMessage(WebSocket webSocket, byte[] packet) {
        return false;
    }

    @Override
    public boolean onError(WebSocket webSocket, Throwable error) {
        netManager.setConnectionStatus(webSocket.isOpen() ? ConnectionStatus.CONNECTED : ConnectionStatus.DISCONNECTED);
        return true;
    }
}
