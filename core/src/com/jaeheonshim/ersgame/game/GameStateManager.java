package com.jaeheonshim.ersgame.game;

import java.util.ArrayList;
import java.util.List;

public class GameStateManager {
    private GameState gameState;

    private List<GameStateUpdateListener> updateListenerList = new ArrayList<>();

    private static GameStateManager instance = new GameStateManager();

    private GameStateManager() {}

    public static GameStateManager getInstance() {
        return instance;
    }

    public void update(GameState newGameState) {
        this.gameState = newGameState;
        for(GameStateUpdateListener listener : updateListenerList) {
            listener.onUpdate(newGameState);
        }
    }

    public void registerListener(GameStateUpdateListener listener) {
        updateListenerList.add(listener);
    }

    public GameState getGameState() {
        return gameState;
    }
}
