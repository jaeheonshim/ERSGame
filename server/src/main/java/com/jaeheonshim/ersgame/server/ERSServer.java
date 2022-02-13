package com.jaeheonshim.ersgame.server;

import com.esotericsoftware.kryonet.Server;

import java.io.IOException;

public class ERSServer {
    private Server server;

    public ERSServer() {
        server = new Server();
    }

    public void start() throws IOException {
        server.start();
        server.bind(54555, 54777);
    }
}
