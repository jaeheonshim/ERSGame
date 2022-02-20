package com.jaeheonshim.ersgame.client;

import com.google.gwt.core.client.Scheduler;
import com.google.gwt.user.client.Window;
import com.jaeheonshim.ersgame.util.INatives;

import java.util.function.Consumer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GwtNatives implements INatives {
    private native static void setupInputField(String prompt) /*-{
        prompt(prompt);
        console.log("hello");
    }-*/;

    private native void consoleLog(String message) /*-{
      console.log( message );
    }-*/;

    @Override
    public void showPrompt(String text, String initial, Consumer<String> resultConsumer) {
        Scheduler.get().scheduleDeferred(new Scheduler.ScheduledCommand() {
            @Override
            public void execute() {
                String result = Window.prompt(text, initial);
                consoleLog("Input from user: " + result);
                resultConsumer.accept(result);

            }
        });
    }

    public boolean isMobileDevice () {
        // RegEx pattern from detectmobilebrowsers.com (public domain)
        String pattern = "(android|bb\\d+|meego).+mobile|avantgo|bada\\/|blackberry|blazer|compal|elaine|fennec"
                + "|hiptop|iemobile|ip(hone|od)|iris|kindle|lge |maemo|midp|mmp|mobile.+firefox|netfront|opera m(ob|in)"
                + "i|palm( os)?|phone|p(ixi|re)\\/|plucker|pocket|psp|series(4|6)0|symbian|treo|up\\.(browser|link)"
                + "|vodafone|wap|windows ce|xda|xiino|android|ipad|playbook|silk";
        Pattern p = Pattern.compile(pattern);
        Matcher m = p.matcher(Window.Navigator.getUserAgent().toLowerCase());
        return m.find();
    }
}
