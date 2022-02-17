package com.jaeheonshim.ersgame.scene.ui;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.jaeheonshim.ersgame.ERSGame;
import com.jaeheonshim.ersgame.net.NetManager;
import com.jaeheonshim.ersgame.scene.shaded.ERSLabel;

import javax.management.MBeanRegistration;

public class ConnectionStatusLabel extends ERSLabel {
    public ConnectionStatusLabel(Skin skin, ERSGame game) {
        super("Disconnected", skin, "small", game);
        setColor(Color.BLACK);
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        switch (NetManager.getInstance().getConnectionStatus()) {
            case CONNECTED:
                setText("Connected");
                break;
            case CONNECTING:
                setText("Connecting...");
                break;
            case DISCONNECTED:
                setText("Disconnected");
                break;
            default:
                setText("Disconnected");
                break;
        }
    }
}
