package com.jaeheonshim.ersgame;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.ScreenUtils;

public class ERSGame extends ApplicationAdapter {
	SpriteBatch batch;
	TextureRegion img;

	public final String cardsAtlas = "cards/cards.atlas";

	public final AssetManager assets = new AssetManager();

	private void loadAssets() {
		assets.load(cardsAtlas, TextureAtlas.class);

		assets.finishLoading();
	}
	
	@Override
	public void create () {
		loadAssets();
		batch = new SpriteBatch();
		img = assets.get(cardsAtlas, TextureAtlas.class).findRegion("Tiles_A_white");
	}

	@Override
	public void render () {
		ScreenUtils.clear(1, 0, 0, 1);
		batch.begin();
		batch.draw(img, 0, 0);
		batch.end();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
	}
}
