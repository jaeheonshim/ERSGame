package com.jaeheonshim.ersgame.game;

import com.jaeheonshim.ersgame.ERSException;

public class GameStateUtil {
    public static void startGame(GameState gameState) {
        if(gameState.getGamePhase() == GameStatePhase.STARTED) {
            throw new ERSException("Game already started");
        }

        CardUtil.randomDistribute(CardUtil.randomDeck(), gameState.getPlayerMap().values().toArray());
        gameState.setGamePhase(GameStatePhase.STARTED);
    }
}
