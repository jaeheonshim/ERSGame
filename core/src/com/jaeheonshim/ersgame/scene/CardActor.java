package com.jaeheonshim.ersgame.scene;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.jaeheonshim.ersgame.CardType;
import com.jaeheonshim.ersgame.ERSGame;

public class CardActor extends Actor {
    private ERSGame game;
    private TextureAtlas atlas;

    private CardType type = CardType.CLOVER_A;

    public CardActor(ERSGame game) {
        this.game = game;

        atlas = game.assets.get(game.cardsAtlas);
        TextureRegion sampleRegion = atlas.findRegion(CardType.SPADE_A.filename);
        setBounds(sampleRegion.getRegionX(), sampleRegion.getRegionY(), sampleRegion.getRegionWidth(), sampleRegion.getRegionHeight());
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        Color color = getColor();
        batch.setColor(color.r, color.g, color.b, color.a * parentAlpha);

        System.out.println(type.filename);
        TextureRegion region = atlas.findRegion(type.filename);
        batch.draw(region, getX(), getY(), getOriginX(), getOriginY(), getWidth(), getHeight(), getScaleX(), getScaleY(), getRotation());
    }

    public CardType getType() {
        return type;
    }

    public void setType(CardType type) {
        this.type = type;
    }
}
