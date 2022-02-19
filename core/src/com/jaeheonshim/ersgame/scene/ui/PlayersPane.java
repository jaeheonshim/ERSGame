package com.jaeheonshim.ersgame.scene.ui;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.jaeheonshim.ersgame.ERSGame;

import java.util.HashMap;
import java.util.Map;

public class PlayersPane extends Table {
    private ERSGame game;
    private Skin skin;

    private Map<String, PlayerElement> playerElementMap = new HashMap<>();

    public PlayersPane(ERSGame game) {
        this.game = game;
        this.skin = game.assets.get(game.uiSkin);
        pad(8);
        top();
    }

    @Override
    public void clearChildren() {
        super.clearChildren();
        playerElementMap.clear();
    }

    public void addElement(PlayerElement element) {
        playerElementMap.put(element.getPlayer().getUuid(), element);
        add(element).top().expandX().fillX().height(60);
        row();
    }

    public PlayerElement getElement(String uuid) {
        return playerElementMap.get(uuid);
    }

    public Map<String, PlayerElement> getPlayerElementMap() {
        return playerElementMap;
    }
}
