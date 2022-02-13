package com.jaeheonshim.ersgame.scene;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Stack;
import com.jaeheonshim.ersgame.ERSGame;
import com.jaeheonshim.ersgame.game.GameState;

import javax.smartcardio.Card;

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
    }

    @Override
    public void act(float delta) {
        for(int i = 0; i < cardActors.length; i++) {
            int pileSize = gameState.getPile().size();
            if(pileSize < i + 1) break;
            cardActors[i].setType(gameState.getPile().get(pileSize - 1 - i));
            cardActors[i].setRotation(8 * i);
            cardActors[i].setY(-20 * i);
        }
    }
}
