package com.jaeheonshim.ersgame;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.jaeheonshim.ersgame.net.NetManager;
import com.jaeheonshim.ersgame.scene.OverlayStage;
import com.jaeheonshim.ersgame.screen.*;

public class ERSGame extends Game {
	public final String defaultAtlas = "images.atlas";
	public final String cardsAtlas = "cards/cards.atlas";
	public final String uiSkin = "uiskin/skin.json";
	public final String fontShader = "shaders/font.vert";

	public final AssetManager assets = new AssetManager();

	public Screen mainScreen;
	public Screen joinGameScreen;
	public Screen createGameScreen;
	public Screen lobbyScreen;
	public Screen gameScreen;

	private void loadAssets() {
		assets.load(cardsAtlas, TextureAtlas.class);
		assets.load(defaultAtlas, TextureAtlas.class);
		assets.load(fontShader, ShaderProgram.class);
		assets.load(uiSkin, Skin.class);

		assets.finishLoading();
	}

	@Override
	public void create () {
		loadAssets();
		OverlayStage.initialize(this);
		mainScreen = new MainScreen(this);
		joinGameScreen = new JoinGameScreen(this);
		createGameScreen = new CreateGameScreen(this);
		lobbyScreen = new LobbyScreen(this);
		gameScreen = new GameScreen(this);

		NetManager.getInstance().connect();
		setScreen(mainScreen);
//		setScreen(lobbyScreen);
	}

	@Override
	public void render () {
		super.render();
		OverlayStage.getInstance().act();
		OverlayStage.getInstance().draw();
	}

	@Override
	public void resize(int width, int height) {
		super.resize(width, height);
		OverlayStage.getInstance().getViewport().update(width, height, true);
	}

	@Override
	public void dispose () {
	}
}
