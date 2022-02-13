package com.jaeheonshim.ersgame.scene;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.loaders.BitmapFontLoader;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;
import com.jaeheonshim.ersgame.ERSGame;

public class StyleUtil {
    public static TextButton.TextButtonStyle textButtonStyle(ERSGame game) {
        NinePatchDrawable upDrawable = new NinePatchDrawable(new NinePatch(game.assets.get(game.uiButtonUp, Texture.class), 1, 3, 1, 1));
        NinePatchDrawable downDrawable = new NinePatchDrawable(new NinePatch(game.assets.get(game.uiButtonDown, Texture.class), 1, 3, 1, 1));

        TextButton.TextButtonStyle style = new TextButton.TextButtonStyle(upDrawable, downDrawable, downDrawable, game.assets.get(game.poppins64, BitmapFont.class));
        style.fontColor = Color.BLACK;
        return style;
    }

    public static BitmapFontLoader.BitmapFontParameter dstFieldParameter() {
        BitmapFontLoader.BitmapFontParameter parameter = new BitmapFontLoader.BitmapFontParameter();
        parameter.magFilter = Texture.TextureFilter.Linear;
        parameter.minFilter = Texture.TextureFilter.MipMapLinearNearest;
        parameter.genMipMaps = true;

        return parameter;
    }
}
