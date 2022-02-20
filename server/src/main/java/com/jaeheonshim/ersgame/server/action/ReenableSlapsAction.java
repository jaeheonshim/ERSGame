package com.jaeheonshim.ersgame.server.action;

import com.jaeheonshim.ersgame.game.model.GameState;
import com.jaeheonshim.ersgame.game.model.GameStatePhase;
import com.jaeheonshim.ersgame.net.packet.GameStatePacket;
import com.jaeheonshim.ersgame.server.ERSServer;

public class ReenableSlapsAction extends ScheduleGameAction {
    private ERSServer ersServer;
    private GameState gameState;

    public ReenableSlapsAction(ERSServer ersServer, GameState gameState) {
        this.ersServer = ersServer;
        this.gameState = gameState;
    }

    @Override
    public long getDelay() {
        return 1000;
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
