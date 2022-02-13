package com.jaeheonshim.ersgame.scene;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;
import com.jaeheonshim.ersgame.ERSGame;
import com.jaeheonshim.ersgame.game.GameState;
import com.jaeheonshim.ersgame.game.Player;
import com.jaeheonshim.ersgame.scene.shaded.ERSLabel;

public class PlayerListItem extends Table {
    private Player player;
    private GameState gameState;

    private NinePatchDrawable border;
    private Label.LabelStyle labelStyle;
    private Label cardCountLabel;

    public PlayerListItem(ERSGame game, Player player, GameState gameState) {
        this.player = player;
        this.gameState = gameState;
        border = new NinePatchDrawable(new NinePatch(game.assets.get(game.uiBorder, Texture.class), 8, 8, 8, 8));
        labelStyle = new Label.LabelStyle(game.assets.get(game.poppins64, BitmapFont.class), Color.BLACK);
        cardCountLabel = new ERSLabel(Integer.toString(player.getCards().size()), labelStyle, game);

        setBackground(border);
        add(new ERSLabel(player.getName(), labelStyle, game)).expandX().fill().pad(0, 8, 0, 8);
        add(cardCountLabel).right();

        updateState();
    }

    public void updateState() {
        cardCountLabel.setText(Integer.toString(player.getCards().size()));

        if(gameState.getCurrentTurn() == player) {
            setColor(Color.YELLOW);
        }
    }
}
