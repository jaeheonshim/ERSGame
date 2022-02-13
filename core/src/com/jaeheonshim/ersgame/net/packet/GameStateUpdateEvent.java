package com.jaeheonshim.ersgame.net.packet;

import com.jaeheonshim.ersgame.game.CardType;

import java.util.Deque;

public class GameStateUpdateEvent {
    public String joinCode;
    public CardType[] pile;
    public PlayerInfo[] players;
    public String currentTurnUUID;
    public String gameAdminUUID;

    public static class PlayerInfo {
        public String uuid;
        public String name;
        public int cardCount;
        public int ordinal;
    }
}
