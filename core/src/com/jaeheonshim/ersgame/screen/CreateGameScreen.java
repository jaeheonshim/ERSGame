package com.jaeheonshim.ersgame.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.jaeheonshim.ersgame.ERSGame;
import com.jaeheonshim.ersgame.net.NetManager;
import com.jaeheonshim.ersgame.net.packet.CreateGamePacket;
import com.jaeheonshim.ersgame.scene.shaded.ERSLabel;
import com.jaeheonshim.ersgame.scene.shaded.ERSTextButton;
import com.jaeheonshim.ersgame.scene.shaded.ERSTextField;

public class CreateGameScreen implements Screen {
    private final ERSTextField nameField;
    private final ERSTextButton createButton;
    private final Button backButton;

    private ERSGame game;
    private Skin skin;
    private Stage stage;
    private Table table;

    public CreateGameScreen(ERSGame game) {
        this.game = game;
        this.skin = game.assets.get(game.uiSkin);

        stage = new Stage(new ExtendViewport(600, 900));
        table = new Table();
        table.setFillParent(true);
//        table.setDebug(true);
        stage.addActor(table);

        table.padTop(32);

        backButton = new Button(skin, "back");

        backButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(game.mainScreen);
            }
        });

        table.add(backButton).center().left().padLeft(32).padTop(4).height(64).width(74);

        ERSLabel titleLabel = new ERSLabel("Create Game", skin, "heading", game);
        table.add(titleLabel).expandX().center().left().padLeft(28);
        table.row();

        table.center();

        nameField = new ERSTextField("", skin, game);
        nameField.setMessageText("Username");
        table.add(nameField).expandY().fillX().padLeft(32).padRight(32).colspan(2).bottom();
        table.row();

        createButton = new ERSTextButton("Create Game", skin, game);
        table.add(createButton).fillX().expandY().top().padLeft(38).padRight(38).padTop(16).colspan(2);

        createButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                String username = nameField.getText();
                if(username.trim().isEmpty()) return;

                CreateGamePacket packet = new CreateGamePacket(username);

                NetManager.getInstance().send(packet);
            }
        });
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
