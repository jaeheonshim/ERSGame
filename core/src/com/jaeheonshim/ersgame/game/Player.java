package com.jaeheonshim.ersgame.game;

import java.util.Deque;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

public class Player implements Comparable<Player> {
    private String uuid;
    private String name;
    private Deque<CardType> cards = new LinkedList<>();
    private int ordinal;

    private Player(String name) {
        this.uuid = UUID.randomUUID().toString();
        this.name = name;
        this.ordinal = ordinal;
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

    public int getOrdinal() {
        return ordinal;
    }

    public void setOrdinal(int ordinal) {
        this.ordinal = ordinal;
    }

    @Override
    public int compareTo(Player o) {
        return this.ordinal - o.ordinal;
    }
}
