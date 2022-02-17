package com.jaeheonshim.ersgame.scene.ui;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.Align;
import com.jaeheonshim.ersgame.ERSGame;
import com.jaeheonshim.ersgame.net.NetManager;
import com.jaeheonshim.ersgame.net.UIMessageType;
import com.jaeheonshim.ersgame.net.listener.UIMessagePacketListener;
import com.jaeheonshim.ersgame.scene.shaded.ERSLabel;

public class UIMessageLabel extends ERSLabel {
    private Skin skin;

    public UIMessageLabel(Skin skin, ERSGame game) {
        super("asdf", skin, "success", game);

        this.skin = skin;
        setVisible(false);
        setAlignment(Align.center);
    }

    public void onMessageUpdate(UIMessageType type, String message) {
        setStyle(skin.get(type.styleName, LabelStyle.class));

        Color color = getColor();
        setColor(color.r, color.g, color.b, 1);
        setVisible(true);
        setText(message);
    }
}
