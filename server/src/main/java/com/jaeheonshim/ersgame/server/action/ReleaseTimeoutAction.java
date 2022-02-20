package com.jaeheonshim.ersgame.server.action;

import com.jaeheonshim.ersgame.game.model.Player;

public class ReleaseTimeoutAction implements GameAction {
    private float timer;
    private Player player;

    public ReleaseTimeoutAction(float timer, Player player) {
        this.timer = timer;
        this.player = player;
    }

    @Override
    public long getDelay() {
        return (long) (timer * 1000);
    }

    @Override
    public void run() {
        player.setTimedOut(false);
    }
}
