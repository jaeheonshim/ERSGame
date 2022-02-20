package com.jaeheonshim.ersgame.net.packet;

import com.jaeheonshim.ersgame.game.model.GameState;

public class GameStatePacket extends SocketPacket {
    public GameState gameState;

    public GameStatePacket() {
    }

    public GameStatePacket(GameState gameState) {
        this.gameState = gameState;
    }
}
