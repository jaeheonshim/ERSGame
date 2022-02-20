package com.jaeheonshim.ersgame.net.packet;

public class SlapTimeoutPacket extends SocketPacket {
    public float time;

    public SlapTimeoutPacket() {
    }

    public SlapTimeoutPacket(float time) {
        this.time = time;
    }
}
