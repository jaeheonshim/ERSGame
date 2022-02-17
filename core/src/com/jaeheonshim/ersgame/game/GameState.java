package com.jaeheonshim.ersgame.game;

import java.util.ArrayList;
import java.util.List;

public class GameState {
    private final String gameCode = "000000";

    private List<Player> playerList = new ArrayList<>();
    private Player adminPlayer;

    public static GameState createGame(Player player) {
        GameState gameState = new GameState();
        gameState.playerList.add(player);
        gameState.adminPlayer = player;

        player.setGameState(gameState);

        return gameState;
    }

    public String getGameCode() {
        return gameCode;
    }
}
