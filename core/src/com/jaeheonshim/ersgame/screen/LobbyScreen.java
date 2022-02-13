package com.jaeheonshim.ersgame.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.jaeheonshim.ersgame.ERSGame;
import com.jaeheonshim.ersgame.scene.PlayersList;
import com.jaeheonshim.ersgame.scene.StyleUtil;
import com.jaeheonshim.ersgame.scene.shaded.ERSLabel;

public class LobbyScreen implements Screen {
    private ERSGame game;
    private Stage stage;
    private Table table;

    public LobbyScreen(ERSGame game) {
        this.game = game;

        stage = new Stage(new ExtendViewport(700, 1000));
        table = new Table();
        table.setFillParent(true);
        stage.addActor(table);

        ERSLabel titleLabel = new ERSLabel("Jaeheon's game", StyleUtil.labelStyle64(game), game);
        titleLabel.setAlignment(Align.center);

        ERSLabel playersLabel = new ERSLabel("10 players", StyleUtil.labelStyle24(game), game);
        playersLabel.setAlignment(Align.right);

        table.add(titleLabel).top().center().expandX().fill();
        table.row();
        table.add(new PlayersList(game)).padTop(20).top().height(700).expandX().fill();
        table.row();
        table.add(playersLabel).fill().padTop(8).expandX();
        table.pad(32);
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(new Color(232 / 255f, 232 / 255f, 232 / 255f, 1));
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
