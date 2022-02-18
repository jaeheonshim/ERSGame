package com.jaeheonshim.ersgame.net.packet;

import com.badlogic.gdx.utils.Json;
import com.owlike.genson.Genson;
import com.owlike.genson.GensonBuilder;

public class SocketPacket {
    private static Json json = new Json();

    public String serialize() {
        return json.toJson(this, SocketPacket.class);
    }

    public static SocketPacket deserialize(String s) {
        return (SocketPacket) json.fromJson(Object.class, s);
    }
}
