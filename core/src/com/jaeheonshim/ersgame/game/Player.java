package com.jaeheonshim.ersgame.game;

import java.util.Deque;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

public class Player {
    private String uuid;
    private String name;
    private Deque<CardType> cards = new LinkedList<>();

    private Player(String name) {
        this.uuid = UUID.randomUUID().toString();
        this.name = name;
    }

    public static Player createNew(String name) {
        return new Player(name);
    }

    public void addCard(CardType cardType) {
        cards.addLast(cardType);
    }

    public CardType removeFirstCard() {
        return cards.removeFirst();
    }

    public String getUuid() {
        return uuid;
    }

    public String getName() {
        return name;
    }

    public Deque<CardType> getCards() {
        return cards;
    }
}
