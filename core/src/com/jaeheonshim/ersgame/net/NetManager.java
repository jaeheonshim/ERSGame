package com.jaeheonshim.ersgame.net;

import com.esotericsoftware.kryonet.Client;
import com.jaeheonshim.ersgame.net.listener.*;
import com.jaeheonshim.ersgame.net.packet.JoinGameRequest;
import com.jaeheonshim.ersgame.net.packet.SocketPacket;
import org.java_websocket.WebSocket;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

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

    public static void initialize() throws URISyntaxException {
        instance = new NetManager();

        instance.registerListener(new ConnectPacketListener());
    }

    private NetManager() throws URISyntaxException {
        client = new NetClient(this);
    }

    public void connect() {
        connectionStatus = ConnectionStatus.CONNECTING;
        client.connect();
    }

    public void reconnect() {
        connectionStatus = ConnectionStatus.CONNECTING;
        client.reconnect();
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

    public static void main(String[] args) throws URISyntaxException {
        NetManager.initialize();
        NetManager.getInstance().connect();
    }
}
