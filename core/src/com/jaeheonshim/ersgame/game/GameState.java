package com.jaeheonshim.ersgame.game;

import java.util.*;

public class GameState {
    private final String gameCode;

    private Map<String, Player> playerMap = new HashMap<>();
    private List<Player> playerList = new ArrayList<>();
    private Player adminPlayer;

    public static GameState createGame(Player player, String gameCode) {
        GameState gameState = new GameState(gameCode);
        gameState.addPlayer(player);
        gameState.adminPlayer = player;

        return gameState;
    }

    public GameState() {
        gameCode = "";
    }

    public GameState(String gameCode) {
        this.gameCode = gameCode;
    }

    public void addPlayer(Player player) {
        playerList.add(player);
        playerMap.put(player.getUuid(), player);
    }

    public boolean hasPlayer(String uuid) {
        for(Player player : playerList) {
            if(player.getUuid().equals(uuid)) return true;
        }

        return false;
    }

    public boolean hasUsername(String username) {
        for(Player player : playerList) {
            if(player.getUsername().trim().equalsIgnoreCase(username.trim())) return true;
        }

        return false;
    }

    public String getGameCode() {
        return gameCode;
    }

    public List<Player> getPlayerList() {
        return playerList;
    }
}
