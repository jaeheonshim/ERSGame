package com.jaeheonshim.ersgame.scene.game;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.actions.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.jaeheonshim.ersgame.game.CardType;
import com.jaeheonshim.ersgame.ERSGame;
import com.jaeheonshim.ersgame.game.GameState;
import com.jaeheonshim.ersgame.scene.ActionUtil;

public class CardActor extends Actor {
    private ERSGame game;
    private TextureAtlas atlas;
    private TextureRegion shadowRegion;

    private boolean shadow = true;
    private boolean flipped = false;

    private CardType type;
    private float scale = 0.4f;

    public CardActor(ERSGame game) {
        this.game = game;

        atlas = game.assets.get(game.cardsAtlas);
        shadowRegion = atlas.findRegion("shadow");

        TextureRegion shadowRegion = atlas.findRegion(CardType.SPADE_A.filename);
        setBounds(shadowRegion.getRegionX(), shadowRegion.getRegionY(), shadowRegion.getRegionWidth() * scale, shadowRegion.getRegionHeight() * scale);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        if (type == null) return;

        Color color = getColor();
        batch.setColor(color.r, color.g, color.b, color.a * parentAlpha);

        if (shadow) {
            batch.draw(shadowRegion, getX() - 16 * scale, getY() - 16 * scale, getOriginX(), getOriginY(), getWidth(), getHeight(), getScaleX(), getScaleY(), getRotation());
        }

        TextureRegion region = atlas.findRegion(type.filename);

        if (flipped) {
            region = atlas.findRegion("back");
        }

        batch.draw(region, getX(), getY(), getOriginX(), getOriginY(), getWidth(), getHeight(), getScaleX(), getScaleY(), getRotation());
    }

    public void flip() {
        addAction(
                new SequenceAction(
                        ActionUtil.flipOut(getX(), getWidth(), 0.15f),
                        new Action() {
                            @Override
                            public boolean act(float delta) {
                                flipped = !flipped;
                                return true;
                            }
                        },
                        ActionUtil.flipIn(getX(), getWidth(), 0.15f)));
    }

    public void flyIn(float destX, float destY, boolean top, Runnable onFinish) {
        float duration = 0.5f;

        setVisible(true);
        if (top) {
            setPosition(getStage().getWidth() / 2f - getWidth() / 2f, getStage().getHeight());
        } else {
            setPosition(getStage().getWidth() / 2f - getWidth() / 2f, -getHeight());
        }
        setScale(0.2f);

        MoveToAction moveToAction = new MoveToAction();
        moveToAction.setPosition(destX, destY);
        moveToAction.setDuration(duration);

        ScaleToAction scaleToAction = new ScaleToAction();
        scaleToAction.setScale(1);
        scaleToAction.setDuration(duration);

        VisibleAction visibleAction = new VisibleAction();
        visibleAction.setVisible(false);

        RunnableAction finishAction = new RunnableAction();
        finishAction.setRunnable(onFinish);

        addAction(new SequenceAction(
                new ParallelAction(
                        moveToAction,
                        scaleToAction
                ),
                finishAction,
                visibleAction
                )
        );
    }

    public CardType getType() {
        return type;
    }

    public void setType(CardType type) {
        this.type = type;
    }

    public void setFlipped(boolean flipped) {
        this.flipped = flipped;
    }

    public boolean isShadow() {
        return shadow;
    }

    public void setShadow(boolean shadow) {
        this.shadow = shadow;
    }
}
