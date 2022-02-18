package com.jaeheonshim.ersgame.scene.ui;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.jaeheonshim.ersgame.ERSGame;

public class PlayersPane extends Table {
    private ERSGame game;
    private Skin skin;

    public PlayersPane(ERSGame game) {
        this.game = game;
        this.skin = game.assets.get(game.uiSkin);
        pad(8);
        top();
    }

    public void addElement(PlayerElement element) {
        add(element).top().expandX().fillX().height(60);
        row();
    }
}
