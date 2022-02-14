package com.jaeheonshim.ersgame.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.jaeheonshim.ersgame.ERSGame;
import com.jaeheonshim.ersgame.game.CardType;
import com.jaeheonshim.ersgame.scene.PileDisplayActor;
import com.jaeheonshim.ersgame.scene.shaded.ERSLabel;
import com.jaeheonshim.ersgame.scene.shaded.ERSTextButton;

import java.util.Arrays;
import java.util.List;

public class MainScreen implements Screen {
    private ERSGame game;
    private Skin skin;
    private Stage stage;
    private Table table;

    private PileDisplayActor sampleDisplay;
    private ERSTextButton createGameButton;
    private ERSTextButton joinGameButton;

    public MainScreen(ERSGame game) {
        this.game = game;
        skin = game.assets.get(game.uiSkin);
        stage = new Stage(new ExtendViewport(500, 800));
        table = new Table();
        table.setFillParent(true);
        stage.addActor(table);

        sampleDisplay = new PileDisplayActor(game, () -> Arrays.asList(CardType.BACK, CardType.BACK, CardType.BACK));
        table.add(sampleDisplay).top().padBottom(32).padLeft(50);
        table.row();

        ERSLabel titleLabel = new ERSLabel("Egyptian Ratscrew", skin, "bold", game);
        titleLabel.setAlignment(Align.center);
        table.add(titleLabel).expandX().fill().padBottom(20);
        table.row();

        createGameButton = new ERSTextButton("Create Game", skin, game);
        table.add(createGameButton).padBottom(8);
        table.row();

        joinGameButton = new ERSTextButton("Join Game", skin, "green", game);
        table.add(joinGameButton);
    }

    @Override
    public void show() {
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