package com.jaeheonshim.ersgame.server.listener;

import com.jaeheonshim.ersgame.game.model.GameState;
import com.jaeheonshim.ersgame.game.model.Player;
import com.jaeheonshim.ersgame.game.util.GameStateUtil;
import com.jaeheonshim.ersgame.net.model.GameAction;
import com.jaeheonshim.ersgame.net.packet.GameActionPacket;
import com.jaeheonshim.ersgame.net.packet.GameStatePacket;
import com.jaeheonshim.ersgame.net.packet.OverlayMessagePacket;
import com.jaeheonshim.ersgame.net.packet.SocketPacket;
import com.jaeheonshim.ersgame.server.ERSServer;
import com.jaeheonshim.ersgame.server.GameManager;
import com.jaeheonshim.ersgame.server.ServerPacketListener;
import com.jaeheonshim.ersgame.util.ERSException;
import org.java_websocket.WebSocket;

public class LeaveGameListener extends ServerPacketListener {
    public LeaveGameListener(ERSServer server) {
        super(server);
    }

    @Override
    public boolean receive(WebSocket socket, SocketPacket packet) {
        if(packet instanceof GameActionPacket) {
            GameActionPacket gameActionPacket = ((GameActionPacket) packet);
            if(gameActionPacket.gameAction == GameAction.LEAVE_GAME) {
                String uuid = socket.getAttachment();
                GameState gameState = GameManager.getInstance().getGameOfPlayer(uuid);

                if(gameState == null) throw new ERSException("Player not in game");
                Player player = gameState.getPlayer(uuid);

                boolean isEmpty = GameStateUtil.removePlayer(gameState, uuid);

                server.send(new GameActionPacket(GameAction.LEAVE_GAME), uuid);

                if(isEmpty) {
                    GameManager.getInstance().removeGame(gameState.getGameCode());
                    return true;
                }

                OverlayMessagePacket messagePacket = new OverlayMessagePacket(player.getUsername() + " left");
                GameStatePacket gameStatePacket = new GameStatePacket(gameState);

                server.broadcast(messagePacket, gameState);
                server.broadcast(gameStatePacket, gameState);

                return true;
            }
        }

        return false;
    }
}
