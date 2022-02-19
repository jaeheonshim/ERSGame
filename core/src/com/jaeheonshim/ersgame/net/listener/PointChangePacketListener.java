package com.jaeheonshim.ersgame.net.listener;

import com.badlogic.gdx.Gdx;
import com.github.czyzby.websocket.WebSocket;
import com.jaeheonshim.ersgame.game.GameStateManager;
import com.jaeheonshim.ersgame.net.packet.PointChangePacket;
import com.jaeheonshim.ersgame.net.packet.SocketPacket;

public class PointChangePacketListener extends SocketPacketListener {
    @Override
    public boolean receive(WebSocket socket, SocketPacket packet) {
        if(packet instanceof PointChangePacket) {
            Gdx.app.postRunnable(() -> {
                PointChangePacket pointChangePacket = ((PointChangePacket) packet);
                GameStateManager.getInstance().onPointUpdate(pointChangePacket.uuid, pointChangePacket.amount);
            });
        }
        return false;
    }
}
