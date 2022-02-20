package com.jaeheonshim.ersgame.net.listener;

import com.badlogic.gdx.Gdx;
import com.github.czyzby.websocket.WebSocket;
import com.jaeheonshim.ersgame.game.GameStateManager;
import com.jaeheonshim.ersgame.net.packet.SlapTimeoutPacket;
import com.jaeheonshim.ersgame.net.packet.SocketPacket;

public class SlapTimeoutPacketListener extends SocketPacketListener {
    @Override
    public boolean receive(WebSocket socket, SocketPacket packet) {
        if(packet instanceof SlapTimeoutPacket) {
            SlapTimeoutPacket slapTimeoutPacket = ((SlapTimeoutPacket) packet);

            Gdx.app.postRunnable(() -> {
                GameStateManager.getInstance().onGameTimeout(slapTimeoutPacket.time);
            });

            return true;
        }

        return false;
    }
}
