package com.jaeheonshim.ersgame.server;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        ERSServer server = new ERSServer();
        server.start();
    }
}
