package com.jaeheonshim.ersgame.game;

import com.badlogic.gdx.utils.Queue;

import java.util.Iterator;
import java.util.Objects;

public class Player {
    private String uuid;
    private String username;

    private Queue<CardType> cardStack = new Queue<>();

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Player player = (Player) o;
        return Objects.equals(uuid, player.uuid);
    }

    @Override
    public int hashCode() {
        return Objects.hash(uuid);
    }

    public Iterator<CardType> getCards() {
        return cardStack.iterator();
    }

    public void addCardToBottom(CardType cardType) {
        cardStack.addLast(cardType);
    }

    public void addCardToTop(CardType cardType) {
        cardStack.addFirst(cardType);
    }

    public CardType removeTopCard() {
        return cardStack.removeFirst();
    }
}
