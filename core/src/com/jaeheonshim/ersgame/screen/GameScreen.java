package com.jaeheonshim.ersgame.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.jaeheonshim.ersgame.game.CardType;
import com.jaeheonshim.ersgame.ERSGame;
import com.jaeheonshim.ersgame.scene.game.CardActor;
import com.jaeheonshim.ersgame.scene.game.PileDisplayActor;

public class GameScreen implements Screen {
    private ERSGame game;
    private Stage stage;
    private Table table;

    private PileDisplayActor pileDisplayActor;
//    private PlayersList playersList;
    private CardActor animationCard;

    public GameScreen(ERSGame game) {
        this.game = game;

        stage = new Stage(new ExtendViewport(1200, 1700));
        table = new Table();

        pileDisplayActor = new PileDisplayActor(game, null);
//        playersList = new PlayersList(game);
        animationCard = new CardActor(game);
        animationCard.setFlipped(true);
        animationCard.setType(CardType.CLOVER_2);
        animationCard.setShadow(false);
        animationCard.setVisible(false);
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);

        table.setFillParent(true);
//        table.add(playersList).expandX().fillX().maxHeight(400).top();
        table.row();
        table.add(pileDisplayActor).padLeft(200).expandY().top().padTop(200);
        table.row();

        stage.addActor(table);
        stage.addActor(animationCard);
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(new Color(232 / 255f, 232 / 255f, 232 / 255f, 1));
        stage.act(delta);
        stage.draw();
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
