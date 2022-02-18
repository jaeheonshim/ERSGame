package com.jaeheonshim.ersgame.game;

import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ObjectMap;

import java.util.*;

public class GameState {
    private String gameCode;

    private ObjectMap<String, Player> playerMap = new ObjectMap<>();
    private Array<Player> playerList = new Array<>();
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

    public Player getPlayer(String uuid) {
        return playerMap.get(uuid);
    }

    public void addPlayer(Player player) {
        playerList.add(player);
        playerMap.put(player.getUuid(), player);
    }

    public void removePlayer(String uuid) {
        Player player = getPlayer(uuid);
        if(player != null) {
            playerList.removeValue(player, true);
            playerMap.remove(uuid);
        }
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

    public Array<Player> getPlayerList() {
        return playerList;
    }

    public Player getAdminPlayer() {
        return adminPlayer;
    }

    public void setAdminPlayer(Player adminPlayer) {
        this.adminPlayer = adminPlayer;
    }
}
