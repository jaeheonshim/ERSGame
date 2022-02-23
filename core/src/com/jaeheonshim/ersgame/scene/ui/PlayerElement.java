package com.jaeheonshim.ersgame.scene.ui;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;
import com.jaeheonshim.ersgame.ERSGame;
import com.jaeheonshim.ersgame.game.GameStateManager;
import com.jaeheonshim.ersgame.game.model.Player;
import com.jaeheonshim.ersgame.scene.action.DisappearAction;
import com.jaeheonshim.ersgame.scene.shaded.ERSLabel;

public class PlayerElement extends Table {
    private ERSGame game;
    private Skin skin;

    private ERSLabel nameLabel;
    private ERSLabel cardCount;
    private ERSLabel pointChange;
    private Image adminImage;
    private Image currentTurnImage;

    private Player player;

    public PlayerElement(ERSGame game, Player player, boolean displayCards) {
        this.game = game;
        this.skin = game.assets.get(game.uiSkin);
        this.player = player;

        NinePatchDrawable backgroundDrawable = new NinePatchDrawable(skin.getPatch("grey_panel"));
        setBackground(backgroundDrawable);

        left();

        currentTurnImage = new Image(game.assets.get(game.defaultAtlas, TextureAtlas.class).findRegion("green_circle"));
        add(currentTurnImage).size(15, 15).padLeft(8);
        currentTurnImage.setVisible(false);

        String stylename = GameStateManager.getInstance().getSelfPlayer().equals(player) ? "bold" : "default";
        nameLabel = new ERSLabel(player.getUsername(), skin, stylename, game);
        nameLabel.setColor(Color.BLACK);
        add(nameLabel).padLeft(16);

        adminImage = new Image(game.assets.get(game.defaultAtlas, TextureAtlas.class).findRegion("crown"));
        add(adminImage).size(50, 50).padLeft(8);
        adminImage.setVisible(GameStateManager.getInstance().isAdmin(player));

        pointChange = new ERSLabel("+0", skin, game);
        pointChange.setColor(skin.getColor("success"));
        pointChange.setVisible(false);
        add(pointChange).expandX().right().padRight(16);

        cardCount = new ERSLabel(Integer.toString(player.getCardCount()), skin, game);
        cardCount.setColor(Color.BLACK);
        add(cardCount).right().padRight(16);
        cardCount.setVisible(displayCards);
    }

    public PlayerElement(ERSGame game, Player player) {
        this(game, player, false);
    }

    public void setCurrentTurn(boolean b) {
        currentTurnImage.setVisible(b);
    }

    public void setPointChange(int amount) {
        pointChange.clearActions();
        pointChange.setVisible(true);
        if(amount > 0) {
            pointChange.setText("+" + amount);
            pointChange.setColor(skin.getColor("success"));
        } else {
            pointChange.setText(amount);
            pointChange.setColor(skin.getColor("warning"));
        }
        pointChange.addAction(new DisappearAction(3));
    }

    public void setCardCount(int n) {
        cardCount.setText(Integer.toString(n));
    }

    public Player getPlayer() {
        return player;
    }
}
