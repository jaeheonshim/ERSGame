package com.jaeheonshim.ersgame.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.jaeheonshim.ersgame.ERSGame;
import com.jaeheonshim.ersgame.game.*;
import com.jaeheonshim.ersgame.net.NetManager;
import com.jaeheonshim.ersgame.scene.GameStatusActor;
import com.jaeheonshim.ersgame.scene.PlayersList;
import com.jaeheonshim.ersgame.scene.StyleUtil;
import com.jaeheonshim.ersgame.scene.shaded.ERSLabel;
import com.jaeheonshim.ersgame.util.RandomNameGenerator;

public class LobbyScreen extends GameStateEventListener implements Screen, GameStateUpdateListener {
    private final GameStatusActor gameStatus;
    private ERSGame game;
    private Stage stage;
    private Table table;

    private ERSLabel playersLabel;
    private ERSLabel titleLabel;

    public LobbyScreen(ERSGame game) {
        this.game = game;

        stage = new Stage(new ExtendViewport(700, 1000));
        table = new Table();
        table.setFillParent(true);
        stage.addActor(table);

        titleLabel = new ERSLabel("", StyleUtil.labelStyle64(game), game);
        titleLabel.setAlignment(Align.center);

        playersLabel = new ERSLabel(getPlayerCount() + " players", StyleUtil.labelStyle24(game), game);
        playersLabel.setAlignment(Align.left);

        table.add(titleLabel).expandX().fill();
        table.row();
        table.add(playersLabel).fill().padTop(8).expandX().padLeft(20).padRight(20).padBottom(4);
        table.row();
        table.add(new PlayersList(game)).height(700).expand().fill().padLeft(20).padRight(20).top();
        table.row();
        table.bottom();
        gameStatus = new GameStatusActor(game);
        table.add(gameStatus).expandX().fill().height(60).bottom().padTop(20);

        setTitleLabel();
        GameStateManager.getInstance().addUpdateListener(this);
        GameStateManager.getInstance().getGameState().setGameEventListener(this);
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
        NetManager.getInstance().join("000000", RandomNameGenerator.randomName());
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(new Color(232 / 255f, 232 / 255f, 232 / 255f, 1));
        stage.act(delta);
        stage.draw();
    }

    public void setTitleLabel() {
        GameState gameState = GameStateManager.getInstance().getGameState();
        if(gameState.getGameAdmin() == null) return;
        titleLabel.setText(String.format("%s's game", gameState.getGameAdmin().getName()));
    }

    public int getPlayerCount() {
        return GameStateManager.getInstance().getGameState().getPlayerMap().size();
    }

    @Override
    public void onPlayerJoin(GameState game, Player player) {
        gameStatus.setText(String.format("%s joined!", player.getName()));
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

    @Override
    public void updateOccurred(GameState gameState) {
        setTitleLabel();
        playersLabel.setText(getPlayerCount() + " players");
    }
}
