package com.jaeheonshim.ersgame.net.listener;

import com.jaeheonshim.ersgame.net.NetManager;
import com.jaeheonshim.ersgame.net.packet.SocketConnectPacket;
import com.jaeheonshim.ersgame.net.packet.SocketPacket;
import org.java_websocket.WebSocket;

public abstract class SocketPacketListener {
    public boolean receive(WebSocket socket, SocketPacket packet) {
        return false;
    }
}
