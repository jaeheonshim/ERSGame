package com.jaeheonshim.ersgame.server.action;

import com.jaeheonshim.ersgame.game.GameState;
import com.jaeheonshim.ersgame.game.GameStateUtil;
import com.jaeheonshim.ersgame.net.GameAction;
import com.jaeheonshim.ersgame.net.packet.GameActionPacket;
import com.jaeheonshim.ersgame.net.packet.GameStatePacket;
import com.jaeheonshim.ersgame.server.ERSServer;

public class NextTurnAction implements com.jaeheonshim.ersgame.server.action.GameAction {
    private ERSServer server;
    private GameState gameState;

    public NextTurnAction(ERSServer server, GameState gameState) {
        this.server = server;
        this.gameState = gameState;
    }

    @Override
    public void run() {
        GameStateUtil.nextTurn(gameState);

        server.broadcast(new GameStatePacket(gameState), gameState);
        server.broadcast(new GameActionPacket(GameAction.TURN_UPDATE), gameState);
    }

    @Override
    public long getDelay() {
        return 1000;
    }
}
