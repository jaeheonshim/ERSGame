package com.jaeheonshim.ersgame.game;

import java.util.ArrayList;
import java.util.List;

public class GameState {
    private List<CardType> pile = new ArrayList<>();

    public GameState() {
        pile.add(CardType.SPADE_2);
        pile.add(CardType.SPADE_3);
        pile.add(CardType.SPADE_4);
    }

    public List<CardType> getPile() {
        return pile;
    }
}
