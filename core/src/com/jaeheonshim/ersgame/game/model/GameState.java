package com.jaeheonshim.ersgame.game.model;

import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ObjectMap;
import com.badlogic.gdx.utils.Queue;

public class GameState {
    private String gameCode;

    private ObjectMap<String, Player> playerMap = new ObjectMap<>();
    private Array<String> playerList = new Array<>();
    private String adminPlayer;
    private int currentTurnIndex;

    private Queue<CardType> deck = new Queue<>();

    private GameStatePhase gamePhase = GameStatePhase.PLAYER_JOIN;
    private boolean canPlay;
    private boolean ignoreSlap;

    private boolean isGameOver;

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

    public int getPileCount() {
        return deck.size;
    }

    public Array<CardType> getTopNCards(int n) {
        Array<CardType> array = new Array<>();

        for(int i = 0; i < n && i < deck.size; i++) {
            array.add(deck.get(i));
        }

        return array;
    }

    public CardType getTopCard() {
        if(deck.size < 1) return null;
        return deck.get(0);
    }

    public void addCardToTop(CardType type) {
        deck.addFirst(type);
    }

    public void addCardToBottom(CardType type) {
        deck.addLast(type);
    }

    public void setCurrentTurnIndex(int currentTurnIndex) {
        this.currentTurnIndex = currentTurnIndex;
    }

    public int getCurrentTurnIndex() {
        return currentTurnIndex;
    }

    public CardType removeCardFromTop() {
        if(deck.size == 0) return null;
        return deck.removeFirst();
    }

    public boolean isCanPlay() {
        return canPlay;
    }

    public void setCanPlay(boolean canPlay) {
        this.canPlay = canPlay;
    }

    public boolean isIgnoreSlap() {
        return ignoreSlap;
    }

    public void setIgnoreSlap(boolean ignoreSlap) {
        this.ignoreSlap = ignoreSlap;
    }

    public boolean isGameOver() {
        return isGameOver;
    }

    public void setGameOver(boolean gameOver) {
        isGameOver = gameOver;
    }
}
