package com.jaeheonshim.ersgame.game;

import com.jaeheonshim.ersgame.game.model.GameState;

public interface GameStateUpdateListener {
    default void onUpdate(GameState newGameState) {}
    default void onUpdate(GameState newGameState, GameState oldGameState) {}
}
