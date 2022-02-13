package com.jaeheonshim.ersgame.server;

import com.badlogic.gdx.Game;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.jaeheonshim.ersgame.game.GameState;
import com.jaeheonshim.ersgame.game.Player;
import com.jaeheonshim.ersgame.net.packet.JoinGameEvent;
import com.jaeheonshim.ersgame.net.packet.JoinGameRequest;
import com.jaeheonshim.ersgame.net.packet.JoinGameResponse;

import java.util.Locale;
import java.util.logging.Logger;

public class JoinGameListener extends Listener {
    private Logger logger = Logger.getLogger(JoinGameListener.class.getName());

    @Override
    public void received(Connection connection, Object object) {
        if(object instanceof JoinGameRequest) {
            GamesManager manager = GamesManager.getInstance();
            JoinGameRequest request = ((JoinGameRequest) object);
            JoinGameResponse response = new JoinGameResponse();
            logger.info(String.format(Locale.ENGLISH, "Connection %d is attempting to join game (joinCode: %s) with username %s%n", connection.getID(), request.gameCode, request.username));

            if(!manager.gameExists(request.gameCode.trim())) {
                logger.info(String.format(Locale.ENGLISH, "Attempt from connection %d denied: Invalid game code", connection.getID()));
                response.status = JoinGameResponse.Status.INVALID_CODE;
                connection.sendTCP(response);
                return;
            }

            GameState joiningGame = manager.getGame(request.gameCode);

            if(joiningGame.usernameExists(request.username)) {
                logger.info(String.format(Locale.ENGLISH, "Attempt from connection %d denied: Username is taken", connection.getID()));
                response.status = JoinGameResponse.Status.USERNAME_TAKEN;
                connection.sendTCP(response);
                return;
            }

            Player player = Player.createNew(request.username);
            player.setConnectionId(connection.getID());
            joiningGame.addNewPlayer(player);

            logger.info(String.format(Locale.ENGLISH, "Attempt from connection %d successful: joins game (joinCode: %s) with username %s", connection.getID(), joiningGame.getJoinCode(), player.getName()));

            response.status = JoinGameResponse.Status.SUCCESS;
            response.uuid = player.getUuid();
            connection.sendTCP(response);
        }
    }
}
