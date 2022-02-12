package com.jaeheonshim.ersgame.game;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class CardUtil {
    public static List<CardType> randomDeck() {
        List<CardType> deck = Arrays.asList(CardType.values());
        Collections.shuffle(deck);

        return deck;
    }
}
