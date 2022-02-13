package com.jaeheonshim.ersgame.game;

public class GameStateManager {
    private GameState gameState;

    private static GameStateManager manager = new GameStateManager();

    private GameStateManager() {
        gameState = new GameState();

        gameState.addNewPlayer(Player.createNew("Bob"));
        gameState.addNewPlayer(Player.createNew("John"));
        gameState.addNewPlayer(Player.createNew("Cathy"));
        gameState.addNewPlayer(Player.createNew("Gerald"));gameState.addNewPlayer(Player.createNew("Bob"));
        gameState.addNewPlayer(Player.createNew("John"));
        gameState.addNewPlayer(Player.createNew("Cathy"));
        gameState.addNewPlayer(Player.createNew("Gerald"));gameState.addNewPlayer(Player.createNew("Bob"));
        gameState.addNewPlayer(Player.createNew("John"));
        gameState.addNewPlayer(Player.createNew("Cathy"));
        gameState.addNewPlayer(Player.createNew("Gerald"));
    }

    public GameState getGameState() {
        return gameState;
    }

    public static GameStateManager getInstance() {
        return manager;
    }
}
