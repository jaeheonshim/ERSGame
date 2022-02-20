package com.jaeheonshim.ersgame.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.jaeheonshim.ersgame.ERSGame;
import com.jaeheonshim.ersgame.scene.action.DisappearAction;
import com.jaeheonshim.ersgame.scene.shaded.ERSLabel;

public class GameOverScreen implements Screen {
    public static final float FADE_TIMER = 2f;

    private ERSGame game;

    private ERSLabel titleLabel;
    private Stage stage;
    private Table preTable;

    private ShapeRenderer shapeRenderer;

    private float timer;
    private Screen nextScreen;

    public GameOverScreen(ERSGame game, Screen nextScreen) {
        this.game = game;
        this.nextScreen = nextScreen;

        Skin skin = game.assets.get(game.uiSkin);

        stage = new Stage(new ExtendViewport(600, 900));
        preTable = new Table();
        preTable.setFillParent(true);
        stage.addActor(preTable);

        titleLabel = new ERSLabel("Game Over!", skin, "heading", game);

        preTable.add(titleLabel);

        shapeRenderer = new ShapeRenderer();
    }

    @Override
    public void show() {
        timer = 0;
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(Color.WHITE);
        stage.act(delta);
        stage.draw();
        timer += delta;

        Gdx.gl.glEnable(Gdx.gl.GL_BLEND);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(1, 1, 1, timer / FADE_TIMER);
        shapeRenderer.rect(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        shapeRenderer.end();
        Gdx.gl.glDisable(Gdx.gl.GL_BLEND);

        if(timer >= FADE_TIMER + 0.5f) {
            game.setScreen(nextScreen);
        }
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
        shapeRenderer.dispose();
    }
}
