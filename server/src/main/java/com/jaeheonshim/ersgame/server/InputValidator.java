package com.jaeheonshim.ersgame.server;

public class InputValidator {
    public static final int MAX_USERNAME_LENGTH = 12;

    public static boolean validateUsername(String username) {
        if(username.length() > MAX_USERNAME_LENGTH) return false;

        for(char c : username.toCharArray()) {
            if(!Character.isAlphabetic(c)) return false;
        }

        return true;
    }

    public static boolean validateGameCode(String gameCode) {
        for(char c : gameCode.toCharArray()) {
            if(!Character.isDigit(c)) return false;
        }

        return true;
    }
}
