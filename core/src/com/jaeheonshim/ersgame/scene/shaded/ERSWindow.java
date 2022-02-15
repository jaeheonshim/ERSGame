package com.jaeheonshim.ersgame.scene.shaded;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.jaeheonshim.ersgame.ERSGame;

public class ERSWindow extends Window {
    public ERSWindow(String title, Skin skin, ERSGame game) {
        this(title, skin.get(WindowStyle.class), game);
    }

    public ERSWindow(String title, Skin skin, String styleName, ERSGame game) {
        this(title, skin.get(styleName, WindowStyle.class), game);
    }

    public ERSWindow(String title, WindowStyle style, ERSGame game) {
        super(title, style);

        Table titleTable = getTitleTable();
        titleTable.clearChildren();
        ERSLabel label = new ERSLabel(title, new Label.LabelStyle(style.titleFont, style.titleFontColor), game);
        titleTable.add(label).expandX().fillX().minWidth(0);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
    }
}
