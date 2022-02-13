package com.jaeheonshim.ersgame.scene;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;
import com.jaeheonshim.ersgame.ERSGame;

public class PlayersList extends ScrollPane {
    private ERSGame game;

    private Table table = new Table();

    public PlayersList(ERSGame game) {
        super(null);
        this.game = game;

        table.add(new PlayerListItem(game)).expandX().fill().height(64);
        table.row();
table.add(new PlayerListItem(game)).expandX().fill().height(64);
        table.row();
table.add(new PlayerListItem(game)).expandX().fill().height(64);
        table.row();
table.add(new PlayerListItem(game)).expandX().fill().height(64);
        table.row();
table.add(new PlayerListItem(game)).expandX().fill().height(64);
        table.row();
table.add(new PlayerListItem(game)).expandX().fill().height(64);
        table.row();
table.add(new PlayerListItem(game)).expandX().fill().height(64);
        table.row();
table.add(new PlayerListItem(game)).expandX().fill().height(64);
        table.row();

        setActor(table);
    }
}
