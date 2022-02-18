package com.jaeheonshim.ersgame.scene.ui;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;
import com.jaeheonshim.ersgame.ERSGame;
import com.jaeheonshim.ersgame.game.GameState;
import com.jaeheonshim.ersgame.game.GameStateManager;
import com.jaeheonshim.ersgame.game.Player;
import com.jaeheonshim.ersgame.scene.shaded.ERSLabel;

public class PlayerElement extends Table {
    private ERSGame game;
    private Skin skin;

    private ERSLabel nameLabel;
    private Image adminImage;

    public PlayerElement(ERSGame game, Player player) {
        this.game = game;
        this.skin = game.assets.get(game.uiSkin);

        NinePatchDrawable backgroundDrawable = new NinePatchDrawable(skin.getPatch("grey_panel"));
        setBackground(backgroundDrawable);

        left();
        String stylename = GameStateManager.getInstance().getSelfPlayer().equals(player) ? "bold" : "default";
        nameLabel = new ERSLabel(player.getUsername(), skin, stylename, game);
        nameLabel.setColor(Color.BLACK);
        add(nameLabel).padLeft(16);

        adminImage = new Image(game.assets.get(game.defaultAtlas, TextureAtlas.class).findRegion("crown"));
        add(adminImage).size(50, 50).padLeft(8);
        adminImage.setVisible(GameStateManager.getInstance().isAdmin(player));
    }
}
