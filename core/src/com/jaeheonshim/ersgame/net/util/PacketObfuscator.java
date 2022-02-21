package com.jaeheonshim.ersgame.net.util;

import java.nio.charset.StandardCharsets;

public class PacketObfuscator {
    private static char KEY = 0xe7;

    public static String applyMask(String packet) {
        char[] chars = packet.toCharArray();
        for(int i = 0; i < chars.length; i++) {
            chars[i] = (char) (chars[i] ^ KEY);
        }

        return new String(chars);
    }
}
