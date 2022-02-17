package com.jaeheonshim.ersgame.net.listener;

import com.badlogic.gdx.Gdx;
import com.github.czyzby.websocket.WebSocket;
import com.jaeheonshim.ersgame.game.GameStateManager;
import com.jaeheonshim.ersgame.net.packet.GameStatePacket;
import com.jaeheonshim.ersgame.net.packet.SocketPacket;

public class GameStatePacketListener extends SocketPacketListener {
    @Override
    public boolean receive(WebSocket socket, SocketPacket packet) {
        if(packet instanceof GameStatePacket) {
            Gdx.app.postRunnable(() -> {
                GameStatePacket gameStatePacket = ((GameStatePacket) packet);
                GameStateManager.getInstance().update(gameStatePacket.gameState);
            });

            return true;
        }

        return false;
    }
}
