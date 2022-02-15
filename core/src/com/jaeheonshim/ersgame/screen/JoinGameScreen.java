package com.jaeheonshim.ersgame.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.jaeheonshim.ersgame.ERSGame;
import com.jaeheonshim.ersgame.scene.shaded.ERSLabel;
import com.jaeheonshim.ersgame.scene.shaded.ERSTextButton;
import com.jaeheonshim.ersgame.scene.shaded.ERSTextField;

public class JoinGameScreen implements Screen {
    private final ERSTextField nameField;
    private final ERSTextField codeField;
    private final ERSTextButton joinButton;
    private ERSGame game;
    private Skin skin;
    private Stage stage;
    private Table table;

    public JoinGameScreen(ERSGame game) {
        this.game = game;
        this.skin = game.assets.get(game.uiSkin);

        stage = new Stage(new ExtendViewport(600, 900));
        table = new Table();
        table.setFillParent(true);
        stage.addActor(table);

        ERSLabel titleLabel = new ERSLabel("Join Game", skin, game);
        titleLabel.setAlignment(Align.center);
        table.top();
        table.add(titleLabel).top().expand().fillX().padTop(32);
        table.row();

        table.center();

        nameField = new ERSTextField("", skin, game);
        nameField.setMessageText("Username");
        table.add(nameField).fillX().padLeft(32).padRight(32);
        table.row();

        codeField = new ERSTextField("", skin, game);
        codeField.setMessageText("Join Code");
        table.add(codeField).fillX().padLeft(32).padRight(32);
        table.row();

        joinButton = new ERSTextButton("Join Game", skin, game);
        table.add(joinButton).fillX().expandY().top().padLeft(38).padRight(38).padTop(16);
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
