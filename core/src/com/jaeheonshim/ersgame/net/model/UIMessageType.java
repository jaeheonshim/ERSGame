package com.jaeheonshim.ersgame.net.model;

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
