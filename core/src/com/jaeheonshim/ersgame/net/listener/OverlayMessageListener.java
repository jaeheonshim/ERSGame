package com.jaeheonshim.ersgame.net.listener;

import com.github.czyzby.websocket.WebSocket;
import com.jaeheonshim.ersgame.net.packet.OverlayMessagePacket;
import com.jaeheonshim.ersgame.net.packet.SocketPacket;
import com.jaeheonshim.ersgame.scene.ui.OverlayStage;

public class OverlayMessageListener extends SocketPacketListener {
    @Override
    public boolean receive(WebSocket socket, SocketPacket packet) {
        if(packet instanceof OverlayMessagePacket) {
            OverlayMessagePacket overlayMessagePacket = ((OverlayMessagePacket) packet);
            postMessage(overlayMessagePacket.message);
            return true;
        }

        return false;
    }

    public synchronized void postMessage(String message) {
        OverlayStage.getInstance().postOverlayMessage(message);
    }
}
