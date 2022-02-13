package com.jaeheonshim.ersgame;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.jaeheonshim.ersgame.screen.GameScreen;

public class ERSGame extends Game {
	public final String cardsAtlas = "cards/cards.atlas";
	public final String poppins64 = "fonts/poppins-64.fnt";
	public final String poppins48 = "fonts/poppins-48.fnt";
	public final String uiBorder = "ui/border.png";

	public final AssetManager assets = new AssetManager();

	private void loadAssets() {
		assets.load(cardsAtlas, TextureAtlas.class);
		assets.load(poppins64, BitmapFont.class);
		assets.load(poppins48, BitmapFont.class);
		assets.load(uiBorder, Texture.class);

		assets.finishLoading();
	}
	
	@Override
	public void create () {
		loadAssets();
		setScreen(new GameScreen(this));
	}

	@Override
	public void render () {
		super.render();
	}
	
	@Override
	public void dispose () {
	}
}
