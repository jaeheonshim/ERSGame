package com.jaeheonshim.ersgame.game;

public class Player {
    private GameState gameState;

    private String uuid;
    private String nickname;

    public void setGameState(GameState gameState) {
        this.gameState = gameState;
    }
}
