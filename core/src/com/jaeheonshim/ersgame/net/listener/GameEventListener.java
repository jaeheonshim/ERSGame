package com.jaeheonshim.ersgame.net.listener;

import com.badlogic.gdx.Gdx;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.jaeheonshim.ersgame.game.GameStateManager;
import com.jaeheonshim.ersgame.game.Player;
import com.jaeheonshim.ersgame.net.packet.GameStateUpdateEvent;
import com.jaeheonshim.ersgame.net.packet.JoinGameEvent;
import com.jaeheonshim.ersgame.net.packet.JoinGameResponse;

public class GameEventListener extends Listener {
    @Override
    public void received(Connection connection, Object object) {
        if(object instanceof JoinGameEvent) {
            JoinGameEvent event = ((JoinGameEvent) object);
            Player player = new Player(event.uuid, event.name, event.ordinal);

            GameStateManager.getInstance().getGameState().getGameEventListener().onPlayerJoin(GameStateManager.getInstance().getGameState(), player);
        } else if(object instanceof GameStateUpdateEvent) {
            System.out.println("Update event received");
            final GameStateUpdateEvent updateEvent = ((GameStateUpdateEvent) object);
            Gdx.app.postRunnable(new Runnable() {
                @Override
                public void run() {
                    GameStateManager.getInstance().updateState(updateEvent);
                }
            });
        }
    }
}
