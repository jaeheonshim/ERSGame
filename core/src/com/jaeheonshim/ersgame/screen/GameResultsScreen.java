package com.jaeheonshim.ersgame.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.jaeheonshim.ersgame.ERSGame;
import com.jaeheonshim.ersgame.game.GameActionListener;
import com.jaeheonshim.ersgame.game.GameStateManager;
import com.jaeheonshim.ersgame.game.model.GameState;
import com.jaeheonshim.ersgame.game.model.Player;
import com.jaeheonshim.ersgame.net.NetManager;
import com.jaeheonshim.ersgame.net.model.GameAction;
import com.jaeheonshim.ersgame.net.packet.GameActionPacket;
import com.jaeheonshim.ersgame.scene.shaded.ERSLabel;
import com.jaeheonshim.ersgame.scene.shaded.ERSTextButton;

public class GameResultsScreen implements Screen, GameActionListener {
    private final ERSLabel winnerLabel;
    private ERSGame game;

    private Stage stage;
    private Table table;

    public GameResultsScreen(ERSGame game) {
        this.game = game;
        Skin skin = game.assets.get(game.uiSkin);

        stage = new Stage(new ExtendViewport(600, 900));
        table = new Table();
        table.setFillParent(true);
        stage.addActor(table);

        ERSLabel titleLabel = new ERSLabel("Game Over!", skin, "heading", game);
        titleLabel.setAlignment(Align.center);
        winnerLabel = new ERSLabel("Winner: jae", skin, game);
        winnerLabel.setAlignment(Align.center);
        winnerLabel.setColor(Color.BLACK);

        ERSTextButton playAgain = new ERSTextButton("Play again", skin, "green", game);
        ERSTextButton exit = new ERSTextButton("Exit", skin, game);

        Image trophyImage = new Image(game.assets.get(game.defaultAtlas, TextureAtlas.class).findRegion("trophy"));

        table.pad(16);
        table.add(titleLabel).growX().expandY().top().row();
        table.add(trophyImage).growX().size(400, 400).center().padBottom(8).row();
        table.add(winnerLabel).growX().padBottom(8).row();
        table.add(playAgain).growX().padBottom(16).row();
        table.add(exit).growX().expandY().top();

        exit.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                NetManager.getInstance().send(new GameActionPacket(GameAction.LEAVE_GAME));
            }
        });

        playAgain.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(game.lobbyScreen);
            }
        });

        GameStateManager.getInstance().registerActionListener(this);
    }

    @Override
    public void onLeaveGame() {
        GameStateManager.getInstance().setGameState(null);
        game.setScreen(game.mainScreen);
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
        GameState gameState = GameStateManager.getInstance().getGameState();

        winnerLabel.setText("Winner: " + gameState.getWinner());
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(239 / 255f, 244 / 255f, 243 / 255f, 1);
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
