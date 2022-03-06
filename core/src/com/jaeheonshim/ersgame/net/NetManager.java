package com.jaeheonshim.ersgame.net;

import com.github.czyzby.websocket.WebSocket;
import com.jaeheonshim.ersgame.net.listener.*;
import com.jaeheonshim.ersgame.net.model.ConnectionStatus;
import com.jaeheonshim.ersgame.net.packet.SocketPacket;

import java.util.ArrayList;
import java.util.List;

public class NetManager {
    private NetClient client;

    private List<SocketPacketListener> socketPacketListenerList = new ArrayList<>();
    private ConnectStatusListener connectStatusListener;

    private ConnectionStatus connectionStatus = ConnectionStatus.DISCONNECTED;
    private String clientUuid;
    private static NetManager instance;

    public static NetManager getInstance() {
        return instance;
    }

    public static void initialize(String connectionUrl) {
        instance = new NetManager(connectionUrl);

        instance.registerListener(new ConnectPacketListener());
        instance.registerListener(new UIMessagePacketListener());
        instance.registerListener(new GameStatePacketListener());
        instance.registerListener(new OverlayMessageListener());
        instance.registerListener(new GameActionPacketListener());
        instance.registerListener(new PointChangePacketListener());
        instance.registerListener(new SlapTimeoutPacketListener());
    }

    private NetManager(String connectionUrl) {
        client = new NetClient(this, connectionUrl);
    }

    public void connect() {
        if(connectionStatus != ConnectionStatus.CONNECTED) {
            connectionStatus = ConnectionStatus.CONNECTING;
            client.connect();
        }
    }

    public void disconnect() {
        client.disconnect();
    }

    public void onMessage(WebSocket socket, String s) {
        SocketPacket deserialized = SocketPacket.deserialize(s);
        for(SocketPacketListener listener : socketPacketListenerList) {
            if(listener.receive(socket, deserialized)) break;
        }
    }

    public void send(SocketPacket socketPacket) {
        client.sendMessage(socketPacket.serialize());
    }

    public void setConnectionStatus(ConnectionStatus connectionStatus) {
        this.connectionStatus = connectionStatus;

        if(connectStatusListener != null)
            connectStatusListener.onStatusChange(connectionStatus);
    }

    public void registerListener(SocketPacketListener listener) {
        socketPacketListenerList.add(listener);
    }

    public void setConnectStatusListener(ConnectStatusListener connectStatusListener) {
        this.connectStatusListener = connectStatusListener;
    }

    public ConnectionStatus getConnectionStatus() {
        return connectionStatus;
    }

    public String getClientUuid() {
        return clientUuid;
    }

    public void setClientUuid(String clientUuid) {
        this.clientUuid = clientUuid;
    }

    public long getRoundTripTime() {
        return client.getRoundTripTime();
    }

    public void initConnectInterval() {
        client.initConnectInterval();
    }

    public boolean isProductionServer() {
        return client.getHost().equalsIgnoreCase("wss://ers.jaeheonshim.dev:8080");
    }
}
