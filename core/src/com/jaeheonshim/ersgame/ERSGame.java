package com.jaeheonshim.ersgame;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.jaeheonshim.ersgame.scene.StyleUtil;
import com.jaeheonshim.ersgame.screen.GameScreen;
import com.jaeheonshim.ersgame.screen.LobbyScreen;

public class ERSGame extends Game {
	public final String cardsAtlas = "cards/cards.atlas";
	public final String poppins64 = "fonts/poppins-64.fnt";
	public final String poppins24 = "fonts/poppins-24.fnt";
	public final String poppinsBold64 = "fonts/poppins-bold-64.fnt";
	public final String uiBorder = "ui/border.png";
	public final String uiButtonUp = "ui/button_up.png";
	public final String uiButtonDown = "ui/button_down.png";
	public final String fontShader = "shaders/font.vert";

	public final AssetManager assets = new AssetManager();

	private void loadAssets() {
		assets.load(cardsAtlas, TextureAtlas.class);
		assets.load(uiBorder, Texture.class);
		assets.load(uiButtonUp, Texture.class);
		assets.load(uiButtonDown, Texture.class);
		assets.load(poppins64, BitmapFont.class, StyleUtil.dstFieldParameter());
		assets.load(poppinsBold64, BitmapFont.class, StyleUtil.dstFieldParameter());
		assets.load(poppins24, BitmapFont.class, StyleUtil.dstFieldParameter());
		assets.load(fontShader, ShaderProgram.class);

		assets.finishLoading();
	}
	
	@Override
	public void create () {
		loadAssets();
		setScreen(new LobbyScreen(this));
	}

	@Override
	public void render () {
		super.render();
	}
	
	@Override
	public void dispose () {
	}
}
