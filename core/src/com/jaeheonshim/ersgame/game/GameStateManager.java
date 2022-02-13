package com.jaeheonshim.ersgame.game;

import com.jaeheonshim.ersgame.net.packet.GameStateUpdateEvent;
import com.jaeheonshim.ersgame.util.RandomNameGenerator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class GameStateManager {
    private GameState gameState;
    private String me;

    private List<GameStateUpdateListener> listenerList = new ArrayList<>();

    private static GameStateManager manager = new GameStateManager();

    private GameStateManager() {
        gameState = new GameState();

//        gameState.addNewPlayer(Player.createNew(RandomNameGenerator.randomName()));
//        gameState.addNewPlayer(Player.createNew(RandomNameGenerator.randomName()));
//        gameState.addNewPlayer(Player.createNew(RandomNameGenerator.randomName()));
//        gameState.addNewPlayer(Player.createNew(RandomNameGenerator.randomName()));
//        gameState.addNewPlayer(Player.createNew(RandomNameGenerator.randomName()));
    }

    public void updateState(GameStateUpdateEvent event) {
        if(gameState == null) {
            gameState = new GameState();
            gameState.setJoinCode(event.joinCode);
        }

        for(GameStateUpdateEvent.PlayerInfo info : event.players) {
            if(!gameState.getPlayerMap().containsKey(info.uuid)) {
                Player newPlayer = new Player(info.uuid, info.name, info.ordinal);
                gameState.getPlayerMap().put(info.uuid, newPlayer);
            }

            Player player = gameState.getPlayerMap().get(info.uuid);
            player.setCardCount(info.cardCount);
        }

        if(event.currentTurnUUID != null) {
            gameState.setCurrentTurn(event.currentTurnUUID);
        }

        gameState.setPile(new LinkedList<CardType>(Arrays.asList(event.pile)));
        fireUpdate();
    }

    public void fireUpdate() {
        for(GameStateUpdateListener listener : listenerList) {
            listener.updateOccurred(gameState);
        }
    }

    public void addUpdateListener(GameStateUpdateListener updateListener) {
        listenerList.add(updateListener);
    }

    public GameState getGameState() {
        return gameState;
    }

    public void setSelfUUID(String self) {
        this.me = self;
    }

    public String getSelfUUID() {
        return this.me;
    }

    public static GameStateManager getInstance() {
        return manager;
    }
}
