package com.jaeheonshim.ersgame.net.packet;

public class PointChangePacket extends SocketPacket {
    public String uuid;
    public int amount;

    public PointChangePacket() {
    }

    public PointChangePacket(String uuid, int amount) {
        this.uuid = uuid;
        this.amount = amount;
    }
}
