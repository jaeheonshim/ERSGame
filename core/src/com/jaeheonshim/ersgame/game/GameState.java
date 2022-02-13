package com.jaeheonshim.ersgame.game;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GameState {
    private List<CardType> pile = new ArrayList<>();
    private Map<String, Player> playerMap = new HashMap<>();

    public GameState() {
        pile.add(CardType.SPADE_2);
        pile.add(CardType.SPADE_3);
        pile.add(CardType.SPADE_4);
    }

    public List<CardType> getPile() {
        return pile;
    }
}
