package com.jaeheonshim.ersgame.net;

import com.badlogic.gdx.utils.TimeUtils;
import com.badlogic.gdx.utils.Timer;
import com.github.czyzby.websocket.WebSocket;
import com.github.czyzby.websocket.WebSocketListener;
import com.github.czyzby.websocket.WebSockets;
import com.jaeheonshim.ersgame.net.model.ConnectionStatus;

public class NetClient implements WebSocketListener {
    public static final int PING_INTERVAL = 3;
    private NetManager netManager;
    private WebSocket socket;

    private long roundTripTime;
    private long lastPingTime;

    public NetClient(NetManager netManager) {
        this.netManager = netManager;

        socket = WebSockets.newSocket(WebSockets.toWebSocketUrl("10.0.0.101", 8887));
        socket.setSendGracefully(true);
        socket.addListener(this);
    }

    public void connect() {
        netManager.setConnectionStatus(ConnectionStatus.CONNECTING);
        socket.connect();
    }

    public void disconnect() {
        socket.close();
    }

    @Override
    public boolean onOpen(WebSocket webSocket) {
        netManager.setConnectionStatus(ConnectionStatus.CONNECTED);

        Timer timer = new Timer();
        timer.scheduleTask(new Timer.Task() {
            @Override
            public void run() {
                if(socket.isOpen()) {
                    ping();
                }
            }
        }, 0, PING_INTERVAL);

        return true;
    }

    @Override
    public boolean onClose(WebSocket webSocket, int closeCode, String reason) {
        netManager.setConnectionStatus(ConnectionStatus.DISCONNECTED);
        return true;
    }

    @Override
    public boolean onMessage(WebSocket webSocket, String packet) {
        if(packet.equals("PONG")) {
            onPong();
            return true;
        }

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

    public void ping() {
        lastPingTime = TimeUtils.millis();
        sendMessage("PING");
    }

    public void onPong() {
        long currentTime = TimeUtils.millis();
        this.roundTripTime = currentTime - lastPingTime;
    }

    public long getRoundTripTime() {
        return roundTripTime;
    }

    public void sendMessage(String packet) {
        socket.send(packet);
    }
}
