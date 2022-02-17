package com.jaeheonshim.ersgame.scene;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Cell;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Queue;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.jaeheonshim.ersgame.ERSGame;
import com.jaeheonshim.ersgame.net.UIMessageType;
import com.jaeheonshim.ersgame.scene.action.DisappearAction;
import com.jaeheonshim.ersgame.scene.shaded.ERSLabel;
import com.jaeheonshim.ersgame.scene.ui.UIMessageLabel;

import java.util.StringJoiner;

public class OverlayStage extends Stage {
    private final Cell<UIMessageLabel> uiMessageLabelCell;
    private Skin skin;
    private Table table;

    private UIMessageLabel uiMessageLabel;

    private ERSLabel overlayLabel;
    private float overlayTimer = 0;
    private final float OVERLAY_TIME = 2;

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
        table = new Table();

        skin = game.assets.get(game.uiSkin);

        overlayLabel = new ERSLabel(null, skin, "overlay", game);
        overlayLabel.setAlignment(Align.center);
        overlayLabel.setVisible(false);

        table.add(overlayLabel).expandX().fillX().top().padTop(150);
        table.row();

        uiMessageLabel = new UIMessageLabel(skin, game);
        uiMessageLabelCell = table.add(uiMessageLabel).expand().fillX().bottom().height(60);

        table.setFillParent(true);
        addActor(table);
    }

    public void onMessageUpdate(UIMessageType type, String message) {
        uiMessageLabel.onMessageUpdate(type, message);
        uiMessageLabel.clearActions();
        uiMessageLabel.addAction(new DisappearAction(3));
    }

    public void postOverlayMessage(String message) {
        overlayTimer = 0;
        overlayMessageQueue.addFirst(message);
        overlayLabel.setText(getOverlayMessage());

        if(getOverlayMessage().isEmpty()) {
            overlayLabel.setVisible(false);
        } else {
            overlayLabel.setVisible(true);
        }
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

        overlayTimer += delta;
        if(overlayTimer >= OVERLAY_TIME) {
            overlayTimer = 0;

            if(overlayMessageQueue.isEmpty()) return;

            overlayMessageQueue.removeLast();
            overlayLabel.setText(getOverlayMessage());

            overlayLabel.setVisible(!getOverlayMessage().isEmpty());
        }
    }
}
