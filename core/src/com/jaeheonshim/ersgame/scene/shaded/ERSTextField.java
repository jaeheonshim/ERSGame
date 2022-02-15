package com.jaeheonshim.ersgame.scene.shaded;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.jaeheonshim.ersgame.ERSGame;

public class ERSTextField extends TextField {
    private ShaderProgram fontShader;

    public ERSTextField(String text, Skin skin, ERSGame game) {
        this(text, skin, "default", game);
    }

    public ERSTextField(String text, Skin skin, String styleName, ERSGame game) {
        super(text, skin, styleName);
        this.fontShader = game.assets.get(game.fontShader);
    }

    @Override
    protected void drawText(Batch batch, BitmapFont font, float x, float y) {
        batch.setShader(fontShader);
        super.drawText(batch, font, x, y);
        batch.setShader(null);
    }

    @Override
    protected void drawMessageText(Batch batch, BitmapFont font, float x, float y, float maxWidth) {
        batch.setShader(fontShader);
        super.drawMessageText(batch, font, x, y, maxWidth);
        batch.setShader(null);
    }
}
