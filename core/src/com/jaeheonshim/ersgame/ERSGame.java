package com.jaeheonshim.ersgame;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.ScreenUtils;
import com.jaeheonshim.ersgame.screen.GameScreen;

public class ERSGame extends Game {
	public final String cardsAtlas = "cards/cards.atlas";

	public final AssetManager assets = new AssetManager();

	private void loadAssets() {
		assets.load(cardsAtlas, TextureAtlas.class);

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
