package com.jaeheonshim.ersgame.net;

import com.esotericsoftware.kryonet.Client;
import com.jaeheonshim.ersgame.net.listener.ConnectPacketListener;
import com.jaeheonshim.ersgame.net.listener.GameEventListener;
import com.jaeheonshim.ersgame.net.listener.JoinGameListener;
import com.jaeheonshim.ersgame.net.listener.SocketPacketListener;
import com.jaeheonshim.ersgame.net.packet.JoinGameRequest;
import com.jaeheonshim.ersgame.net.packet.SocketPacket;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

public class NetManager {
    private NetClient client;
    private List<SocketPacketListener> socketPacketListenerList = new ArrayList<>();

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

    public void registerListener(SocketPacketListener listener) {
        socketPacketListenerList.add(listener);
    }

    public void onMessage(String s) {
        SocketPacket deserialized = SocketPacket.deserialize(s);
        for(SocketPacketListener listener : socketPacketListenerList) {
            if(listener.receive(deserialized)) break;
        }
    }

    public ConnectionStatus getConnectionStatus() {
        return connectionStatus;
    }

    public void setConnectionStatus(ConnectionStatus connectionStatus) {
        this.connectionStatus = connectionStatus;
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
