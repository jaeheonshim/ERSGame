package com.jaeheonshim.ersgame.game;

public interface GameActionListener {
    default void onGameStart() {}
    default void onReceiveCard() {}
    default void onTurnUpdate() {}
    default void onDiscard(boolean you) {}
    default void onPointUpdate(String uuid, int amount) {}
}
