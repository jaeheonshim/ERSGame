package com.jaeheonshim.ersgame.scene.ui;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;
import com.jaeheonshim.ersgame.ERSGame;
import com.jaeheonshim.ersgame.scene.shaded.ERSLabel;

public class PlayerElement extends Table {
    private ERSGame game;
    private Skin skin;

    private ERSLabel nameLabel;

    public PlayerElement(ERSGame game) {
        this.game = game;
        this.skin = game.assets.get(game.uiSkin);

        NinePatchDrawable backgroundDrawable = new NinePatchDrawable(skin.getPatch("grey_panel"));
        setBackground(backgroundDrawable);

        nameLabel = new ERSLabel("Jaeheon", skin, game);
        nameLabel.setColor(Color.BLACK);
        add(nameLabel);
    }
}
