package com.jaeheonshim.ersgame;

import java.util.function.Consumer;

public interface INatives {
    void showPrompt(String text, String initial, Consumer<String> resultConsumer);
    public boolean isMobileDevice ();
}
