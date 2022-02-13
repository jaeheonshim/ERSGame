package com.jaeheonshim.ersgame.server;

import com.jaeheonshim.ersgame.game.GameStateEventListener;
import com.jaeheonshim.ersgame.game.GameState;
import com.jaeheonshim.ersgame.game.Player;
import com.jaeheonshim.ersgame.net.packet.GameStateUpdateEvent;
import com.jaeheonshim.ersgame.net.packet.JoinGameEvent;

public class ServerGameEventListener extends GameStateEventListener {
    private ERSServer ersServer;

    public ServerGameEventListener(ERSServer server) {
        this.ersServer = server;
    }

    @Override
    public void onPlayerJoin(GameState game, Player player) {
        JoinGameEvent joinGameEvent = new JoinGameEvent(player.getUuid(), player.getName(), player.getOrdinal());
        GameStateUpdateEvent updateEvent = GamesManager.generateUpdateEvent(game);

        for(Player eachPlayer : game.getPlayers()) {
            if(eachPlayer != player) {
                ersServer.getServer().sendToTCP(eachPlayer.getConnectionId(), joinGameEvent);
            }
            ersServer.getServer().sendToTCP(eachPlayer.getConnectionId(), updateEvent);
        }
    }
}
