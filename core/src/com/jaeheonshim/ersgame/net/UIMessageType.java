package com.jaeheonshim.ersgame.net;

public enum UIMessageType {
    SUCCESS("success"),
    INFO("info"),
    WARNING("warning"),
    ERROR("error");

    public String styleName;

    UIMessageType(String styleName) {
        this.styleName = styleName;
    }
}
