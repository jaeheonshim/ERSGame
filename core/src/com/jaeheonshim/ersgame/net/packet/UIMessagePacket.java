package com.jaeheonshim.ersgame.net.packet;

import com.jaeheonshim.ersgame.net.UIMessageType;

public class UIMessagePacket extends SocketPacket {
    public UIMessageType type;
    public String message;

    public UIMessagePacket() {
    }

    public UIMessagePacket(UIMessageType type, String message) {
        this.type = type;
        this.message = message;
    }
}
