package com.jaeheonshim.ersgame.server.action;

public abstract class ScheduleGameAction implements Runnable {
    private long timer;
    private boolean isRun;
    public abstract long getDelay();

    public ScheduleGameAction() {
        timer = getDelay();
    }

    public boolean update(long deltaMillis) {
        timer -= deltaMillis;
        if(timer <= 0 && !isRun) {
            run();
            isRun = true;
        }

        return isRun;
    }
}
