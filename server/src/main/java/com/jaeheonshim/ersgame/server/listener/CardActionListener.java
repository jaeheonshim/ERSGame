package com.jaeheonshim.ersgame.server.listener;

import com.jaeheonshim.ersgame.ERSException;
import com.jaeheonshim.ersgame.game.CardType;
import com.jaeheonshim.ersgame.game.GameState;
import com.jaeheonshim.ersgame.game.GameStateUtil;
import com.jaeheonshim.ersgame.game.Player;
import com.jaeheonshim.ersgame.net.GameAction;
import com.jaeheonshim.ersgame.net.listener.SocketPacketListener;
import com.jaeheonshim.ersgame.net.packet.GameActionPacket;
import com.jaeheonshim.ersgame.net.packet.GameStatePacket;
import com.jaeheonshim.ersgame.net.packet.OverlayMessagePacket;
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
    public synchronized boolean receive(WebSocket socket, SocketPacket packet) {
        if(packet instanceof GameActionPacket) {
            String uuid = socket.getAttachment();
            GameActionPacket gameActionPacket = ((GameActionPacket) packet);
            GameState gameState = GameManager.getInstance().getGameOfPlayer(uuid);

            if(gameState == null) throw new ERSException("Player not in game");

            if(gameActionPacket.gameAction == GameAction.PLAY_CARD) {
                int oldTurn = gameState.getCurrentTurnIndex();
                GameStateUtil.playCard(gameState, uuid);
                GameStateUtil.nextTurn(gameState);

                server.broadcastExcept(new GameActionPacket(GameAction.RECEIVE_CARD), gameState, uuid);
                server.broadcast(new GameStatePacket(gameState), gameState);

                if(gameState.getCurrentTurnIndex() != oldTurn) {
                    server.broadcast(new GameActionPacket(GameAction.TURN_UPDATE), gameState);
                }

                return true;
            } else if(gameActionPacket.gameAction == GameAction.SLAP) {
                boolean isValid = GameStateUtil.isValidSlap(gameState);
                Player player = gameState.getPlayer(uuid);

                if(isValid) {
                    server.broadcast(new OverlayMessagePacket(player.getUsername() + " slapped: +" + gameState.getPileCount() + " cards"), gameState);

                    CardType c;
                    while((c = gameState.removeCardFromTop()) != null) {
                        player.addCardToBottom(c);
                    }
                } else {
                    server.broadcast(new OverlayMessagePacket(player.getUsername() + " slapped: -1 cards"), gameState);
                    server.broadcastExcept(new GameActionPacket(GameAction.OTHERS_DISCARD), gameState, uuid);
                    server.send(new GameActionPacket(GameAction.YOU_DISCARD), uuid);
                    if(player.getCardCount() > 0) {
                        gameState.addCardToBottom(player.removeTopCard());
                    }
                }

                server.broadcast(new GameStatePacket(gameState), gameState);
            }
        }

        return false;
    }
}
