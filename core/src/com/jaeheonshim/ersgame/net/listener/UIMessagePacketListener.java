package com.jaeheonshim.ersgame.net.listener;

import com.github.czyzby.websocket.WebSocket;
import com.jaeheonshim.ersgame.net.packet.SocketPacket;
import com.jaeheonshim.ersgame.net.packet.UIMessagePacket;
import com.jaeheonshim.ersgame.scene.OverlayStage;
import com.jaeheonshim.ersgame.scene.ui.UIMessageLabel;

public class UIMessagePacketListener extends SocketPacketListener {
    @Override
    public boolean receive(WebSocket socket, SocketPacket packet) {
        if(packet instanceof UIMessagePacket) {
            UIMessagePacket uiMessagePacket = ((UIMessagePacket) packet);

            OverlayStage.getInstance().onMessageUpdate(uiMessagePacket.message);
        }

        return false;
    }
}
