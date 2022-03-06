package com.jaeheonshim.ersgame.server.listener;

import com.jaeheonshim.ersgame.game.model.GameState;
import com.jaeheonshim.ersgame.game.model.Player;
import com.jaeheonshim.ersgame.net.model.UIMessageType;
import com.jaeheonshim.ersgame.net.packet.*;
import com.jaeheonshim.ersgame.server.ERSServer;
import com.jaeheonshim.ersgame.server.GameManager;
import com.jaeheonshim.ersgame.server.InputValidator;
import com.jaeheonshim.ersgame.server.ServerPacketListener;
import com.jaeheonshim.ersgame.util.ERSException;
import org.java_websocket.WebSocket;

public class JoinGameListener extends ServerPacketListener {
    public JoinGameListener(ERSServer server) {
        super(server);
    }

    @Override
    public boolean receive(WebSocket socket, SocketPacket packet) {
        if(packet instanceof JoinGamePacket) {
            String uuid = socket.getAttachment();
            JoinGamePacket joinGamePacket = ((JoinGamePacket) packet);

            if(!InputValidator.validateGameCode(joinGamePacket.joinCode)) {
                throw new ERSException();
            }

            if(!InputValidator.validateUsername(joinGamePacket.username)) {
                throw new ERSException();
            }

            GameState gameState = GameManager.getInstance().getGame(joinGamePacket.joinCode);

            if(gameState == null) {
                server.send(new UIMessagePacket(UIMessageType.ERROR, "Game not found!"), uuid);
                return true;
            }

            if(gameState.hasPlayer(uuid)) {
                server.send(new UIMessagePacket(UIMessageType.ERROR, "Already in game!"), uuid);
                return true;
            }

            if(gameState.hasUsername(joinGamePacket.username)) {
                server.send(new UIMessagePacket(UIMessageType.ERROR, "Username already exists"), uuid);
                return true;
            }

            Player player = new Player();
            player.setUuid(uuid);
            player.setUsername(joinGamePacket.username);

            gameState.addPlayer(player);
            server.send(new UIMessagePacket(UIMessageType.SUCCESS, "Successfully joined game"), uuid);
            server.broadcast(new GameStatePacket(gameState), gameState);
            server.broadcast(new OverlayMessagePacket(joinGamePacket.username + " joined"), gameState);

            return true;
        }

        return false;
    }
}
