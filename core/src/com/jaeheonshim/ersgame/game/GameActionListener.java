package com.jaeheonshim.ersgame.game;

public interface GameActionListener {
    default void onGameStart() {}
    default void onReceiveCard() {}
    default void onTurnUpdate() {}
}
