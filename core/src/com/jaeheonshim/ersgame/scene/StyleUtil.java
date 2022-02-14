package com.jaeheonshim.ersgame.scene;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.loaders.BitmapFontLoader;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;
import com.jaeheonshim.ersgame.ERSGame;

public class StyleUtil {
    public static Label.LabelStyle labelStyle64(ERSGame game) {
        Label.LabelStyle labelStyle = new Label.LabelStyle(game.assets.get(game.poppins64, BitmapFont.class), Color.BLACK);
        return labelStyle;
    }

    public static Label.LabelStyle labelStyle24(ERSGame game) {
        Label.LabelStyle labelStyle = new Label.LabelStyle(game.assets.get(game.poppins24, BitmapFont.class), Color.BLACK);
        return labelStyle;
    }

    public static TextButton.TextButtonStyle textButtonStyle(ERSGame game) {
//        NinePatchDrawable upDrawable = new NinePatchDrawable(new NinePatch(game.assets.get(game.uiButtonUp, Texture.class), 16, 16, 16, 16));
//        NinePatchDrawable downDrawable = new NinePatchDrawable(new NinePatch(game.assets.get(game.uiButtonDown, Texture.class), 16, 16, 16, 16));
//
//        TextButton.TextButtonStyle style = new TextButton.TextButtonStyle(upDrawable, downDrawable, downDrawable, game.assets.get(game.poppinsBold64, BitmapFont.class));
//        style.fontColor = Color.BLACK;
        return null;
    }

    public static BitmapFontLoader.BitmapFontParameter dstFieldParameter() {
        BitmapFontLoader.BitmapFontParameter parameter = new BitmapFontLoader.BitmapFontParameter();
        parameter.magFilter = Texture.TextureFilter.Linear;
        parameter.minFilter = Texture.TextureFilter.MipMapLinearNearest;
        parameter.genMipMaps = true;

        return parameter;
    }
}
