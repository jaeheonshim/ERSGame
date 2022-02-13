package com.jaeheonshim.ersgame.scene;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Stack;
import com.jaeheonshim.ersgame.ERSGame;
import com.jaeheonshim.ersgame.game.CardType;
import com.jaeheonshim.ersgame.game.GameState;

import java.util.List;

public class PileDisplayActor extends Stack {
    private static final int DISPLAY_COUNT = 3;

    private ERSGame game;
    private GameState gameState;

    private CardActor[] cardActors = new CardActor[DISPLAY_COUNT];

    public PileDisplayActor(ERSGame game, GameState gameState) {
        this.game = game;
        this.gameState = gameState;

        for(int i = 0; i < cardActors.length; i++) {
            cardActors[i] = new CardActor(game);
        }

        for(int i = cardActors.length - 1; i >= 0; i--) {
            add(cardActors[i]);
        }

        updatePileState();
    }

    public void updatePileState() {
        List<CardType> cards = gameState.getTopN(DISPLAY_COUNT);
        for(int i = 0; i < cardActors.length; i++) {
            int pileSize = gameState.getPile().size();
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
