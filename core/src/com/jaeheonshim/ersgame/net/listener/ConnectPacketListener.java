package com.jaeheonshim.ersgame.net.listener;

import com.badlogic.gdx.Gdx;
import com.github.czyzby.websocket.WebSocket;
import com.jaeheonshim.ersgame.net.ConnectionStatus;
import com.jaeheonshim.ersgame.net.NetManager;
import com.jaeheonshim.ersgame.net.packet.SocketConnectPacket;
import com.jaeheonshim.ersgame.net.packet.SocketPacket;

import java.util.logging.Logger;

public class ConnectPacketListener extends SocketPacketListener {
    @Override
    public boolean receive(WebSocket socket, SocketPacket packet) {
        if(packet instanceof SocketConnectPacket) {
            NetManager.getInstance().setConnectionStatus(ConnectionStatus.CONNECTED);
            NetManager.getInstance().setClientUuid(((SocketConnectPacket) packet).uuid);

            return true;
        }

        return false;
    }
}
