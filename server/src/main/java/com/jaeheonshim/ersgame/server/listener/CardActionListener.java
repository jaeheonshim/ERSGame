package com.jaeheonshim.ersgame.server.listener;

import com.jaeheonshim.ersgame.game.GameStateManager;
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
            if(gameActionPacket.gameAction != GameAction.PLAY_CARD && gameActionPacket.gameAction != GameAction.SLAP) return false;
            GameState gameState = GameManager.getInstance().getGameOfPlayer(uuid);

            if(gameState == null) throw new ERSException("Player not in game");

            if(gameActionPacket.gameAction == GameAction.PLAY_CARD) {
                if(!gameState.isCanPlay()) throw new ERSException("Can't play right now");
                boolean switchTurn = GameStateUtil.playCard(gameState, uuid);

                if(gameState.getPendingCardCount() == 0 && gameState.getLastFacePlayer() != null && !gameState.getLastFacePlayer().equals(uuid)) {
                    server.broadcastExcept(new GameActionPacket(GameAction.RECEIVE_CARD), gameState, uuid);
                    server.broadcast(new GameStatePacket(gameState), gameState);
                    awardDeck(gameState, gameState.getPlayer(gameState.getLastFacePlayer()));
                    GameStateUtil.nextTurn(gameState, true);
                    server.broadcast(new GameStatePacket(gameState), gameState);
                } else {
                    server.broadcastExcept(new GameActionPacket(GameAction.RECEIVE_CARD), gameState, uuid);
                    server.broadcast(new GameStatePacket(gameState), gameState);

                    GameManager.getInstance().schedule(new NextTurnAction(server, gameState, switchTurn));
                }

                // after play, next turn is delayed so that players have a chance to slap
            } else if(gameActionPacket.gameAction == GameAction.SLAP) {
                if(gameState.isIgnoreSlap()) return true;

                boolean isValid = GameStateUtil.isValidSlap(gameState);
                Player player = gameState.getPlayer(uuid);

                if(player.isTimedOut()) throw new ERSException("You aren't allowed to slap right now!");

                if(isValid) {
                    awardDeck(gameState, player);
                } else {
                    if(player.getCardCount() > 0) {
                        server.broadcastExcept(new GameActionPacket(GameAction.OTHERS_DISCARD), gameState, uuid);
                        server.send(new GameActionPacket(GameAction.YOU_DISCARD), uuid);
                        server.broadcast(new OverlayMessagePacket(player.getUsername() + " slapped: -1 cards"), gameState);
                        gameState.addCardToBottom(player.removeTopCard());
                        server.broadcast(new PointChangePacket(player.getUuid(), -1), gameState);

                        if(gameState.getPlayerList().get(gameState.getCurrentTurnIndex()).equals(player.getUuid()) && player.getCardCount() <= 0) {
                            gameState.setCanPlay(false);
                            GameManager.getInstance().schedule(new NextTurnAction(server, gameState, true));
                        }
                    } else {
                        float timeAmount = 10;
                        server.broadcast(new OverlayMessagePacket(player.getUsername() + " slapped: " + ((int) timeAmount) + " sec penalty!"), gameState);
                        server.send(new SlapTimeoutPacket(timeAmount), player.getUuid());
                        player.setTimedOut(true);
                        GameManager.getInstance().schedule(new ReleaseTimeoutAction(timeAmount, player));
                    }
                }

                // after a slap happens, disable slap for a short amount of time to avoid multiple slaps unfairly penalizing players
                gameState.setIgnoreSlap(true);

                GameManager.getInstance().schedule(new ReenableSlapsAction(server, gameState));
                server.broadcast(new GameStatePacket(gameState), gameState);
            }

            if(GameStateUtil.isGameOver(gameState)) {
                gameState.setGameOver(true);

                String winner = "none";
                for (Player player : gameState.getPlayerMap().values()) {
                    if (player.getCardCount() > 0) {
                        winner = player.getUsername();
                        break;
                    }
                }

                gameState.setWinner(winner);

                server.broadcast(new GameStatePacket(gameState), gameState);
                gameState.reset();
            }

            return true;
        }

        return false;
    }

    private void awardDeck(GameState gameState, Player awardedPlayer) {
        gameState.setLastFacePlayer(null);
        gameState.setPendingCardCount(0);

        int incrementAmount = gameState.getPileCount();
        server.broadcast(new OverlayMessagePacket(awardedPlayer.getUsername() + ": +" + incrementAmount + " cards"), gameState);

        CardType c;
        while((c = gameState.removeCardFromTop()) != null) {
            awardedPlayer.addCardToBottom(c);
        }

        // After someone slaps, stop play for a few seconds to avoid confusion
        gameState.setCanPlay(false);
        GameManager.getInstance().schedule(new ReenablePlayAction(server, gameState));

        server.broadcast(new PointChangePacket(awardedPlayer.getUuid(), incrementAmount), gameState);
    }
}
