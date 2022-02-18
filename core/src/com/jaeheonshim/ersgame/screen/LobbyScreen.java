package com.jaeheonshim.ersgame.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.jaeheonshim.ersgame.ERSGame;
import com.jaeheonshim.ersgame.game.*;
import com.jaeheonshim.ersgame.net.GameAction;
import com.jaeheonshim.ersgame.net.NetManager;
import com.jaeheonshim.ersgame.net.packet.GameActionPacket;
import com.jaeheonshim.ersgame.scene.shaded.ERSLabel;
import com.jaeheonshim.ersgame.scene.shaded.ERSTextButton;
import com.jaeheonshim.ersgame.scene.ui.PlayerElement;
import com.jaeheonshim.ersgame.scene.ui.PlayersPane;

public class LobbyScreen implements Screen, GameStateUpdateListener, GameActionListener {
    private ERSGame game;
    private Stage stage;
    private Table table;
    private Skin skin;

    private ERSLabel gameCodeLabel;
    private PlayersPane playersPane;
    private ERSTextButton startButton;

    public LobbyScreen(ERSGame game) {
        this.game = game;

        stage = new Stage(new ExtendViewport(600, 900));
        table = new Table();
        table.setFillParent(true);
        stage.addActor(table);

        skin = game.assets.get(game.uiSkin);

        gameCodeLabel = new ERSLabel("000000", skin, "bluepanel", game);
        gameCodeLabel.setAlignment(Align.center);
        table.add(gameCodeLabel).width(150).height(60).expandX().padTop(8);
        table.row();

        playersPane = new PlayersPane(game);
        table.add(new ScrollPane(playersPane)).expandY().expandX().fill().padTop(16).top();
        table.row();

        startButton = new ERSTextButton("Start", skin, "green", game);
        table.add(startButton).expandX().fill().bottom().pad(8);
        startButton.setVisible(false);

        startButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                NetManager.getInstance().send(new GameActionPacket(GameAction.START));
            }
        });

        GameStateManager.getInstance().registerListener(this);
        GameStateManager.getInstance().registerActionListener(this);
    }

    @Override
    public void onUpdate(GameState newGameState) {
        if(newGameState == null) return;
        if(!game.getScreen().equals(this)) return;

        playersPane.clearChildren();
        for(String uuid : newGameState.getPlayerList()) {
            Player player = newGameState.getPlayer(uuid);
            playersPane.add(new PlayerElement(game, player)).top().expandX().fillX().height(60);
            playersPane.row();
        }

        gameCodeLabel.setText(newGameState.getGameCode());

        if(GameStateManager.getInstance().isAdmin()) {
            startButton.setVisible(true);
        }
    }

    @Override
    public void onGameStart() {
        if(!game.getScreen().equals(this)) return;
        game.setScreen(game.gameScreen);
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
