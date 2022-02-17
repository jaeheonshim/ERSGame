package com.jaeheonshim.ersgame.scene.shaded;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFontCache;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.jaeheonshim.ersgame.ERSGame;

public class ERSLabel extends Label {
    private ShaderProgram fontShader;
    private Color tempColor = new Color();

    public ERSLabel(CharSequence text, Skin skin, ERSGame game) {
        this(text, skin, "default", game);
    }

    public ERSLabel(CharSequence text, Skin skin, String stylename, ERSGame game) {
        super(text, skin, stylename);
        this.fontShader = game.assets.get(game.fontShader);
    }

    public ERSLabel(CharSequence text, LabelStyle style, ERSGame game) {
        super(text, style);
        this.fontShader = game.assets.get(game.fontShader);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        validate();
        Color color = tempColor.set(getColor());
        BitmapFontCache cache = getBitmapFontCache();
        LabelStyle style = getStyle();

        color.a *= parentAlpha;
        if (style.background != null) {
            batch.setColor(color.r, color.g, color.b, color.a);
            style.background.draw(batch, getX(), getY(), getWidth(), getHeight());
        }
        if (style.fontColor != null) color.mul(style.fontColor);
        cache.tint(color);
        cache.setPosition(getX(), getY());

        batch.setShader(fontShader);
        cache.draw(batch);
        batch.setShader(null);
    }
}
