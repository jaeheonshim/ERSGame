package com.jaeheonshim.ersgame.game;

import java.util.*;

public class GameState {
    private Deque<CardType> pile = new LinkedList<>();
    private Map<String, Player> playerMap = new HashMap<>();

    public GameState() {
        pile.addLast(CardType.SPADE_2);
        pile.addLast(CardType.SPADE_3);
        pile.addLast(CardType.SPADE_4);
        pile.addLast(CardType.CLOVER_2);

        addNewPlayer(Player.createNew("Bob"));
        addNewPlayer(Player.createNew("John"));
        addNewPlayer(Player.createNew("Cathy"));
        addNewPlayer(Player.createNew("Gerald"));

        initializeNew();
    }

    public Deque<CardType> getPile() {
        return pile;
    }

    public void initializeNew() {
        List<CardType> newDeck = CardUtil.randomDeck();
        CardUtil.randomDistribute(newDeck, new ArrayList<>(playerMap.values()));
    }

    public void addNewPlayer(Player player) {
        playerMap.put(player.getUuid(), player);
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

    public static void main(String[] args) {
        GameState state = new GameState();
        state.addNewPlayer(Player.createNew("Bob"));
        state.addNewPlayer(Player.createNew("John"));
        state.addNewPlayer(Player.createNew("Cathy"));
        state.addNewPlayer(Player.createNew("Gerald"));

        state.initializeNew();
    }
}
