package com.jaeheonshim.ersgame.game;

import com.jaeheonshim.ersgame.net.GameAction;
import com.jaeheonshim.ersgame.net.NetManager;

import java.util.ArrayList;
import java.util.List;

public class GameStateManager {
    private GameState gameState;

    private List<GameStateUpdateListener> updateListenerList = new ArrayList<>();
    private List<GameActionListener> gameActionListeners = new ArrayList<>();

    private static GameStateManager instance = new GameStateManager();

    private GameStateManager() {}

    public static GameStateManager getInstance() {
        return instance;
    }

    public void update(GameState newGameState) {
        this.gameState = newGameState;
        for(GameStateUpdateListener listener : updateListenerList) {
            listener.onUpdate(newGameState);
        }
    }

    public void registerListener(GameStateUpdateListener listener) {
        updateListenerList.add(listener);
    }

    public void registerActionListener(GameActionListener listener) {
        gameActionListeners.add(listener);
    }

    public void onGameAction(GameAction gameAction) {
        for(GameActionListener listener : gameActionListeners) {
            switch (gameAction) {
                case START:
                    listener.onGameStart();
                    break;
                case RECEIVE_CARD:
                    listener.onReceiveCard();
                    break;
            }
        }
    }

    public GameState getGameState() {
        return gameState;
    }

    public Player getSelfPlayer() {
        if(gameState == null) return null;
        return gameState.getPlayer(NetManager.getInstance().getClientUuid());
    }

    public boolean isAdmin(Player player) {
        if(gameState == null) return false;
        return gameState.getAdminPlayer().equals(player.getUuid());
    }

    public boolean isAdmin() {
        if(gameState == null) return false;
        return gameState.getAdminPlayer().equals(getSelfPlayer().getUuid());
    }
}
