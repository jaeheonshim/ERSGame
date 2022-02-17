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
        return json.fromJson(SocketPacket.class, s);
    }

    public static void main(String[] args) {
        SocketConnectPacket packet = new SocketConnectPacket();
        packet.uuid = "asdf";

        String serialized = packet.serialize();
        System.out.println(serialized);
        System.out.println(((SocketConnectPacket) SocketPacket.deserialize(serialized)).uuid);
    }
}
