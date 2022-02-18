package com.jaeheonshim.ersgame.game;

import com.badlogic.gdx.math.MathUtils;
import com.jaeheonshim.ersgame.ERSException;

public class GameStateUtil {
    public static void startGame(GameState gameState) {
        if(gameState.getGamePhase() == GameStatePhase.STARTED) {
            throw new ERSException("Game already started");
        }

        CardUtil.randomDistribute(CardUtil.randomDeck(), gameState.getPlayerMap().values().toArray());
        gameState.setGamePhase(GameStatePhase.STARTED);
        gameState.setCurrentTurnIndex(MathUtils.random(gameState.getPlayerList().size - 1));
    }

    public static void playCard(GameState gameState, String uuid) {
        Player player = gameState.getPlayer(uuid);
        CardType type = player.removeTopCard();

        if(type == null) throw new ERSException("Tried to play card while empty hand");

        gameState.addCardToTop(type);
    }
}
