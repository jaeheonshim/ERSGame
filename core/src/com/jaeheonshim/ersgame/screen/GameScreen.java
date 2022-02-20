package com.jaeheonshim.ersgame.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.AlphaAction;
import com.badlogic.gdx.scenes.scene2d.actions.RunnableAction;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Stack;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
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
    private Stack stack;

    private PileDisplayActor pileDisplayActor;
    private PlayersPane playersPane;
    private CardActor animationCard;
    private ScrollPane playerScrollPane;

    private ERSLabel pileCount;
    private ERSLabel selfCount;
    private ERSLabel timeoutLabel;
    private ERSTextButton playButton;

    private boolean awaitGameUpdatePile;
    private boolean pendingPileUpdate;

    private float timeoutTimer = 0;

    public GameScreen(ERSGame game) {
        this.game = game;
        Skin skin = game.assets.get(game.uiSkin);

        stage = new Stage(new ExtendViewport(600, 900));
        table = new Table();

        pileDisplayActor = new PileDisplayActor(game, () -> {
            if (GameStateManager.getInstance().getGameState() == null) return null;
            return GameStateManager.getInstance().getGameState().getTopNCards(PileDisplayActor.DISPLAY_COUNT);
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

        timeoutLabel = new ERSLabel("0:00", skin, "timeout", game);

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

        overlayTable = new Table();
        table.setFillParent(true);
        overlayTable.add(timeoutLabel).center().padLeft(50);
        timeoutLabel.setVisible(false);

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

        GameStateManager.getInstance().registerListener(this);
        GameStateManager.getInstance().registerActionListener(this);

        pileDisplayActor.setZIndex(3);
    }

    @Override
    public void onUpdate(GameState newGameState, GameState oldGameState) {
        if (!game.getScreen().equals(this)) return;
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
            awaitGameUpdatePile = false;
            pendingPileUpdate = true;
        } else {
            pileDisplayActor.updatePileState();
        }

        updatePlayButtonDisableState();
    }

    @Override
    public void onTurnUpdate() {
        GameState gameState = GameStateManager.getInstance().getGameState();
        String currentTurnUUID = gameState.getPlayerList().get(gameState.getCurrentTurnIndex());
        Player currentTurn = gameState.getPlayer(currentTurnUUID);

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

    public void playCard() {
        animationCard.setZIndex(pileDisplayActor.getZIndex() + 1);
        animationCard.flyIn(pileDisplayActor.getX(), pileDisplayActor.getY(), false, () -> {
        });
    }

    public void fadeAwayPile() {
        AlphaAction action = new AlphaAction();
        action.setAlpha(0);
        action.setDuration(1.5f);

        RunnableAction updatePileAction = new RunnableAction();
        updatePileAction.setRunnable(() -> {
            pileDisplayActor.updatePileState();
        });

        AlphaAction reshow = new AlphaAction();
        reshow.setAlpha(1);

        pileDisplayActor.addAction(new SequenceAction(action, updatePileAction, reshow));
    }

    @Override
    public void show() {
        resize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        Gdx.input.setInputProcessor(stage);
        onUpdate(GameStateManager.getInstance().getGameState());

        stage.addListener(new InputListener() {
            @Override
            public boolean keyDown(InputEvent event, int keycode) {
                timeoutTimer = 30;
                return true;
            }
        });
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
