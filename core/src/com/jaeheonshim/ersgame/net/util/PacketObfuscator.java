package com.jaeheonshim.ersgame.net.util;

import java.nio.charset.StandardCharsets;

public class PacketObfuscator {
    private static char KEY = 0x487a;
    private static char INCREMENT = 0xF;

    public static String applyMask(String packet) {
        char key = KEY;
        char[] chars = packet.toCharArray();
        for(int i = 0; i < chars.length; i++) {
            chars[i] = (char) (chars[i] ^ key);
            key += INCREMENT;
        }

        return new String(chars);
    }
}
