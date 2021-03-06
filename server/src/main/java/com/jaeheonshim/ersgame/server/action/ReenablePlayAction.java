package com.jaeheonshim.ersgame.server.action;

import com.jaeheonshim.ersgame.game.model.GameState;
import com.jaeheonshim.ersgame.game.model.GameStatePhase;
import com.jaeheonshim.ersgame.net.packet.GameStatePacket;
import com.jaeheonshim.ersgame.server.ERSServer;

public class ReenablePlayAction extends ScheduleGameAction {
    private ERSServer server;
    private GameState gameState;

    private static final long DEFAULT_DELAY = 2000;

    public ReenablePlayAction(ERSServer server, GameState gameState) {
        super(DEFAULT_DELAY);
        this.server = server;
        this.gameState = gameState;
    }

    @Override
    public void run() {
        gameState.setCanPlay(true);
        server.broadcast(new GameStatePacket(gameState), gameState);
    }

    @Override
    protected boolean cancel() {
        return gameState.getGamePhase() != GameStatePhase.STARTED;
    }
}
