package com.jaeheonshim.ersgame.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.*;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Stack;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Queue;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.jaeheonshim.ersgame.game.*;
import com.jaeheonshim.ersgame.ERSGame;
import com.jaeheonshim.ersgame.game.model.CardType;
import com.jaeheonshim.ersgame.game.model.GameState;
import com.jaeheonshim.ersgame.game.model.Player;
import com.jaeheonshim.ersgame.net.model.GameAction;
import com.jaeheonshim.ersgame.net.NetManager;
import com.jaeheonshim.ersgame.net.packet.GameActionPacket;
import com.jaeheonshim.ersgame.scene.ui.OverlayStage;
import com.jaeheonshim.ersgame.scene.game.CardActor;
import com.jaeheonshim.ersgame.scene.game.PileDisplayActor;
import com.jaeheonshim.ersgame.scene.shaded.ERSLabel;
import com.jaeheonshim.ersgame.scene.shaded.ERSTextButton;
import com.jaeheonshim.ersgame.scene.ui.PlayerElement;
import com.jaeheonshim.ersgame.scene.ui.PlayersPane;

import java.util.Map;

public class GameScreen implements Screen, GameStateUpdateListener, GameActionListener {
    private ERSGame game;
    private Stage stage;

    private Table table;
    private Table overlayTable;
    private Table debugTable;
    private Stack stack;

    private PileDisplayActor pileDisplayActor;
    private PlayersPane playersPane;
    private CardActor animationCard;
    private ScrollPane playerScrollPane;

    private ERSLabel pileCount;
    private ERSLabel selfCount;
    private ERSLabel remainingCount;
    private ERSLabel timeoutLabel;
    private ERSTextButton playButton;

    private ERSLabel debugCanPlay;
    private ERSLabel debugIgnoreSlap;

    private Array<CardType> updatePile;
    private boolean awaitGameUpdatePile;
    private boolean pendingPileUpdate;

    private float timeoutTimer = 0;

    private boolean debug = false;

    public GameScreen(ERSGame game) {
        this.game = game;
        Skin skin = game.assets.get(game.uiSkin);

        stage = new Stage(new ExtendViewport(600, 900));
        table = new Table();

        pileDisplayActor = new PileDisplayActor(game, () -> {
            if (GameStateManager.getInstance().getGameState() == null) return null;
            return updatePile;
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

        remainingCount = new ERSLabel("Play 0 cards", skin, game);
        remainingCount.setColor(Color.BLACK);
        remainingCount.setVisible(false);

        playButton = new ERSTextButton("Play", skin, game);

        timeoutLabel = new ERSLabel("0:00", skin, "timeout", game);

        table.setFillParent(true);
        table.add(playerScrollPane).expandX().height(200).fill().top();
        table.row();
        table.add(remainingCount).expandX();
        table.row();
        table.add(pileDisplayActor).top().padBottom(8).padLeft(50).padTop(70);
        table.row();
        table.add(pileCount).top();
        table.row();
        table.add(selfCount).bottom().padTop(20);
        table.row();
        table.add(playButton).expandY().top().pad(8).growX().height(100).bottom();

        overlayTable = new Table();
        table.setFillParent(true);
        overlayTable.add(timeoutLabel).center().padLeft(50).row();
        timeoutLabel.setVisible(false);

        debugCanPlay = new ERSLabel("canPlay: ", skin, "small", game);
        debugCanPlay.setColor(Color.RED);
        debugIgnoreSlap = new ERSLabel("ignoreSlap: ", skin, "small", game);
        debugIgnoreSlap.setColor(Color.RED);

        debugTable = new Table();
        debugTable.setVisible(debug);
        debugTable.left().padLeft(4);
        debugTable.add(debugCanPlay).left().row();
        debugTable.add(debugIgnoreSlap).left().row();

        overlayTable.add(debugTable).growX().left();

        stack = new Stack();
        stack.setFillParent(true);
        stack.add(table);
        stack.add(overlayTable);

        stage.addActor(stack);
        stage.addActor(animationCard);

        playButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (animationCard.hasActions()) return;
                if (playButton.isDisabled()) {
                    if (!GameStateManager.getInstance().isTurn()) {
                        OverlayStage.getInstance().postOverlayMessage("It's not your turn yet!");
                    } else {
                        OverlayStage.getInstance().postOverlayMessage("You don't have any cards!");
                    }
                    return;
                }

                NetManager.getInstance().send(new GameActionPacket(GameAction.PLAY_CARD));
                awaitGameUpdatePile = true;
                playCard();
            }
        });

