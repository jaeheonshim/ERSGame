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

    public GameState createNewGame(String uuid, String username) {
        Player player = new Player();
        player.setUuid(uuid);
        player.setUsername(username);

        GameState gameState = GameState.createGame(player);
        gameStates.put(gameState.getGameCode(), gameState);
        return gameState;
    }
}
