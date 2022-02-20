package com.jaeheonshim.ersgame.net.packet;

import com.jaeheonshim.ersgame.net.model.GameAction;

public class GameActionPacket extends SocketPacket {
    public GameAction gameAction;

    public GameActionPacket() {
    }

    public GameActionPacket(GameAction gameAction) {
        this.gameAction = gameAction;
    }
}
