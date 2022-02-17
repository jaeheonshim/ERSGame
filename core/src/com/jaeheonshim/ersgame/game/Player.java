package com.jaeheonshim.ersgame.game;

public class Player {
    private GameState gameState;

    private String uuid;
    private String username;

    public void setGameState(GameState gameState) {
        this.gameState = gameState;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
