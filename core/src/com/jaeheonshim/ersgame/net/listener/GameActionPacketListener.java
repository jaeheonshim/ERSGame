package com.jaeheonshim.ersgame.net.listener;

import com.github.czyzby.websocket.WebSocket;
import com.jaeheonshim.ersgame.game.GameStateManager;
import com.jaeheonshim.ersgame.net.GameAction;
import com.jaeheonshim.ersgame.net.UIMessageType;
import com.jaeheonshim.ersgame.net.packet.GameActionPacket;
import com.jaeheonshim.ersgame.net.packet.SocketPacket;
import com.jaeheonshim.ersgame.scene.OverlayStage;

public class GameActionPacketListener extends SocketPacketListener {
    @Override
    public boolean receive(WebSocket socket, SocketPacket packet) {
        if(packet instanceof GameActionPacket) {
            GameActionPacket gameActionPacket = ((GameActionPacket) packet);
            GameStateManager.getInstance().onGameAction(gameActionPacket.gameAction);
            if(gameActionPacket.gameAction == GameAction.START) {
                OverlayStage.getInstance().onMessageUpdate(UIMessageType.SUCCESS, "Game is starting!");
            }
        }

        return false;
    }
}
