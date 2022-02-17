package com.jaeheonshim.ersgame.game;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GameState {
    private final String gameCode = "000000";

    private Map<String, Player> playerMap = new HashMap<>();
    private List<Player> playerList = new ArrayList<>();
    private Player adminPlayer;

    public static GameState createGame(Player player) {
        GameState gameState = new GameState();
        gameState.addPlayer(player);
        gameState.adminPlayer = player;

        return gameState;
    }

    public void addPlayer(Player player) {
        playerList.add(player);
        playerMap.put(player.getUuid(), player);
    }

    public String getGameCode() {
        return gameCode;
    }

    public List<Player> getPlayerList() {
        return playerList;
    }
}
