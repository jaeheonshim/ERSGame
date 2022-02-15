package com.jaeheonshim.ersgame.net;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;
import java.net.URISyntaxException;

public class NetClient extends WebSocketClient {
    private NetManager netManager;

    public NetClient(NetManager netManager) throws URISyntaxException {
        super(new URI("ws://localhost:8887"));
        this.netManager = netManager;
    }

    @Override
    public void onOpen(ServerHandshake handshakedata) {

    }

    @Override
    public void onMessage(String message) {
        netManager.onMessage(message);
    }

    @Override
    public void onClose(int code, String reason, boolean remote) {

    }

    @Override
    public void onError(Exception ex) {

    }
}
