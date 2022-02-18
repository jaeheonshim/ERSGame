package com.jaeheonshim.ersgame.game;

import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ObjectMap;

import java.util.*;

public class GameState {
    private String gameCode;

    private ObjectMap<String, Player> playerMap = new ObjectMap<>();
    private Array<String> playerList = new Array<>();
    private String adminPlayer;

    private GameStatePhase gamePhase = GameStatePhase.PLAYER_JOIN;

    public static GameState createGame(Player player, String gameCode) {
        GameState gameState = new GameState(gameCode);
        gameState.addPlayer(player);
        gameState.adminPlayer = player.getUuid();

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
        playerList.add(player.getUuid());
        playerMap.put(player.getUuid(), player);
    }

    public void removePlayer(String uuid) {
        Player player = getPlayer(uuid);
        if(player != null) {
            playerList.removeValue(uuid, true);
            playerMap.remove(uuid);
        }
    }

    public boolean hasPlayer(String uuid) {
        for(String player : playerList) {
            if(player.equals(uuid)) return true;
        }

        return false;
    }

    public boolean hasUsername(String username) {
        for(String player : playerList) {
            if(playerMap.get(player).getUsername().trim().equalsIgnoreCase(username.trim())) return true;
        }

        return false;
    }

    public String getGameCode() {
        return gameCode;
    }

    public Array<String> getPlayerList() {
        return playerList;
    }

    public String getAdminPlayer() {
        return adminPlayer;
    }

    public void setAdminPlayer(String adminPlayer) {
        this.adminPlayer = adminPlayer;
    }

    public GameStatePhase getGamePhase() {
        return gamePhase;
    }

    public void setGamePhase(GameStatePhase gamePhase) {
        this.gamePhase = gamePhase;
    }

    public ObjectMap<String, Player> getPlayerMap() {
        return playerMap;
    }
}
