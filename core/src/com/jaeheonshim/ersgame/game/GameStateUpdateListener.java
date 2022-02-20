package com.jaeheonshim.ersgame.game;

import com.jaeheonshim.ersgame.game.model.GameState;

public interface GameStateUpdateListener {
    void onUpdate(GameState newGameState);
}
