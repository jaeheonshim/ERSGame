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
import com.jaeheonshim.ersgame.game.Player;

public class PlayerListItem extends Table {
    private String name;
    private NinePatchDrawable border;
    private Label.LabelStyle labelStyle;
    private Label cardCountLabel;

    public PlayerListItem(ERSGame game, Player player) {
        this.name = player.getName();
        border = new NinePatchDrawable(new NinePatch(game.assets.get(game.uiBorder, Texture.class), 8, 8, 8, 8));
        labelStyle = new Label.LabelStyle(game.assets.get(game.poppins48, BitmapFont.class), Color.BLACK);
        cardCountLabel = new Label(Integer.toString(player.getCards().size()), labelStyle);

        setBackground(border);
        add(new Label(name, labelStyle)).expandX().fill().pad(0, 8, 0, 8);
        add(cardCountLabel).right();
    }
}
