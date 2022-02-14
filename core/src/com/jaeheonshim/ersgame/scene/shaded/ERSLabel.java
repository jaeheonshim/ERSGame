package com.jaeheonshim.ersgame.scene.shaded;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.jaeheonshim.ersgame.ERSGame;

public class ERSLabel extends Label {
    private ShaderProgram fontShader;

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
        batch.setShader(fontShader);
        super.draw(batch, parentAlpha);
        batch.setShader(null);
    }
}
