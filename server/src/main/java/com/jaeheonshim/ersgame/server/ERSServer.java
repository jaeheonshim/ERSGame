package com.jaeheonshim.ersgame.server;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;
import com.jaeheonshim.ersgame.game.GameState;
import com.jaeheonshim.ersgame.net.NetUtil;

import java.io.IOException;

public class ERSServer {
    private Server server;

    public ERSServer() {
        server = new Server();
        server.addListener(new JoinGameListener());
        NetUtil.register(server.getKryo());

        GamesManager.initialize(this);
    }

    public void start() throws IOException {
        server.start();
        server.bind(54555, 54777);
        GameState newGame = GamesManager.getInstance().newGame();
        System.out.println(newGame.getJoinCode());
    }

    public Server getServer() {
        return server;
    }
}
