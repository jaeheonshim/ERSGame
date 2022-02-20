package com.jaeheonshim.ersgame.util;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.jaeheonshim.ersgame.ERSGame;

public class UniversalOnscreenKeyboard implements TextField.OnscreenKeyboard {
    private ERSGame game;
    private TextField textField;

    public UniversalOnscreenKeyboard(ERSGame game, TextField textField) {
        this.game = game;
        this.textField = textField;
    }

    @Override
    public void show(boolean visible) {
        if (Gdx.app.getType() == Application.ApplicationType.WebGL && game.natives.isMobileDevice()) {
            game.natives.showPrompt(textField.getMessageText(), textField.getText(), (result) -> {
                textField.setText(result);
            });
        } else {
            Gdx.input.setOnscreenKeyboardVisible(true);
        }
    }
}
