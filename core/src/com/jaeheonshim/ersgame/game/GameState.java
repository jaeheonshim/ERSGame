package com.jaeheonshim.ersgame.game;

import java.util.*;

public class GameState {
    private String joinCode;
    private Deque<CardType> pile = new LinkedList<>();
    private Map<String, Player> playerMap = new HashMap<>();
    private Player currentTurn;
    private GameEventListener gameEventListener = new GameEventListener();

    public GameState() {
        pile.addLast(CardType.SPADE_2);
        pile.addLast(CardType.SPADE_3);
        pile.addLast(CardType.SPADE_4);
        pile.addLast(CardType.CLOVER_2);
    }

    public Deque<CardType> getPile() {
        return pile;
    }

    public void setPile(Deque<CardType> pile) {
        this.pile = pile;
    }

    public void initializeNew() {
        List<CardType> newDeck = CardUtil.randomDeck();
        List<Player> players = new ArrayList<>(playerMap.values());
        CardUtil.randomDistribute(newDeck, players);
        currentTurn = players.get((int) (Math.random() * players.size()));
    }

    public void addNewPlayer(Player player) {
        player.setOrdinal(playerMap.size());
        playerMap.put(player.getUuid(), player);

        gameEventListener.onPlayerJoin(this, player);
    }

    public List<CardType> getTopN(int n) {
        return new ArrayList<>(pile).subList(0, n);
    }

    public boolean isDouble() {
        if(pile.size() < 2) return false;
        List<CardType> cards = getTopN(2);
        return cards.get(0) == cards.get(1);
    }

    public boolean isSandwich() {
        if(pile.size() < 3) return false;
        List<CardType> cards = getTopN(3);

        return cards.get(0) == cards.get(2) && cards.get(1) != cards.get(0);
    }

    public Map<String, Player> getPlayerMap() {
        return playerMap;
    }

    public Collection<Player> getPlayers() {
        return playerMap.values();
    }

    public void setCurrentTurn(String uuid) {
        currentTurn = playerMap.get(uuid);
    }

    public Player getCurrentTurn() {
        return currentTurn;
    }

    public String getJoinCode() {
        return joinCode;
    }

    public void setJoinCode(String joinCode) {
        this.joinCode = joinCode;
    }

    public boolean usernameExists(String username) {
        for(Player player : playerMap.values()) {
            if(player.getName().toLowerCase().trim().equals(username.toLowerCase().trim())) {
                return true;
            }
        }

        return false;
    }

    public void setGameEventListener(GameEventListener gameEventListener) {
        this.gameEventListener = gameEventListener;
    }

    public GameEventListener getGameEventListener() {
        return gameEventListener;
    }

    public static void main(String[] args) {
        GameState state = new GameState();
        state.addNewPlayer(Player.createNew("Bob"));
        state.addNewPlayer(Player.createNew("John"));
        state.addNewPlayer(Player.createNew("Cathy"));
        state.addNewPlayer(Player.createNew("Gerald"));

        state.initializeNew();
    }
}
