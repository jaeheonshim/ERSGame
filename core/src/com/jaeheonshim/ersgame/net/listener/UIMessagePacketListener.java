package com.jaeheonshim.ersgame.net.listener;

import com.badlogic.gdx.Gdx;
import com.github.czyzby.websocket.WebSocket;
import com.jaeheonshim.ersgame.net.packet.SocketPacket;
import com.jaeheonshim.ersgame.net.packet.UIMessagePacket;
import com.jaeheonshim.ersgame.scene.ui.OverlayStage;

public class UIMessagePacketListener extends SocketPacketListener {
    @Override
    public boolean receive(WebSocket socket, SocketPacket packet) {
        if(packet instanceof UIMessagePacket) {
            UIMessagePacket uiMessagePacket = ((UIMessagePacket) packet);

            Gdx.app.postRunnable(() -> {
                OverlayStage.getInstance().onMessageUpdate(uiMessagePacket.type, uiMessagePacket.message);
            });
        }

        return false;
    }
}
