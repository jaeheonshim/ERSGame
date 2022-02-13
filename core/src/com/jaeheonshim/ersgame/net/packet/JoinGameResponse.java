package com.jaeheonshim.ersgame.net.packet;

public class JoinGameResponse {
    public enum Status {
        SUCCESS,
        INVALID_CODE,
        USERNAME_TAKEN
    }

    public Status status;
    public String uuid;
}
