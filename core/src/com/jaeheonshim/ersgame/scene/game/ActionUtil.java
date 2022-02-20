package com.jaeheonshim.ersgame.scene.game;

import com.badlogic.gdx.scenes.scene2d.Action;

public class ActionUtil {
    public static Action flipIn(final float x, final float width, final float duration) {
        return new Action() {
            float done = 0;

            @Override
            public boolean act(float delta) {
                done += delta;
                if (done >= duration) {
                    actor.setX(x);
                    actor.setWidth(width);
                    return true;
                }
                float tmpWidth = width * (done / duration);
                actor.setX(x + ((width / 2) - (tmpWidth / 2)));
                actor.setWidth(tmpWidth);
                return false;
            }
        };
    }

    public static Action flipOut(final float x, final float width, final float duration) {
        return new Action() {
            float left = duration;

            @Override
            public boolean act(float delta) {
                left -= delta;
                if (left <= 0) {
                    actor.setX(x + (width / 2));
                    actor.setWidth(0);
                    return true;
                }
                float tmpWidth = width * (left / duration);
                actor.setX(x + ((width / 2) - (tmpWidth / 2)));
                actor.setWidth(tmpWidth);
                return false;
            }
        };
    }


}
