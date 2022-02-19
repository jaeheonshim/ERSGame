package com.jaeheonshim.ersgame.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.DelayAction;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.badlogic.gdx.scenes.scene2d.actions.VisibleAction;
import com.badlogic.gdx.scenes.scene2d.ui.Cell;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.jaeheonshim.ersgame.ERSGame;
import com.jaeheonshim.ersgame.game.CardType;
import com.jaeheonshim.ersgame.net.ConnectionStatus;
import com.jaeheonshim.ersgame.net.NetManager;
import com.jaeheonshim.ersgame.net.listener.ConnectStatusListener;
import com.jaeheonshim.ersgame.scene.OverlayStage;
import com.jaeheonshim.ersgame.scene.ui.ConnectionStatusLabel;
import com.jaeheonshim.ersgame.scene.game.PileDisplayActor;
import com.jaeheonshim.ersgame.scene.shaded.ERSLabel;
import com.jaeheonshim.ersgame.scene.shaded.ERSTextButton;
import com.jaeheonshim.ersgame.scene.shaded.ERSWindow;
import com.jaeheonshim.ersgame.scene.ui.UIMessageLabel;

import java.util.Arrays;

public class MainScreen implements Screen, ConnectStatusListener {
    private ERSGame game;
    private Skin skin;
    private Stage stage;
    private Table table;

    private PileDisplayActor sampleDisplay;
    private ERSTextButton createGameButton;
    private ERSTextButton joinGameButton;
    private ERSTextButton creditsButton;

    private ConnectionStatusLabel connectionStatus;
    private ERSWindow connectingWindow;

    private Screen pendingScreen;

    public MainScreen(ERSGame game) {
        this.game = game;
        skin = game.assets.get(game.uiSkin);
        stage = new Stage(new ExtendViewport(600, 900));
        table = new Table();
        table.setFillParent(true);
        stage.addActor(table);

        sampleDisplay = new PileDisplayActor(game, () -> Array.with(CardType.BACK, CardType.BACK, CardType.BACK));
        table.add(sampleDisplay).top().padBottom(32).padLeft(50).padTop(50);
        table.row();

        ERSLabel titleLabel = new ERSLabel("Egyptian Ratscrew", skin, "bold", game);
        titleLabel.setAlignment(Align.center);
        table.add(titleLabel).expandX().fill().padBottom(20);
        table.row();

//        ERSTextField nameField = new ERSTextField("Username", skin, game);
//        table.add(nameField).expandX().width(565).padBottom(8).center();
//        table.row();

        createGameButton = new ERSTextButton("Create Game", skin, game);
        table.add(createGameButton).expandX().fill().center().padBottom(8).padLeft(32).padRight(32);
        table.row();

        joinGameButton = new ERSTextButton("Join Game", skin, "green", game);
        table.add(joinGameButton).expandX().fill().center().padBottom(8).padLeft(32).padRight(32);
        table.row();

        creditsButton = new ERSTextButton("Credits", skin, "yellow-outline", game);
        table.add(creditsButton).expandX().fill().center().padBottom(20).padLeft(32).padRight(32).height(60);
        table.row();

        connectionStatus = new ConnectionStatusLabel(skin, game);
        table.add(connectionStatus).expandY().bottom().right().pad(8);
        table.row();

        joinGameButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                trySwitchScreen(game.joinGameScreen);
            }
        });

        createGameButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                trySwitchScreen(game.createGameScreen);
            }
        });

        creditsButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(game.creditsScreen);
            }
        });

        connectingWindow = new ERSWindow("Connecting...", skin, game);
        connectingWindow.setWidth(500);
        connectingWindow.setPosition(stage.getWidth() / 2 - connectingWindow.getWidth() / 2, stage.getHeight() / 2 - connectingWindow.getHeight() / 2);
        connectingWindow.setVisible(false);
        connectingWindow.setModal(true);
        stage.addActor(connectingWindow);
    }

    public void trySwitchScreen(Screen screen) {
        if (NetManager.getInstance().getConnectionStatus() != ConnectionStatus.CONNECTED) {
            pendingScreen = screen;
            displayConnectingWindow("Please wait while we connect to the server...");
            NetManager.getInstance().reconnect();
        } else {
            game.setScreen(screen);
        }
    }

    @Override
    public void onStatusChange(ConnectionStatus newStatus) {
        if (newStatus == ConnectionStatus.CONNECTING) return;

        if (pendingScreen != null) {
            Gdx.app.postRunnable(() -> {
                if (newStatus == ConnectionStatus.CONNECTED) {
                    game.setScreen(pendingScreen);
                } else {
                    displayConnectingWindow("Failed to connect!");
                    delayHideConnectingWindow();
                }

                pendingScreen = null;
            });
        }
    }

    public void displayConnectingWindow(String text) {
        connectingWindow.setPosition(stage.getWidth() / 2 - connectingWindow.getWidth() / 2, stage.getHeight() / 2 - connectingWindow.getHeight() / 2);
        connectingWindow.clearChildren();
        ERSLabel connectingWindowText = new ERSLabel(text, skin, "small", game);
        connectingWindowText.setColor(Color.WHITE);
        connectingWindow.add(connectingWindowText).center();
        connectingWindow.setVisible(true);
    }

    public void delayHideConnectingWindow() {
        VisibleAction action = new VisibleAction();
        action.setVisible(false);
        DelayAction delayAction = new DelayAction();
        delayAction.setDuration(3);

        SequenceAction sequenceAction = new SequenceAction(delayAction, action);
        connectingWindow.addAction(sequenceAction);
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
        NetManager.getInstance().setConnectStatusListener(this);
        connectingWindow.setVisible(false);
        NetManager.getInstance().connect();
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
