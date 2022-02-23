package com.jaeheonshim.ersgame.server.action;

import com.jaeheonshim.ersgame.game.model.GameStatePhase;
import com.jaeheonshim.ersgame.game.model.Player;

public class ReleaseTimeoutAction extends ScheduleGameAction {
    private float timer;
    private Player player;

    public ReleaseTimeoutAction(float timer, Player player) {
        super((long) (timer * 1000));
        this.timer = timer;
        this.player = player;
    }

    @Override
    public void run() {
        player.setTimedOut(false);
    }

    @Override
    protected boolean cancel() {
        return false;
    }
}
