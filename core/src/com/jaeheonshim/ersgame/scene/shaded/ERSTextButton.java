package com.jaeheonshim.ersgame.scene.shaded;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.Align;
import com.jaeheonshim.ersgame.ERSGame;

public class ERSTextButton extends TextButton {
    private ShaderProgram fontShader;

    public ERSTextButton(String text, Skin skin, ERSGame game) {
        super(text, skin);

        fontShader = game.assets.get(game.fontShader);
        TextButtonStyle style = skin.get(TextButtonStyle.class);
        ERSLabel label = new ERSLabel(text, new Label.LabelStyle(style.font, style.fontColor), game);
        label.setAlignment(Align.center);
        clearChildren();

        add(label).expand().fill();
    }

    public ERSTextButton(String text, TextButtonStyle style, ERSGame game) {
        super(text, style);
        fontShader = game.assets.get(game.fontShader);
        ERSLabel label = new ERSLabel(text, new Label.LabelStyle(style.font, style.fontColor), game);
        label.setAlignment(Align.center);
        clearChildren();

        add(label).expand().fill();
    }
}
