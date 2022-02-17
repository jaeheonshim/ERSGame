package com.jaeheonshim.ersgame.scene.action;

import com.badlogic.gdx.scenes.scene2d.actions.AlphaAction;
import com.badlogic.gdx.scenes.scene2d.actions.DelayAction;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.badlogic.gdx.scenes.scene2d.actions.VisibleAction;

public class DisappearAction extends SequenceAction {
    public DisappearAction(float duration) {
        DelayAction delayAction = new DelayAction(duration * 0.8f);

        AlphaAction alphaAction = new AlphaAction();
        alphaAction.setDuration(duration * 0.2f);
        alphaAction.setAlpha(0);

        VisibleAction visibleAction = new VisibleAction();
        visibleAction.setVisible(false);

        addAction(delayAction);
        addAction(alphaAction);
        addAction(visibleAction);
    }
}
