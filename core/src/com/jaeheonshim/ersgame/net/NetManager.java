package com.jaeheonshim.ersgame.net;

import com.esotericsoftware.kryonet.Client;
import com.jaeheonshim.ersgame.net.listener.GameEventListener;
import com.jaeheonshim.ersgame.net.listener.JoinGameListener;
import com.jaeheonshim.ersgame.net.packet.JoinGameRequest;

import java.io.IOException;

public class NetManager {
    private Client client;

    private static NetManager instance = new NetManager();

    private NetManager() {
        client = new Client();
        client.addListener(new GameEventListener());
        client.addListener(new JoinGameListener());
        NetUtil.register(client.getKryo());
    }

    public void connect() {
        client.start();
        try {
            client.connect(5000, "localhost", 54555, 54777);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void join(String joinCode, String name) {
        JoinGameRequest request = new JoinGameRequest();
        request.gameCode = joinCode;
        request.username = name;

        client.sendTCP(request);
    }

    public static NetManager getInstance() {
        return instance;
    }
}
