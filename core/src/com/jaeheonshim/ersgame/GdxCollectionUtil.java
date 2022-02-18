package com.jaeheonshim.ersgame;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;

public class GdxCollectionUtil {
    public static void shuffle(Array array) {
        for(int i = array.size - 1; i >= 1; i--) {
            int j = MathUtils.random(i + 1);
            Object temp = array.get(j);
            array.set(j, array.get(i));
            array.set(i, temp);
        }
    }
}
