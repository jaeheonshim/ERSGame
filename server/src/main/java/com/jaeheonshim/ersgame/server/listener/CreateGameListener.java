package com.jaeheonshim.ersgame.server.listener;

import com.jaeheonshim.ersgame.net.packet.CreateGamePacket;
import com.jaeheonshim.ersgame.net.packet.SocketPacket;
import com.jaeheonshim.ersgame.server.ServerPacketListener;
import org.java_websocket.WebSocket;

public class CreateGameListener extends ServerPacketListener {
    @Override
    public boolean receive(WebSocket socket, SocketPacket packet) {
        if(packet instanceof CreateGamePacket) {
            CreateGamePacket createGamePacket = ((CreateGamePacket) packet);
            System.out.println(createGamePacket.username);
            return true;
        }

        return false;
    }
}
