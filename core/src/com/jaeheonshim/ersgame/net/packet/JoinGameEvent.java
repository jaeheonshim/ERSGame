package com.jaeheonshim.ersgame.net.packet;

public class JoinGameEvent {
    public String uuid;
    public String name;
    public int ordinal;

    public JoinGameEvent() {

    }

    public JoinGameEvent(String uuid, String name, int ordinal) {
        this.uuid = uuid;
        this.name = name;
        this.ordinal = ordinal;
    }
}
