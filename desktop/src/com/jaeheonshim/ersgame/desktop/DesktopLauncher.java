package com.jaeheonshim.ersgame.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.badlogic.gdx.tools.texturepacker.TexturePacker;
import com.jaeheonshim.ersgame.ERSGame;
import com.jaeheonshim.ersgame.net.NetManager;

import java.net.URISyntaxException;

public class DesktopLauncher {
	public static void main (String[] arg) throws URISyntaxException {
		NetManager.initialize();

		TexturePacker.Settings settings = new TexturePacker.Settings();
		settings.maxWidth = 2048;
		settings.maxHeight = 2048;
//		TexturePacker.process(settings, "cards_unpacked", "cards", "cards");

		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width = 768;
		config.height = 1024;
		new LwjglApplication(new ERSGame(), config);
	}
}
