package com.jaeheonshim.ersgame.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.jaeheonshim.ersgame.ERSGame;
import com.jaeheonshim.ersgame.scene.shaded.ERSLabel;

public class CreditsScreen implements Screen {
    private final Button backButton;

    private ERSGame game;
    private Skin skin;
    private Stage stage;
    private Table table;

    public CreditsScreen(ERSGame game) {
        this.game = game;
        this.skin = game.assets.get(game.uiSkin);

        stage = new Stage(new ExtendViewport(600, 900));
        table = new Table();
        table.setFillParent(true);
        stage.addActor(table);

        table.padTop(32);

        backButton = new Button(skin, "back");

        backButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(game.mainScreen);
            }
        });

        table.add(backButton).left().padLeft(32).padTop(4).height(64).width(74);

        ERSLabel titleLabel = new ERSLabel("Credits", skin, "heading", game);
        table.add(titleLabel).growX().left().padLeft(16);
        table.row();

        Table creditsTable = new Table();
        table.add(creditsTable).grow().colspan(2);

        ERSLabel creditsTitleLabel = new ERSLabel("Egyptian Ratscrew (ERS)", skin, "bold", game);
        creditsTitleLabel.setColor(Color.BLACK);
        creditsTitleLabel.setAlignment(Align.center);

        creditsTable.top().padTop(32);
        creditsTable.add(creditsTitleLabel);
        creditsTable.row();

        ERSLabel developerLabel = new ERSLabel("Online game by Jaeheon Shim", skin, "default", game);
        developerLabel.setColor(Color.BLACK);
        creditsTable.add(developerLabel).padTop(40);
        creditsTable.row();

        ERSLabel libgdxLabel = new ERSLabel("Developed using libGDX", skin, "default", game);
        libgdxLabel.setColor(Color.BLACK);
        creditsTable.add(libgdxLabel).padTop(40).row();

        Image libgdxImage = new Image(game.assets.get(game.defaultAtlas, TextureAtlas.class).findRegion("libgdx"));
        creditsTable.add(libgdxImage).width(libgdxImage.getWidth() * 0.5f).height(libgdxImage.getHeight() * 0.5f).padTop(8).padBottom(36).row();

        ERSLabel fontLabel = new ERSLabel("Font: \"Poppins\" - Jonny Pinhorn", skin, "small", game);
        fontLabel.setColor(Color.BLACK);
        creditsTable.add(fontLabel).padTop(16);
        creditsTable.row();

        ERSLabel uiLabel = new ERSLabel("UI assets: Kenney (www.kenney.nl)", skin, "small", game);
        uiLabel.setColor(Color.BLACK);
        creditsTable.add(uiLabel).padTop(8);
        creditsTable.row();

        ERSLabel openSourceLabel = new ERSLabel("OPEN SOURCE LIBRARIES", skin, "small", game);
        openSourceLabel.setColor(Color.BLACK);
        creditsTable.add(openSourceLabel).padTop(32).row();

        ERSLabel javaWebsocketOSS = new ERSLabel("TooTallNate/Java-WebSocket\nhttps://github.com/TooTallNate/Java-WebSocket\nLicense: MIT", skin, "small", game);
        javaWebsocketOSS.setColor(Color.BLACK);
        creditsTable.add(javaWebsocketOSS).padTop(16);
        creditsTable.row();

        ERSLabel gdxWebsocketOSS = new ERSLabel("MrStahlfelge/gdx-websockets\nhttps://github.com/MrStahlfelge/gdx-websockets\nLicense: Apache 2.0", skin, "small", game);
        gdxWebsocketOSS.setColor(Color.BLACK);
        creditsTable.add(gdxWebsocketOSS).padTop(16);
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
