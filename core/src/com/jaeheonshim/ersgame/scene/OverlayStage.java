package com.jaeheonshim.ersgame.scene;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Cell;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.jaeheonshim.ersgame.ERSGame;
import com.jaeheonshim.ersgame.net.UIMessageType;
import com.jaeheonshim.ersgame.scene.action.DisappearAction;
import com.jaeheonshim.ersgame.scene.ui.UIMessageLabel;

public class OverlayStage extends Stage {
    private final Cell<UIMessageLabel> uiMessageLabelCell;
    private Skin skin;
    private Table table;

    private UIMessageLabel uiMessageLabel;

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

        uiMessageLabel = new UIMessageLabel(skin, game);
        uiMessageLabelCell = table.add(uiMessageLabel).expand().fillX().bottom().height(60);

        table.setFillParent(true);
        addActor(table);
    }

    public void onMessageUpdate(UIMessageType type, String message) {
        uiMessageLabel.onMessageUpdate(type, message);
        uiMessageLabel.addAction(new DisappearAction(3));
    }
}
