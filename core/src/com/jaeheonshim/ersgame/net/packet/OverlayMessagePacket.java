package com.jaeheonshim.ersgame.net.packet;

public class OverlayMessagePacket extends SocketPacket {
    public String message;

    public OverlayMessagePacket() {
    }

    public OverlayMessagePacket(String message) {
        this.message = message;
    }
}
