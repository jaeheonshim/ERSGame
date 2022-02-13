package com.jaeheonshim.ersgame.net.listener;

import com.badlogic.gdx.Gdx;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.jaeheonshim.ersgame.game.GameStateManager;
import com.jaeheonshim.ersgame.net.packet.JoinGameResponse;

public class JoinGameListener extends Listener {
    @Override
    public void received(Connection connection, Object object) {
        if(object instanceof JoinGameResponse) {
            final JoinGameResponse response = ((JoinGameResponse) object);

            if(response.status == JoinGameResponse.Status.SUCCESS) {
                Gdx.app.postRunnable(new Runnable() {
                    @Override
                    public void run() {
                        GameStateManager.getInstance().setSelfUUID(response.uuid);
                        GameStateManager.getInstance().fireUpdate();
                    }
                });
            }
        }
    }
}
