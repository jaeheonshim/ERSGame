package com.jaeheonshim.ersgame.scene.ui;

import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.AlphaAction;
import com.badlogic.gdx.scenes.scene2d.actions.RunnableAction;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.badlogic.gdx.scenes.scene2d.ui.Cell;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.VerticalGroup;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Queue;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.jaeheonshim.ersgame.ERSGame;
import com.jaeheonshim.ersgame.net.model.UIMessageType;
import com.jaeheonshim.ersgame.scene.action.DisappearAction;
import com.jaeheonshim.ersgame.scene.shaded.ERSLabel;

import java.util.StringJoiner;

public class OverlayStage extends Stage {
    private ERSGame game;

    private final Cell<UIMessageLabel> uiMessageLabelCell;
    private Skin skin;
    private Table table;
    private VerticalGroup overlayMessageGroup;

    private UIMessageLabel uiMessageLabel;

    private final float OVERLAY_TIME = 3;

    private Queue<String> overlayMessageQueue = new Queue<>();

    private static OverlayStage instance;

    public static void initialize(ERSGame game) {
        instance = new OverlayStage(game);
    }

    public static OverlayStage getInstance() {
        return instance;
    }

    private OverlayStage(ERSGame game) {
        super(new ExtendViewport(600, 900));
        this.game = game;
        table = new Table();
        overlayMessageGroup = new VerticalGroup();
        overlayMessageGroup.grow();

        skin = game.assets.get(game.uiSkin);

        table.add(overlayMessageGroup).growX().top().padTop(150);
        table.row();

        uiMessageLabel = new UIMessageLabel(skin, game);
        uiMessageLabelCell = table.add(uiMessageLabel).expand().fillX().bottom().height(60);

        table.setFillParent(true);
        addActor(table);
    }

    public void onMessageUpdate(UIMessageType type, String message) {
        uiMessageLabel.onMessageUpdate(type, message);
        uiMessageLabel.clearActions();
        uiMessageLabel.addAction(new DisappearAction(2.5f));
    }

    public void postOverlayMessage(String message) {
        ERSLabel newLabel = new ERSLabel(message, skin, "overlay", game);
        newLabel.setAlignment(Align.center);
        newLabel.addAction(overlayMessageAction(newLabel));

        overlayMessageGroup.addActorAt(0, newLabel);
        overlayMessageGroup.pack();
    }

    private Action overlayMessageAction(Actor actor) {
        SequenceAction action = new SequenceAction();
        AlphaAction alphaAction = new AlphaAction();
        alphaAction.setAlpha(0.5f);
        alphaAction.setDuration(OVERLAY_TIME);
        alphaAction.setInterpolation(Interpolation.fastSlow);
        RunnableAction removeAction = new RunnableAction();
        removeAction.setRunnable(() -> {
            overlayMessageGroup.removeActor(actor);
        });

        action.addAction(alphaAction);
        action.addAction(removeAction);

        return action;
    }

    public String getOverlayMessage() {
        StringJoiner stringBuilder = new StringJoiner("\n");
        for(String s : overlayMessageQueue) {
            stringBuilder.add(s);
        }

        return stringBuilder.toString();
    }

    @Override
    public void act(float delta) {
        super.act(delta);
    }
}
