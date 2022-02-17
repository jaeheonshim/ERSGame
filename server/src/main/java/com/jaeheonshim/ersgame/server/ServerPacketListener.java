package com.jaeheonshim.ersgame.server;

import com.jaeheonshim.ersgame.net.packet.SocketPacket;
import org.java_websocket.WebSocket;

public abstract class ServerPacketListener {
    protected ERSServer server;

    public ServerPacketListener(ERSServer server) {
        this.server = server;
    }

    public boolean receive(WebSocket socket, SocketPacket packet) {
        return false;
    }
}
