package com.jaeheonshim.ersgame.scene.game;

import com.badlogic.gdx.scenes.scene2d.ui.Stack;
import com.jaeheonshim.ersgame.ERSGame;
import com.jaeheonshim.ersgame.game.CardType;
import com.jaeheonshim.ersgame.scene.game.CardActor;

import java.util.List;
import java.util.function.Supplier;

public class PileDisplayActor extends Stack {
    private static final int DISPLAY_COUNT = 3;

    private ERSGame game;
    private Supplier<List<CardType>> getCards;

    private CardActor[] cardActors = new CardActor[0];

    public PileDisplayActor(ERSGame game, Supplier<List<CardType>> getCards) {
        this.game = game;
        this.getCards = getCards;

        updatePileState();
    }

    private void initializeActors() {
        cardActors = new CardActor[DISPLAY_COUNT];

        for(int i = 0; i < cardActors.length; i++) {
            cardActors[i] = new CardActor(game);
        }

        clearChildren();
        for(int i = cardActors.length - 1; i >= 0; i--) {
            add(cardActors[i]);
        }
    }

    public void updatePileState() {
        List<CardType> cards = getCards.get();
        if(cards.size() != cardActors.length) initializeActors();

        for(int i = 0; i < cardActors.length; i++) {
            int pileSize = cards.size();
            if(pileSize < i + 1) break;
            cardActors[i].setType(cards.get(i));
            cardActors[i].setRotation(8 * i);
            cardActors[i].setY(-20 * i);
        }
        invalidate();
    }

    public void setTopFlipped(boolean flipped) {
        cardActors[0].setFlipped(flipped);
    }

    public void flipTop() {
        cardActors[0].flip();
    }

    @Override
    public void act(float delta) {
        super.act(delta);
    }
}
