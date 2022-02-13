package com.jaeheonshim.ersgame.scene.shaded;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.jaeheonshim.ersgame.ERSGame;

public class ERSTextButton extends TextButton {
    private ShaderProgram fontShader;

    public ERSTextButton(String text, TextButtonStyle style, ERSGame game) {
        super(text, style);
        fontShader = game.assets.get(game.fontShader);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.setShader(fontShader);
        super.draw(batch, parentAlpha);
        batch.setShader(null);
    }
}
