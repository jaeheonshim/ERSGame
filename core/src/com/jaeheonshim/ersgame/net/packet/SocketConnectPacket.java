package com.jaeheonshim.ersgame.net.packet;

public class SocketConnectPacket extends SocketPacket {
    public String uuid;

    public SocketConnectPacket() {

    }

    public SocketConnectPacket(String uuid) {
        this.uuid = uuid;
    }
}
