package com.jaeheonshim.ersgame.net.listener;

import com.badlogic.gdx.Gdx;
import com.github.czyzby.websocket.WebSocket;
import com.jaeheonshim.ersgame.game.GameStateManager;
import com.jaeheonshim.ersgame.net.model.GameAction;
import com.jaeheonshim.ersgame.net.model.UIMessageType;
import com.jaeheonshim.ersgame.net.packet.GameActionPacket;
import com.jaeheonshim.ersgame.net.packet.SocketPacket;
import com.jaeheonshim.ersgame.scene.ui.OverlayStage;

public class GameActionPacketListener extends SocketPacketListener {
    @Override
    public boolean receive(WebSocket socket, SocketPacket packet) {
        if(packet instanceof GameActionPacket) {
            Gdx.app.postRunnable(() -> {
                GameActionPacket gameActionPacket = ((GameActionPacket) packet);
                GameStateManager.getInstance().onGameAction(gameActionPacket.gameAction);
                if(gameActionPacket.gameAction == GameAction.START) {
                    OverlayStage.getInstance().onMessageUpdate(UIMessageType.SUCCESS, "Game is starting!");
                }
            });
        }

        return false;
    }
}
