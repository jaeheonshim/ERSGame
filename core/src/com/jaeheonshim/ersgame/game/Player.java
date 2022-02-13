package com.jaeheonshim.ersgame.game;

import java.util.Deque;
import java.util.List;

public class Player {
    private String uuid;
    private String name;
    private Deque<CardType> cards;

    public void addCard(CardType cardType) {
        cards.addLast(cardType);
    }

    public CardType removeFirstCard() {
        return cards.removeFirst();
    }
}
