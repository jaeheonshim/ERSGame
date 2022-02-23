package com.jaeheonshim.ersgame.server.action;

import com.jaeheonshim.ersgame.game.model.GameState;
import com.jaeheonshim.ersgame.game.model.GameStatePhase;
import com.jaeheonshim.ersgame.net.packet.GameStatePacket;
import com.jaeheonshim.ersgame.server.ERSServer;

public class ReenableSlapsAction extends ScheduleGameAction {
    private ERSServer ersServer;
    private GameState gameState;
    private static final long DEFAULT_DELAY = 1000;

    public ReenableSlapsAction(ERSServer ersServer, GameState gameState) {
        super(DEFAULT_DELAY);
        this.ersServer = ersServer;
        this.gameState = gameState;
    }

    public ReenableSlapsAction(ERSServer ersServer, GameState gameState, long delay) {
        super(delay);
        this.ersServer = ersServer;
        this.gameState = gameState;
    }

    @Override
    public void run() {
        gameState.setIgnoreSlap(false);
        ersServer.broadcast(new GameStatePacket(gameState), gameState);
    }

    @Override
    protected boolean cancel() {
        return gameState.getGamePhase() != GameStatePhase.STARTED;
    }
}
