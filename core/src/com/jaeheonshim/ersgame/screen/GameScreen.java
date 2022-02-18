package com.jaeheonshim.ersgame.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.jaeheonshim.ersgame.game.*;
import com.jaeheonshim.ersgame.ERSGame;
import com.jaeheonshim.ersgame.scene.game.CardActor;
import com.jaeheonshim.ersgame.scene.game.PileDisplayActor;
import com.jaeheonshim.ersgame.scene.ui.PlayerElement;
import com.jaeheonshim.ersgame.scene.ui.PlayersPane;

import java.util.Arrays;

public class GameScreen implements Screen, GameStateUpdateListener {
    private ERSGame game;
    private Stage stage;
    private Table table;

    private PileDisplayActor pileDisplayActor;
    private PlayersPane playersPane;
    private CardActor animationCard;
    private ScrollPane playerScrollPane;

    public GameScreen(ERSGame game) {
        this.game = game;

        stage = new Stage(new ExtendViewport(600, 900));
        table = new Table();

        pileDisplayActor = new PileDisplayActor(game, () -> Arrays.asList(CardType.CLOVER_A, CardType.CLOVER_4, CardType.DIAMOND_A));
        playersPane = new PlayersPane(game);
        playerScrollPane = new ScrollPane(playersPane);

        animationCard = new CardActor(game);
        animationCard.setFlipped(true);
        animationCard.setType(CardType.CLOVER_2);
        animationCard.setShadow(false);
        animationCard.setVisible(false);

        table.setFillParent(true);
        table.add(playerScrollPane).expandX().height(200).fill().top();
        table.row();
        table.add(pileDisplayActor).top().expandY().padBottom(32).padLeft(50).padTop(100);
        table.row();

        stage.addActor(table);
        stage.addActor(animationCard);

        GameStateManager.getInstance().registerListener(this);
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);

        GameStateManager.getInstance().update(DummyGame.gameState);
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(new Color(232 / 255f, 232 / 255f, 232 / 255f, 1));
        stage.act(delta);
        stage.draw();
    }

    @Override
    public void onUpdate(GameState newGameState) {
        if(!game.getScreen().equals(this)) return;

        playersPane.clearChildren();
        for(String playerUuid : newGameState.getPlayerList()) {
            Player player = newGameState.getPlayer(playerUuid);
            playersPane.addElement(new PlayerElement(game, player, true));
        }
    }

    public void onReceiveCard() {
        animationCard.flyIn(pileDisplayActor.getX(), pileDisplayActor.getY(), true, new Runnable() {
            @Override
            public void run() {
                pileDisplayActor.updatePileState();
                pileDisplayActor.setTopFlipped(true);
                pileDisplayActor.flipTop();
            }
        });
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
