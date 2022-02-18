package com.jaeheonshim.ersgame.game;

import com.badlogic.gdx.utils.Array;
import com.jaeheonshim.ersgame.GdxCollectionUtil;

import java.util.*;
import static com.jaeheonshim.ersgame.game.CardType.*;

public class CardUtil {
    private static List<CardType> validCards = Arrays.asList(CLOVER_A, CLOVER_2, CLOVER_3, CLOVER_4, CLOVER_5, CLOVER_6, CLOVER_7, CLOVER_8, CLOVER_9, CLOVER_10, CLOVER_J, CLOVER_Q, CLOVER_K, HEART_A, HEART_2, HEART_3, HEART_4, HEART_5, HEART_6, HEART_7, HEART_8, HEART_9, HEART_10, HEART_J, HEART_Q, HEART_K, SPADE_A, SPADE_2, SPADE_3, SPADE_4, SPADE_5, SPADE_6, SPADE_7, SPADE_8, SPADE_9, SPADE_10, SPADE_J, SPADE_Q, SPADE_K, DIAMOND_A, DIAMOND_2, DIAMOND_3, DIAMOND_4, DIAMOND_5, DIAMOND_6, DIAMOND_7, DIAMOND_8, DIAMOND_9, DIAMOND_10, DIAMOND_J, DIAMOND_Q, DIAMOND_K);

    public static List<CardType> randomDeck() {
        List<CardType> deck = new ArrayList<>(validCards);
        deck.remove(CardType.BACK);
        Collections.shuffle(deck);

        return deck;
    }

    public static void randomDistribute(List<CardType> deck, Array<Player> players) {
        int n = deck.size() / players.size;
        int c = 0;
        Collections.shuffle(deck);

        for(Player p : players) {
            for(int i = 0; i < n; i++) {
                p.addCardToBottom(deck.get(c++));
            }
        }

        GdxCollectionUtil.shuffle(players);
        for(int i = 0; i < deck.size() % players.size; i++) {
            players.get(i).addCardToBottom(deck.get(c++));
        }
    }
}
