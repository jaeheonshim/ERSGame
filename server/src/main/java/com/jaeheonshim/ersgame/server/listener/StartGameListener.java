package com.jaeheonshim.ersgame.server.listener;

import com.jaeheonshim.ersgame.game.GameState;
import com.jaeheonshim.ersgame.game.Player;
import com.jaeheonshim.ersgame.net.GameAction;
import com.jaeheonshim.ersgame.net.packet.GameActionPacket;
import com.jaeheonshim.ersgame.net.packet.SocketPacket;
import com.jaeheonshim.ersgame.server.ERSException;
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

                GameActionPacket startGamePacket = new GameActionPacket(GameAction.START);
                server.broadcast(startGamePacket, gameState);
                return true;
            }
        }

        return false;
    }
}
