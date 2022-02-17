package com.jaeheonshim.ersgame.net.packet;

public class CreateGamePacket extends SocketPacket {
    public String username;

    public CreateGamePacket() {
    }

    public CreateGamePacket(String username) {
        this.username = username;
    }
}