package com.jaeheonshim.ersgame.scene.ui;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.Align;
import com.jaeheonshim.ersgame.ERSGame;
import com.jaeheonshim.ersgame.net.NetManager;
import com.jaeheonshim.ersgame.net.listener.UIMessagePacketListener;
import com.jaeheonshim.ersgame.scene.shaded.ERSLabel;

public class UIMessageLabel extends ERSLabel {
    public UIMessageLabel(Skin skin, ERSGame game) {
        super("asdf", skin, "success", game);
        setVisible(false);
        setAlignment(Align.center);
    }

    public void onMessageUpdate(String message) {
        setVisible(true);
        setText(message);
    }
}
