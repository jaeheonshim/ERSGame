package com.jaeheonshim.ersgame.game.util;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;
import com.jaeheonshim.ersgame.util.ERSException;
import com.jaeheonshim.ersgame.game.model.CardType;
import com.jaeheonshim.ersgame.game.model.GameState;
import com.jaeheonshim.ersgame.game.model.GameStatePhase;
import com.jaeheonshim.ersgame.game.model.Player;

public class GameStateUtil {
    public static void startGame(GameState gameState) {
        if(gameState.getGamePhase() == GameStatePhase.STARTED) {
            throw new ERSException("Game already started");
        }

        CardUtil.randomDistribute(CardUtil.randomDeck(), gameState.getPlayerMap().values().toArray());
        gameState.setGamePhase(GameStatePhase.STARTED);
        gameState.setCurrentTurnIndex(MathUtils.random(gameState.getPlayerList().size - 1));
        gameState.setCanPlay(true);
    }

    public static void playCard(GameState gameState, String uuid) {
        Player player = gameState.getPlayer(uuid);
        CardType type = player.removeTopCard();

        if(type == null) throw new ERSException("Tried to play card while empty hand");

        gameState.addCardToTop(type);
        gameState.setCanPlay(false);
    }

    public static boolean removePlayer(GameState gameState, String uuid) {
        Player player = gameState.getPlayer(uuid);
        gameState.removePlayer(uuid);

        if(gameState.getPlayerList().size == 0) {
            return true;
        }

        if(gameState.getAdminPlayer().equals(player.getUuid())) {
            gameState.setAdminPlayer(gameState.getPlayerList().get(0));
        }

        return false;
    }

    public static boolean isGameOver(GameState gameState) {
        int count = 0;

        for(Player player : gameState.getPlayerMap().values()) {
            if(player.getCardCount() > 0) {
                count++;
            }
        }

        return count <= 1 && !isValidSlap(gameState);
    }

    public static void nextTurn(GameState gameState) {
        int i = gameState.getCurrentTurnIndex();

        do {
            i++;
            i %= gameState.getPlayerList().size;
        } while(gameState.getPlayer(gameState.getPlayerList().get(i)).getCardCount() <= 0);

        gameState.setCurrentTurnIndex(i);
        gameState.setCanPlay(true);
    }

    public static boolean isValidSlap(GameState gameState) {
        return isDouble(gameState) || isSandwich(gameState);
    }

    private static boolean isDouble(GameState gameState) {
        Array<CardType> cardTypeArray = gameState.getTopNCards(2);
        if(cardTypeArray.size < 2) return false;

        return cardTypeArray.get(0).number == cardTypeArray.get(1).number;
    }

    private static boolean isSandwich(GameState gameState) {
        Array<CardType> cardTypeArray = gameState.getTopNCards(3);
        if(cardTypeArray.size < 3) return false;

        return cardTypeArray.get(0).number == cardTypeArray.get(2).number;
    }
}
