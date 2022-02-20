package com.jaeheonshim.ersgame.server;

import com.jaeheonshim.ersgame.game.model.GameState;
import com.jaeheonshim.ersgame.game.model.Player;
import com.jaeheonshim.ersgame.server.action.ScheduleGameAction;

import java.util.*;

public class GameManager implements Runnable {
    private Map<String, GameState> gameStates = new HashMap<>();

    private static GameManager instance = new GameManager();

    private long lastTick;
    private Queue<ScheduleGameAction> gameActions = new LinkedList<>();

    public static GameManager getInstance() {
        return instance;
    }

    private GameManager() {
        lastTick = System.currentTimeMillis();
    }

    public GameState getGameOfPlayer(String playerUuid) {
        for(GameState gameState : gameStates.values()) {
            for(String player : gameState.getPlayerList()) {
                if(player.equals(playerUuid)) return gameState;
            }
        }

        return null;
    }

    public void removeGame(String joinCode) {
        gameStates.remove(joinCode);
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
        if(gameStates.size() >= 10000 / 2) return null;

        String code;

        do {
            code = Integer.toString((int) (Math.random() * (10000 - 1000)) + 1000);
        } while(getGame(code) != null);

        return code;
    }

    public Map<String, GameState> getGameStates() {
        return gameStates;
    }

    public void schedule(ScheduleGameAction gameAction) {
        gameActions.add(gameAction);
    }

    @Override
    public void run() {
        long deltaMills = System.currentTimeMillis() - lastTick;
        Iterator<ScheduleGameAction> scheduleGameActionIterator = gameActions.iterator();
        while(scheduleGameActionIterator.hasNext()) {
            ScheduleGameAction action = scheduleGameActionIterator.next();
            if(action.update(deltaMills)) {
                scheduleGameActionIterator.remove();
            }
        }

        lastTick = System.currentTimeMillis();
    }
}
