package com.jaeheonshim.ersgame.server;

import com.jaeheonshim.ersgame.game.GameState;
import com.jaeheonshim.ersgame.game.Player;

import java.util.HashMap;
import java.util.Map;

public class GameManager {
    private Map<String, GameState> gameStates = new HashMap<>();

    private static GameManager instance = new GameManager();

    public static GameManager getInstance() {
        return instance;
    }

    private GameManager() {}

    public GameState getGameOfPlayer(String playerUuid) {
        for(GameState gameState : gameStates.values()) {
            for(Player player : gameState.getPlayerList()) {
                if(player.getUuid().equals(playerUuid)) return gameState;
            }
        }

        return null;
    }

    public GameState getGame(String joinCode) {
        return gameStates.get(joinCode);
    }

    public GameState createNewGame(String uuid, String username) throws Exception {
        if(getGameOfPlayer(uuid) != null) {
            throw new Exception("Player already in game");
        }

        Player player = new Player();
        player.setUuid(uuid);
        player.setUsername(username);

        GameState gameState = GameState.createGame(player, getUnusedGameCode());
        gameStates.put(gameState.getGameCode(), gameState);
        return gameState;
    }

    public String getUnusedGameCode() {
        if(gameStates.size() >= 1000000 / 2) return null;

        String code;

        do {
            code = Integer.toString((int) (Math.random() * (1000000 - 100000)) + 100000);
        } while(getGame(code) != null);

        return code;
    }
}
