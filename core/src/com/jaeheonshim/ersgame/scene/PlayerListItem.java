package com.jaeheonshim.ersgame.scene;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.VerticalGroup;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;
import com.jaeheonshim.ersgame.ERSGame;

public class PlayerListItem extends Table {
    private String name;
    private NinePatchDrawable border;
    private Label.LabelStyle labelStyle;

    public PlayerListItem(ERSGame game) {
        border = new NinePatchDrawable(new NinePatch(game.assets.get(game.uiBorder, Texture.class), 8, 8, 8, 8));
        labelStyle = new Label.LabelStyle(game.assets.get(game.poppins48, BitmapFont.class), Color.BLACK);

        setBackground(border);
        add(new Label("Jaeheon", labelStyle)).expandX().fill().pad(8);
    }
}
