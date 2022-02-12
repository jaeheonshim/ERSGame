package com.jaeheonshim.ersgame.screen;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.jaeheonshim.ersgame.CardType;
import com.jaeheonshim.ersgame.ERSGame;
import com.jaeheonshim.ersgame.scene.CardActor;

public class GameScreen implements Screen {
    private ERSGame game;
    private Stage stage;
    private Table table;
    private CardActor actor;

    public GameScreen(ERSGame game) {
        this.game = game;
    }

    @Override
    public void show() {
        stage = new Stage(new FitViewport(800, 1500));

        table = new Table();
        table.setFillParent(true);
        actor = new CardActor(game);
        table.add(actor);


        stage.addActor(table);
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(Color.BLUE);
        stage.act(delta);
        stage.draw();

        actor.setType(CardType.values()[(actor.getType().ordinal() + 1) % CardType.values().length]);
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
