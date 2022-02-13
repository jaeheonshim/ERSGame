package com.jaeheonshim.ersgame.scene;

import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.jaeheonshim.ersgame.ERSGame;
import com.jaeheonshim.ersgame.scene.shaded.ERSTextButton;

public class PlayButton extends ERSTextButton {
    public PlayButton(ERSGame game) {
        super("Play", StyleUtil.textButtonStyle(game), game);
    }
}
