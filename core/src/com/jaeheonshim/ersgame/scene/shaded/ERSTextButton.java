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
    private ERSLabel label;

    public ERSTextButton(String text, Skin skin, String styleName, ERSGame game) {
        super(text, skin, styleName);

        fontShader = game.assets.get(game.fontShader);
        TextButtonStyle style = skin.get(TextButtonStyle.class);
        label = new ERSLabel(text, new Label.LabelStyle(style.font, style.fontColor), game);
        label.setAlignment(Align.center);
        clearChildren();

        add(label).expand().fill();
    }

    public ERSTextButton(String text, Skin skin, ERSGame game) {
        this(text, skin, "default", game);
    }

    public ERSTextButton(String text, TextButtonStyle style, ERSGame game) {
        super(text, style);
        fontShader = game.assets.get(game.fontShader);
        label = new ERSLabel(text, new Label.LabelStyle(style.font, style.fontColor), game);
        label.setAlignment(Align.center);
        clearChildren();

        add(label).expand().fill();
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        label.getStyle().fontColor = getFontColor();
        super.draw(batch, parentAlpha);
    }
}
