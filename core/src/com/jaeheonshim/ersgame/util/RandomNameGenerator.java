package com.jaeheonshim.ersgame.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class RandomNameGenerator {
    private static List<String> names = new ArrayList<>();

    public static void initialize() {
        BufferedReader reader = new BufferedReader(new InputStreamReader(RandomNameGenerator.class.getResourceAsStream("/names.txt")));
        String line;
        try {
            while (true) {
                if (!((line = reader.readLine()) != null)) break;

                names.add(line.trim());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String randomName() {
        return names.get((int) (Math.random() * names.size()));
    }
}
