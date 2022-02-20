package com.jaeheonshim.ersgame.server.listener;

import com.jaeheonshim.ersgame.game.model.GameState;
import com.jaeheonshim.ersgame.game.util.GameStateUtil;
import com.jaeheonshim.ersgame.game.model.Player;
import com.jaeheonshim.ersgame.net.model.GameAction;
import com.jaeheonshim.ersgame.net.packet.GameActionPacket;
import com.jaeheonshim.ersgame.net.packet.GameStatePacket;
import com.jaeheonshim.ersgame.net.packet.SocketPacket;
import com.jaeheonshim.ersgame.util.ERSException;
import com.jaeheonshim.ersgame.server.ERSServer;
import com.jaeheonshim.ersgame.server.GameManager;
import com.jaeheonshim.ersgame.server.ServerPacketListener;
import org.java_websocket.WebSocket;

public class StartGameListener extends ServerPacketListener {
    public StartGameListener(ERSServer server) {
        super(server);
    }

    @Override
    public boolean receive(WebSocket socket, SocketPacket packet) {
        if(packet instanceof GameActionPacket) {
            GameActionPacket gameActionPacket = ((GameActionPacket) packet);
            if(gameActionPacket.gameAction == GameAction.START) {
                String uuid = socket.getAttachment();
                GameState gameState = GameManager.getInstance().getGameOfPlayer(uuid);

                if(gameState == null) throw new ERSException("Not in game");
                Player player = gameState.getPlayer(uuid);
                if(!gameState.getAdminPlayer().equals(player.getUuid())) throw new ERSException("Player not admin");

                GameStateUtil.startGame(gameState);

                GameActionPacket startGamePacket = new GameActionPacket(GameAction.START);
                server.broadcast(startGamePacket, gameState);

                GameStatePacket gameStatePacket = new GameStatePacket(gameState);
                server.broadcast(gameStatePacket, gameState);

                server.broadcast(new GameActionPacket(GameAction.TURN_UPDATE), gameState);
                return true;
            }
        }

        return false;
    }
}
