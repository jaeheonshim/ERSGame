package com.jaeheonshim.ersgame.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.jaeheonshim.ersgame.game.CardType;
import com.jaeheonshim.ersgame.ERSGame;
import com.jaeheonshim.ersgame.game.GameState;
import com.jaeheonshim.ersgame.scene.CardActor;
import com.jaeheonshim.ersgame.scene.PileDisplayActor;
import com.jaeheonshim.ersgame.scene.PlayersList;

public class GameScreen implements Screen {
    private ERSGame game;
    private Stage stage;
    private Table table;

    private PileDisplayActor pileDisplayActor;
    private PlayersList playersList;

    public GameScreen(ERSGame game) {
        this.game = game;

        stage = new Stage(new ExtendViewport(1200, 1700));
        table = new Table();

        pileDisplayActor = new PileDisplayActor(game, new GameState());
        playersList = new PlayersList(game);
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);

        table.setFillParent(true);
        table.add(playersList).expandX().fillX().maxHeight(400).top();
        table.row();
        table.add(pileDisplayActor).padLeft(200).expandY().top().padTop(140);

        stage.addActor(table);
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
