package com.jaeheonshim.ersgame.net;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.TimeUtils;
import com.badlogic.gdx.utils.Timer;
import com.github.czyzby.websocket.WebSocket;
import com.github.czyzby.websocket.WebSocketListener;
import com.github.czyzby.websocket.WebSockets;
import com.jaeheonshim.ersgame.net.model.ConnectionStatus;
import com.jaeheonshim.ersgame.net.util.PacketObfuscator;

public class NetClient implements WebSocketListener {
    public static final int PING_INTERVAL = 3;
    private NetManager netManager;
    private WebSocket socket;

    private long roundTripTime;
    private long lastPingTime;

    private Timer connectInterval;
    private int connectionTries = 0;
    private static final int MAX_CONNECTIONTRIES = 10;

    public NetClient(NetManager netManager, String connectionUrl) {
        this.netManager = netManager;

        socket = WebSockets.newSocket(connectionUrl);
        socket.setSendGracefully(true);
        socket.addListener(this);
        socket.setUseTcpNoDelay(true);
    }

    public void initConnectInterval( ){
        if(connectInterval == null) {
            connectInterval = new Timer();
            connectInterval.scheduleTask(new Timer.Task() {
                @Override
                public void run() {
                    if(socket.isClosed()) {
                        if(connectionTries <= MAX_CONNECTIONTRIES) {
                            Gdx.app.log("NET", "Attempting to reconnect...");
                            connect();
                            connectionTries++;
                        }
                    }
                }
            }, 0, 5);

            connectInterval.start();
        }
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
        Gdx.app.log("NET", "Connected!");
        netManager.setConnectionStatus(ConnectionStatus.CONNECTED);
        connectInterval.stop();
        connectionTries = 0;

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
        Gdx.app.log("NET", "Disconnected");
        netManager.setConnectionStatus(ConnectionStatus.DISCONNECTED);
        connectInterval.start();
        return true;
    }

    @Override
    public boolean onMessage(WebSocket webSocket, String packet) {
        packet = PacketObfuscator.applyMask(packet);
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
        error.printStackTrace();
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
        packet = PacketObfuscator.applyMask(packet);

        socket.send(packet);
    }

    public String getHost() {
        return socket.getUrl();
    }
}
