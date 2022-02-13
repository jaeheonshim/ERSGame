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
import java.util.Collections;
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

        List<Player> players = new ArrayList<>(gameState.getPlayers());
        Collections.sort(players);

        for(Player player : players) {
            PlayerListItem listItem = new PlayerListItem(game, player, gameState);
            playerListItemList.add(listItem);

            table.add(listItem).expandX().fillX().height(72);
            table.row();
        }

        setActor(table);
    }

    public void updateState() {
        for(PlayerListItem listItem : playerListItemList) {
            listItem.updateState();
        }
    }
}
