package com.jaeheonshim.ersgame.net.listener;

import com.badlogic.gdx.Gdx;
import com.jaeheonshim.ersgame.net.ConnectionStatus;
import com.jaeheonshim.ersgame.net.NetManager;
import com.jaeheonshim.ersgame.net.packet.SocketConnectPacket;
import com.jaeheonshim.ersgame.net.packet.SocketPacket;

import java.util.logging.Logger;

public class ConnectPacketListener extends SocketPacketListener {
    @Override
    public boolean receive(SocketPacket packet) {
        if(packet instanceof SocketConnectPacket) {
            NetManager.getInstance().setConnectionStatus(ConnectionStatus.CONNECTING);
            NetManager.getInstance().setClientUuid(((SocketConnectPacket) packet).uuid);

            Gdx.app.log("NET", String.format("Connected to server! UUID: %s", NetManager.getInstance().getClientUuid()));

            return true;
        }

        return false;
    }
}
