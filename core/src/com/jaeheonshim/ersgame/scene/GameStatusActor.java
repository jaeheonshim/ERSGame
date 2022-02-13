package com.jaeheonshim.ersgame.scene;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.jaeheonshim.ersgame.ERSGame;
import com.jaeheonshim.ersgame.scene.shaded.ERSLabel;

public class GameStatusActor extends Table {
    private ERSLabel statusText;

    public GameStatusActor(ERSGame game) {
        Pixmap bgPixmap = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
        bgPixmap.setColor(new Color(0, 99 / 255f, 237 / 255f, 1));
        bgPixmap.fill();

        setBackground(new TextureRegionDrawable(new TextureRegion(new Texture(bgPixmap))));

        statusText = new ERSLabel("Welcome!", StyleUtil.labelStyle24(game), game);
        statusText.getStyle().fontColor = Color.WHITE;
        statusText.setAlignment(Align.center);
        add(statusText).expand().fill();
    }

    public void setText(String text) {
        statusText.setText(text);
    }
}
