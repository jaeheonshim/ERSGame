package com.jaeheonshim.ersgame.net.packet;

import com.owlike.genson.Genson;
import com.owlike.genson.GensonBuilder;

public class SocketPacket {
    private static Genson genson = new GensonBuilder().useClassMetadata(true).create();

    public String serialize() {
        return genson.serialize(this);
    }

    public static SocketPacket deserialize(String s) {
        return genson.deserialize(s, SocketPacket.class);
    }
}
