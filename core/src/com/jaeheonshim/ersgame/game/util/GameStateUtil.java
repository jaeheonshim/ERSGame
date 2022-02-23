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

    // returns true of turn should be switched, false if not
    public static boolean playCard(GameState gameState, String uuid) {
        Player player = gameState.getPlayer(uuid);
        CardType type = player.removeTopCard();

        if(type == null) throw new ERSException("Tried to play card while empty hand");

        gameState.addCardToTop(type);
        gameState.setCanPlay(false);

        if(gameState.getPendingCardCount() > 0) {
            gameState.setPendingCardCount(gameState.getPendingCardCount() - 1);
        }

        if(type.number >= 11 || type.number == 1) {
            gameState.setLastFacePlayer(uuid);
            switch(type.number) {
                case 1:
                    gameState.setPendingCardCount(4);
                    break;
                case 11:
                    gameState.setPendingCardCount(1);
                    break;
                case 12:
                    gameState.setPendingCardCount(2);
                    break;
                case 13:
                    gameState.setPendingCardCount(3);
                    break;
                default:
                    break;
            }
        } else {
            return gameState.getPendingCardCount() <= 0 || gameState.getPlayer(gameState.getPlayerList().get(gameState.getCurrentTurnIndex())).getCardCount() <= 0;
        }

        return true;
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

    public static void nextTurn(GameState gameState, boolean switchTurn) {
        if(!switchTurn) {
            gameState.setCanPlay(true);
            return;
        }

        int i = gameState.getCurrentTurnIndex();

        do {
            i++;
            i %= gameState.getPlayerList().size;
        } while(gameState.getPlayer(gameState.getPlayerList().get(i)).getCardCount() <= 0);

        gameState.setCurrentTurnIndex(i);
        gameState.setCanPlay(true);
    }

    public static boolean isValidSlap(GameState gameState) {
        return isDouble(gameState) || isSandwich(gameState) || isMarriage(gameState) || isConsecutiveAscending(gameState) || isConsecutiveDescending(gameState);
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

    private static boolean isMarriage(GameState gameState) {
        Array<CardType> cardTypeArray = gameState.getTopNCards(2);
        if(cardTypeArray.size < 2) return false;

        return (cardTypeArray.get(0).number == 12 && cardTypeArray.get(1).number == 13) || (cardTypeArray.get(1).number == 12 && cardTypeArray.get(0).number == 13);
    }

    public static boolean isConsecutiveAscending(GameState gameState) {
        Array<CardType> cardTypeArray = gameState.getTopNCards(3);
        if(cardTypeArray.size < 3) return false;

        for(int i = 1; i < cardTypeArray.size; i++) {
            boolean isAce = (cardTypeArray.get(i - 1).number == 13 && cardTypeArray.get(i).number == 1);
            boolean isAscending = (cardTypeArray.get(i).number - cardTypeArray.get(i - 1).number) == 1;

            if(!isAscending && !isAce) return false;
        }

        return true;
    }

    public static boolean isConsecutiveDescending(GameState gameState) {
        Array<CardType> cardTypeArray = gameState.getTopNCards(3);
        if(cardTypeArray.size < 3) return false;

        for(int i = 1; i < cardTypeArray.size; i++) {
            boolean isAce = (cardTypeArray.get(i).number == 13 && cardTypeArray.get(i - 1).number == 1);
            boolean isAscending = (cardTypeArray.get(i - 1).number - cardTypeArray.get(i).number) == 1;

            if(!isAscending && !isAce) return false;
        }

        return true;
    }
}
