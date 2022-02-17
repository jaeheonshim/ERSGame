package com.jaeheonshim.ersgame.net.packet;

public class JoinGamePacket extends SocketPacket {
    public String joinCode;
    public String username;

    public JoinGamePacket() {
    }

    public JoinGamePacket(String joinCode, String username) {
        this.joinCode = joinCode;
        this.username = username;
    }
}
