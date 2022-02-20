package com.jaeheonshim.ersgame.server.listener;

import com.jaeheonshim.ersgame.server.action.ReenablePlayAction;
import com.jaeheonshim.ersgame.server.action.ReleaseTimeoutAction;
import com.jaeheonshim.ersgame.util.ERSException;
import com.jaeheonshim.ersgame.game.model.CardType;
import com.jaeheonshim.ersgame.game.model.GameState;
import com.jaeheonshim.ersgame.game.util.GameStateUtil;
import com.jaeheonshim.ersgame.game.model.Player;
import com.jaeheonshim.ersgame.net.model.GameAction;
import com.jaeheonshim.ersgame.net.packet.*;
import com.jaeheonshim.ersgame.server.ERSServer;
import com.jaeheonshim.ersgame.server.GameManager;
import com.jaeheonshim.ersgame.server.ServerPacketListener;
import com.jaeheonshim.ersgame.server.action.NextTurnAction;
import com.jaeheonshim.ersgame.server.action.ReenableSlapsAction;
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
                if(!gameState.isCanPlay()) throw new ERSException("Can't play right now");
                GameStateUtil.playCard(gameState, uuid);

                server.broadcastExcept(new GameActionPacket(GameAction.RECEIVE_CARD), gameState, uuid);
                server.broadcast(new GameStatePacket(gameState), gameState);
                server.schedule(new NextTurnAction(server, gameState));

                // after play, next turn is delayed so that players have a chance to slap
            } else if(gameActionPacket.gameAction == GameAction.SLAP) {
                if(gameState.isIgnoreSlap()) return true;

                boolean isValid = GameStateUtil.isValidSlap(gameState);
                Player player = gameState.getPlayer(uuid);

                if(player.isTimedOut()) throw new ERSException("You aren't allowed to slap right now!");

                if(isValid) {
                    int incrementAmount = gameState.getPileCount();
                    server.broadcast(new OverlayMessagePacket(player.getUsername() + " slapped: +" + incrementAmount + " cards"), gameState);

                    CardType c;
                    while((c = gameState.removeCardFromTop()) != null) {
                        player.addCardToBottom(c);
                    }

                    // After someone slaps, stop play for a few seconds to avoid confusion
                    gameState.setCanPlay(false);
                    server.schedule(new ReenablePlayAction(server, gameState));

                    server.broadcast(new PointChangePacket(player.getUuid(), incrementAmount), gameState);
                } else {
                    if(player.getCardCount() > 0) {
                        server.broadcastExcept(new GameActionPacket(GameAction.OTHERS_DISCARD), gameState, uuid);
                        server.send(new GameActionPacket(GameAction.YOU_DISCARD), uuid);
                        server.broadcast(new OverlayMessagePacket(player.getUsername() + " slapped: -1 cards"), gameState);
                        gameState.addCardToBottom(player.removeTopCard());
                        server.broadcast(new PointChangePacket(player.getUuid(), -1), gameState);

                        if(gameState.getPlayerList().get(gameState.getCurrentTurnIndex()).equals(player.getUuid()) && player.getCardCount() <= 0) {
                            gameState.setCanPlay(false);
                            server.schedule(new NextTurnAction(server, gameState));
                        }
                    } else {
                        float timeAmount = 10;
                        server.broadcast(new OverlayMessagePacket(player.getUsername() + " slapped: " + ((int) timeAmount) + " sec penalty!"), gameState);
                        server.send(new SlapTimeoutPacket(timeAmount), player.getUuid());
                        player.setTimedOut(true);
                        server.schedule(new ReleaseTimeoutAction(timeAmount, player));
                    }
                }

                // after a slap happens, disable slap for a short amount of time to avoid multiple slaps unfairly penalizing players
                gameState.setIgnoreSlap(true);

                server.schedule(new ReenableSlapsAction(server, gameState));
                server.broadcast(new GameStatePacket(gameState), gameState);
            }

            if(GameStateUtil.isGameOver(gameState)) {
                gameState.setGameOver(true);
                server.broadcast(new GameStatePacket(gameState), gameState);
            }

            return true;
        }

        return false;
    }
}