        pileDisplayActor.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if(timeoutTimer > 0) return;

                if(GameStateManager.getInstance().getGameState().getPileCount() <= 0) {
                    OverlayStage.getInstance().postOverlayMessage("There aren't any cards to slap!");
                    return;
                }

                NetManager.getInstance().send(new GameActionPacket(GameAction.SLAP));
            }
        });

        stage.addListener(new InputListener() {
            @Override
            public boolean keyDown(InputEvent event, int keycode) {
                if(keycode == Input.Keys.Z) {
                    debug = !debug;
                    debugTable.setVisible(debug);
                    return true;
                }

                return false;
            }
        });

        GameStateManager.getInstance().registerListener(this);
        GameStateManager.getInstance().registerActionListener(this);
    }

    @Override
    public void onUpdate(GameState newGameState, GameState oldGameState) {
        if (!game.getScreen().equals(this)) return;

        if(debug) {
            debugIgnoreSlap.setText("ignoreSlap: " + newGameState.isIgnoreSlap());
            debugCanPlay.setText("canPlay: " + newGameState.isCanPlay());
        }

        if(newGameState.isGameOver()) {
            game.setScreen(game.gameOverScreen);
        }

        Map<String, PlayerElement> elementMap = playersPane.getPlayerElementMap();

        if(newGameState.getPlayerList().size != elementMap.size()) {
            playersPane.clearChildren();
            for(int i = 0; i < newGameState.getPlayerList().size; i++) {
                String playerUuid = newGameState.getPlayerList().get(i);
                Player player = newGameState.getPlayer(playerUuid);
                PlayerElement element = new PlayerElement(game, player, true);
                playersPane.addElement(element);
            }
        } else {
            Player currentTurn = newGameState.getPlayer(newGameState.getPlayerList().get(newGameState.getCurrentTurnIndex()));
            for(PlayerElement element : elementMap.values()) {
                Player player = newGameState.getPlayer(element.getPlayer().getUuid());
                element.setCurrentTurn(element.getPlayer().equals(currentTurn));
                element.setCardCount(player.getCardCount());
            }
        }

        pileCount.setText(Integer.toString(newGameState.getPileCount()));

        Player selfPlayer = GameStateManager.getInstance().getSelfPlayer();
        selfCount.setText("You have " + selfPlayer.getCardCount() + " cards");

        int oldPileCount = oldGameState.getPileCount();
        if(oldPileCount > 0 && newGameState.getPileCount() == 0) {
            // if someone took all the cards, slowly fade away the pile
            fadeAwayPile();
        } else if (awaitGameUpdatePile) {
            pileDisplayActor.pack();
            awaitGameUpdatePile = false;
            pendingPileUpdate = true;
            updatePile = newGameState.getTopNCards(4);
        } else {
            pileDisplayActor.pack();
            updatePile = newGameState.getTopNCards(4);

            if(animationCard.hasActions()) {
                pendingPileUpdate = true;
            } else {
                pileDisplayActor.updatePileState();
            }
        }

        if(newGameState.getPendingCardCount() > 0 && GameStateManager.getInstance().isTurn() && !GameStateManager.getInstance().getGameState().getLastFacePlayer().equals(GameStateManager.getInstance().getSelfPlayer().getUuid())) {
            remainingCount.setText("Play " + newGameState.getPendingCardCount() + " cards");
            remainingCount.setVisible(true);
        } else {
            remainingCount.setVisible(false);
        }

        updatePlayButtonDisableState();
    }

    @Override
    public void onTurnUpdate() {
        GameState gameState = GameStateManager.getInstance().getGameState();
        String currentTurnUUID = gameState.getPlayerList().get(gameState.getCurrentTurnIndex());
        Player currentTurn = gameState.getPlayer(currentTurnUUID);

        PlayerElement element = playersPane.getElement(currentTurnUUID);
        float x = element.getX();
        float y = element.getY();
        float width = element.getWidth();
        float height = element.getHeight();

        playerScrollPane.scrollTo(x, y, width, height, false, true);

        if (currentTurn.equals(GameStateManager.getInstance().getSelfPlayer())) {
            OverlayStage.getInstance().postOverlayMessage("It's your turn!");
        } else {
            OverlayStage.getInstance().postOverlayMessage(currentTurn.getUsername() + "'s turn");
        }

        updatePlayButtonDisableState();
    }

    @Override
    public void onReceiveCard() {
        awaitGameUpdatePile = true;
        animationCard.setZIndex(pileDisplayActor.getZIndex() + 1);
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
    public void onPointUpdate(String uuid, int amount) {
        PlayerElement element = playersPane.getElement(uuid);
        element.setPointChange(amount);

        if(uuid.equals(GameStateManager.getInstance().getSelfPlayer().getUuid()) && amount > 0) {
            onWinCards();
        }
    }

    @Override
    public void onDiscard(boolean you) {
        animationCard.setZIndex(0);
        awaitGameUpdatePile = true;
        animationCard.flyIn(pileDisplayActor.getX(), pileDisplayActor.getY(), !you, () -> {});
    }

    @Override
    public void onGameTimeout(float time) {
        this.timeoutTimer = time;
    }

    public void updatePlayButtonDisableState() {
        Player selfPlayer = GameStateManager.getInstance().getSelfPlayer();

        playButton.setDisabled(selfPlayer.getCardCount() <= 0 || !GameStateManager.getInstance().isTurn() || !GameStateManager.getInstance().getGameState().isCanPlay() || GameStateManager.getInstance().getGameState().isIgnoreSlap());
    }

    private void onWinCards() {
        MoveToAction moveToAction = new MoveToAction();
        moveToAction.setPosition(pileDisplayActor.getX(), -500);
        moveToAction.setDuration(1.5f);
        moveToAction.setInterpolation(Interpolation.slowFast);

        pileDisplayActor.addAction(moveToAction);
    }

    public void playCard() {
        animationCard.setZIndex(pileDisplayActor.getZIndex() + 1);
        animationCard.flyIn(pileDisplayActor.getX(), pileDisplayActor.getY(), false, () -> {
        });
    }

    public void fadeAwayPile() {
        DelayAction delayAction = new DelayAction(0.5f);

        AlphaAction action = new AlphaAction();
        action.setAlpha(0);
        action.setDuration(1.5f);

        RunnableAction updatePileAction = new RunnableAction();
        updatePileAction.setRunnable(() -> {
            updatePile = new Array<>();
            pileDisplayActor.updatePileState();
        });

        AlphaAction reshow = new AlphaAction();
        reshow.setAlpha(1);

        pileDisplayActor.addAction(new SequenceAction(delayAction, action, updatePileAction, reshow));
    }

    @Override
    public void show() {
        resize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        Gdx.input.setInputProcessor(stage);

        awaitGameUpdatePile = false;
        pendingPileUpdate = false;
        animationCard.setVisible(false);
        playersPane.clearChildren();

        onUpdate(GameStateManager.getInstance().getGameState());
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(new Color(232 / 255f, 232 / 255f, 232 / 255f, 1));
        stage.act(delta);
        stage.draw();

        if (pendingPileUpdate && !animationCard.hasActions()) {
            pendingPileUpdate = false;

            CardType oldTop = pileDisplayActor.getTopCard();
            pileDisplayActor.updatePileState();

            GameState gameState = GameStateManager.getInstance().getGameState();
            if(pileDisplayActor.getTopCard() != null && gameState.getTopCard() != null && oldTop != gameState.getTopCard()) {
                pileDisplayActor.setTopFlipped(true);
                pileDisplayActor.flipTop();
            }
        }

        if(timeoutTimer > 0) {
            timeoutTimer -= delta;
            timeoutLabel.setVisible(true);
            timeoutLabel.setText(getTimeoutTimerString(timeoutTimer));
        } else {
            timeoutTimer = 0;
            timeoutLabel.setVisible(false);
        }
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

    public static String getTimeoutTimerString(float timeoutTimer) {
        int mins = (int) (timeoutTimer / 60);
        int secs = (int) (timeoutTimer % 60);

        String secsString = secs < 10 ? "0" + secs : Integer.toString(secs);

        return mins + ":" + secsString;
    }
}
