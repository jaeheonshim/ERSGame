package com.jaeheonshim.ersgame.server.action;

public abstract class ScheduleGameAction implements Runnable {
    private long timer;
    private boolean isRun;

    public ScheduleGameAction(long delay) {
        timer = delay;
    }

    public boolean update(long deltaMillis) {
        if(cancel()) return true;

        timer -= deltaMillis;
        if(timer <= 0 && !isRun) {
            run();
            isRun = true;
        }

        return isRun;
    }

    protected abstract boolean cancel();
}
