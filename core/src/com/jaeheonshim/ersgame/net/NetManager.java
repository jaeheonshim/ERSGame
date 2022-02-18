package com.jaeheonshim.ersgame.net;

import com.github.czyzby.websocket.WebSocket;
import com.jaeheonshim.ersgame.net.listener.*;
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

    public static void initialize() {
        instance = new NetManager();

        instance.registerListener(new ConnectPacketListener());
        instance.registerListener(new UIMessagePacketListener());
        instance.registerListener(new GameStatePacketListener());
        instance.registerListener(new OverlayMessageListener());
        instance.registerListener(new GameActionListener());
    }

    private NetManager() {
        client = new NetClient(this);
    }

    public void connect() {
        connectionStatus = ConnectionStatus.CONNECTING;
        client.connect();
    }

    public void reconnect() {
        connectionStatus = ConnectionStatus.CONNECTING;
        client.connect();
    }

    public void registerListener(SocketPacketListener listener) {
        socketPacketListenerList.add(listener);
    }

    public void setConnectStatusListener(ConnectStatusListener connectStatusListener) {
        this.connectStatusListener = connectStatusListener;
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

    public ConnectionStatus getConnectionStatus() {
        return connectionStatus;
    }

    public void setConnectionStatus(ConnectionStatus connectionStatus) {
        this.connectionStatus = connectionStatus;

        if(connectStatusListener != null)
            connectStatusListener.onStatusChange(connectionStatus);
    }

    public String getClientUuid() {
        return clientUuid;
    }

    public void setClientUuid(String clientUuid) {
        this.clientUuid = clientUuid;
    }
}
