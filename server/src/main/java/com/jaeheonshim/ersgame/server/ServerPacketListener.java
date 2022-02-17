package com.jaeheonshim.ersgame.server;

import com.jaeheonshim.ersgame.net.packet.SocketPacket;
import org.java_websocket.WebSocket;

public abstract class ServerPacketListener {
    public boolean receive(WebSocket socket, SocketPacket packet) {
        return false;
    }
}
