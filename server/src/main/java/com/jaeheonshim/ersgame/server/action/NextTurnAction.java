package com.jaeheonshim.ersgame.server.action;

import com.jaeheonshim.ersgame.game.model.GameState;
import com.jaeheonshim.ersgame.game.model.GameStatePhase;
import com.jaeheonshim.ersgame.game.util.GameStateUtil;
import com.jaeheonshim.ersgame.net.model.GameAction;
import com.jaeheonshim.ersgame.net.packet.GameActionPacket;
import com.jaeheonshim.ersgame.net.packet.GameStatePacket;
import com.jaeheonshim.ersgame.server.ERSServer;

public class NextTurnAction extends ScheduleGameAction {
    private ERSServer server;
    private GameState gameState;
    private boolean switchTurn;
    private static long DEFAULT_DELAY = 750;

    public NextTurnAction(ERSServer server, GameState gameState, boolean switchTurn) {
        super(DEFAULT_DELAY);
        this.server = server;
        this.gameState = gameState;
        this.switchTurn = switchTurn;
    }

    public NextTurnAction(ERSServer server, GameState gameState, boolean switchTurn, long delay) {
        super(delay);
        this.server = server;
        this.gameState = gameState;
        this.switchTurn = switchTurn;
    }

    @Override
    public void run() {
        GameStateUtil.nextTurn(gameState, switchTurn);

        server.broadcast(new GameStatePacket(gameState), gameState);
        server.broadcast(new GameActionPacket(GameAction.TURN_UPDATE), gameState);
    }

    @Override
    protected boolean cancel() {
        return gameState.getGamePhase() != GameStatePhase.STARTED;
    }
}
