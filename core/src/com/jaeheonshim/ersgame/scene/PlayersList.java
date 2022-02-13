package com.jaeheonshim.ersgame.scene;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;
import com.jaeheonshim.ersgame.ERSGame;
import com.jaeheonshim.ersgame.game.GameState;
import com.jaeheonshim.ersgame.game.Player;

import java.util.ArrayList;
import java.util.List;

public class PlayersList extends ScrollPane {
    private ERSGame game;
    private GameState gameState;

    private Table table = new Table();

    private List<PlayerListItem> playerListItemList = new ArrayList<>();

    public PlayersList(ERSGame game, GameState gameState) {
        super(null);
        this.game = game;
        this.gameState = gameState;

        for(Player player : gameState.getPlayers()) {
            PlayerListItem listItem = new PlayerListItem(game, player);
            playerListItemList.add(listItem);

            table.add(listItem).expandX().fillX().height(68);
            table.row();
        }

        setActor(table);
    }
}
