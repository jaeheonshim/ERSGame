package com.jaeheonshim.ersgame.util;

import java.util.Random;

public class UUIDUtil {
    private static Random numberGenerator = new Random();
    private static final long MSB = 0x8000000000000000L;

    public static String unique() {
        return Long.toHexString(MSB | numberGenerator.nextLong()) + Long.toHexString(MSB | numberGenerator.nextLong());
    }
}
