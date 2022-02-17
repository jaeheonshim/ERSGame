package com.jaeheonshim.ersgame.game;

import java.util.*;

public class CardUtil {
    public static List<CardType> randomDeck() {
        List<CardType> deck = Arrays.asList(CardType.values());
        Collections.shuffle(deck);

        return deck;
    }

    public static void randomDistribute(List<CardType> deck, List<Player> players) {
        int n = deck.size() / players.size();
        int c = 0;
        Collections.shuffle(deck);

//        for(Player p : players) {
//            for(int i = 0; i < n; i++) {
//                p.addCard(deck.get(c++));
//            }
//        }
//
//        Collections.shuffle(players);
//        for(int i = 0; i < deck.size() % players.size(); i++) {
//            players.get(i).addCard(deck.get(c++));
//        }
    }
}
