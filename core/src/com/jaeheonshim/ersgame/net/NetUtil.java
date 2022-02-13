package com.jaeheonshim.ersgame.net;

import com.esotericsoftware.kryo.Kryo;
import com.jaeheonshim.ersgame.game.CardType;
import com.jaeheonshim.ersgame.net.packet.GameStateUpdateEvent;
import com.jaeheonshim.ersgame.net.packet.JoinGameEvent;
import com.jaeheonshim.ersgame.net.packet.JoinGameRequest;
import com.jaeheonshim.ersgame.net.packet.JoinGameResponse;

public class NetUtil {
    public static void register(Kryo kryo) {
        kryo.register(CardType.class);
        kryo.register(CardType[].class);
        kryo.register(GameStateUpdateEvent.PlayerInfo.class);
        kryo.register(GameStateUpdateEvent.PlayerInfo[].class);

        kryo.register(JoinGameRequest.class);
        kryo.register(JoinGameResponse.class);
        kryo.register(JoinGameResponse.Status.class);
        kryo.register(JoinGameEvent.class);
        kryo.register(GameStateUpdateEvent.class);
    }
}
