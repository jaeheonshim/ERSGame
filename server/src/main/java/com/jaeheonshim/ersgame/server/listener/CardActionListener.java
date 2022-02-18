package com.jaeheonshim.ersgame.server.listener;

import com.jaeheonshim.ersgame.ERSException;
import com.jaeheonshim.ersgame.game.GameState;
import com.jaeheonshim.ersgame.game.GameStateUtil;
import com.jaeheonshim.ersgame.net.GameAction;
import com.jaeheonshim.ersgame.net.listener.SocketPacketListener;
import com.jaeheonshim.ersgame.net.packet.GameActionPacket;
import com.jaeheonshim.ersgame.net.packet.GameStatePacket;
import com.jaeheonshim.ersgame.net.packet.SocketPacket;
import com.jaeheonshim.ersgame.server.ERSServer;
import com.jaeheonshim.ersgame.server.GameManager;
import com.jaeheonshim.ersgame.server.ServerPacketListener;
import org.java_websocket.WebSocket;

public class CardActionListener extends ServerPacketListener {
    public CardActionListener(ERSServer server) {
        super(server);
    }

    @Override
    public boolean receive(WebSocket socket, SocketPacket packet) {
        if(packet instanceof GameActionPacket) {
            String uuid = socket.getAttachment();
            GameActionPacket gameActionPacket = ((GameActionPacket) packet);

            if(gameActionPacket.gameAction == GameAction.PLAY_CARD) {
                GameState gameState = GameManager.getInstance().getGameOfPlayer(uuid);

                if(gameState == null) throw new ERSException("Player not in game");

                GameStateUtil.playCard(gameState, uuid);

                server.broadcast(new GameStatePacket(gameState), gameState);
                server.broadcastExcept(new GameActionPacket(GameAction.RECEIVE_CARD), gameState, uuid);

                return true;
            }
        }

        return false;
    }
}
