package com.jaeheonshim.ersgame;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.jaeheonshim.ersgame.net.NetManager;
import com.jaeheonshim.ersgame.scene.StyleUtil;
import com.jaeheonshim.ersgame.screen.JoinGameScreen;
import com.jaeheonshim.ersgame.screen.MainScreen;
import com.jaeheonshim.ersgame.util.RandomNameGenerator;

public class ERSGame extends Game {
	public final String cardsAtlas = "cards/cards.atlas";
	public final String poppins64 = "fonts/poppins-64.fnt";
	public final String poppins24 = "fonts/poppins-24.fnt";
	public final String poppinsBold64 = "fonts/poppins-bold-64.fnt";
	public final String uiSkin = "uiskin/skin.json";
	public final String fontShader = "shaders/font.vert";

	public final AssetManager assets = new AssetManager();

	public Screen mainScreen;
	public Screen joinGameScreen;

	private void loadAssets() {
		assets.load(cardsAtlas, TextureAtlas.class);
		assets.load(poppins64, BitmapFont.class, StyleUtil.dstFieldParameter());
		assets.load(poppinsBold64, BitmapFont.class, StyleUtil.dstFieldParameter());
		assets.load(poppins24, BitmapFont.class, StyleUtil.dstFieldParameter());
		assets.load(fontShader, ShaderProgram.class);
		assets.load(uiSkin, Skin.class);

		assets.finishLoading();
	}
	
	@Override
	public void create () {
		loadAssets();
		mainScreen = new MainScreen(this);
		joinGameScreen = new JoinGameScreen(this);
//		NetManager.getInstance().connect();
		RandomNameGenerator.initialize();
		setScreen(mainScreen);
	}

	@Override
	public void render () {
		super.render();
	}
	
	@Override
	public void dispose () {
	}
}
