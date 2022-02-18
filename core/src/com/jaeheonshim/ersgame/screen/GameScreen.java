package com.jaeheonshim.ersgame.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.jaeheonshim.ersgame.game.*;
import com.jaeheonshim.ersgame.ERSGame;
import com.jaeheonshim.ersgame.scene.game.CardActor;
import com.jaeheonshim.ersgame.scene.game.PileDisplayActor;
import com.jaeheonshim.ersgame.scene.shaded.ERSLabel;
import com.jaeheonshim.ersgame.scene.shaded.ERSTextButton;
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

    private ERSLabel pileCount;
    private ERSLabel selfCount;
    private ERSTextButton playButton;

    public GameScreen(ERSGame game) {
        this.game = game;
        Skin skin = game.assets.get(game.uiSkin);

        stage = new Stage(new ExtendViewport(600, 900));
        table = new Table();

        pileDisplayActor = new PileDisplayActor(game, () -> {
            if(GameStateManager.getInstance().getGameState() == null) return null;
            return GameStateManager.getInstance().getGameState().getTopNCards(3);
        });
        playersPane = new PlayersPane(game);
        playerScrollPane = new ScrollPane(playersPane);

        animationCard = new CardActor(game);
        animationCard.setFlipped(true);
        animationCard.setType(CardType.CLOVER_2);
        animationCard.setShadow(false);
        animationCard.setVisible(false);

        pileCount = new ERSLabel("0", skin, game);
        pileCount.setColor(Color.BLACK);

        selfCount = new ERSLabel("You have 0 cards", skin, game);
        selfCount.setColor(Color.BLACK);

        playButton = new ERSTextButton("Play", skin, game);

        table.setFillParent(true);
        table.add(playerScrollPane).expandX().height(200).fill().top();
        table.row();
        table.add(pileDisplayActor).top().padBottom(8).padLeft(50).padTop(70);
        table.row();
        table.add(pileCount).top();
        table.row();
        table.add(selfCount).bottom().padTop(20);
        table.row();
        table.add(playButton).expandY().top().padTop(2);

        stage.addActor(table);
        stage.addActor(animationCard);

        GameStateManager.getInstance().registerListener(this);
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
        onUpdate(GameStateManager.getInstance().getGameState());
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

        pileCount.setText(Integer.toString(newGameState.getPileCount()));

        Player selfPlayer = GameStateManager.getInstance().getSelfPlayer();
        selfCount.setText("You have " + selfPlayer.getCardCount() + " cards");
        pileDisplayActor.updatePileState();
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
