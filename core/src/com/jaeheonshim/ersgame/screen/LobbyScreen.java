package com.jaeheonshim.ersgame.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.jaeheonshim.ersgame.ERSGame;
import com.jaeheonshim.ersgame.game.GameState;
import com.jaeheonshim.ersgame.game.GameStateManager;
import com.jaeheonshim.ersgame.game.GameStateUpdateListener;
import com.jaeheonshim.ersgame.game.Player;
import com.jaeheonshim.ersgame.scene.shaded.ERSLabel;
import com.jaeheonshim.ersgame.scene.ui.PlayerElement;
import com.jaeheonshim.ersgame.scene.ui.PlayersPane;

public class LobbyScreen implements Screen, GameStateUpdateListener {
    private ERSGame game;
    private Stage stage;
    private Table table;
    private Skin skin;

    private ERSLabel gameCodeLabel;
    private PlayersPane playersPane;

    public LobbyScreen(ERSGame game) {
        this.game = game;

        stage = new Stage(new ExtendViewport(600, 900));
        table = new Table();
        table.setFillParent(true);
        stage.addActor(table);

        skin = game.assets.get(game.uiSkin);

        gameCodeLabel = new ERSLabel("129323", skin, "bluepanel", game);
        gameCodeLabel.setAlignment(Align.center);
        table.add(gameCodeLabel).width(150).height(60).expandX();
        table.row();

        playersPane = new PlayersPane(game);
        table.add(new ScrollPane(playersPane)).expandY().expandX().fill().padTop(16);

        GameStateManager.getInstance().registerListener(this);
    }

    @Override
    public void onUpdate(GameState newGameState) {
        if(newGameState == null) return;

        playersPane.clearChildren();
        for(Player player : newGameState.getPlayerList()) {
            playersPane.add(new PlayerElement(game, player.getUsername())).top().expandX().expandY().fillX().height(60);
        }
    }

    @Override
    public void show() {
        onUpdate(GameStateManager.getInstance().getGameState());
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(Color.WHITE);

        stage.act(delta);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}
