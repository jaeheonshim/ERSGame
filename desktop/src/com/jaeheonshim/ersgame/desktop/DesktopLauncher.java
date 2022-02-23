package com.jaeheonshim.ersgame.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.badlogic.gdx.tools.texturepacker.TexturePacker;
import com.github.czyzby.websocket.CommonWebSockets;
import com.github.czyzby.websocket.WebSockets;
import com.jaeheonshim.ersgame.ERSGame;
import com.jaeheonshim.ersgame.net.NetManager;
import org.apache.commons.cli.*;

import java.net.URISyntaxException;

public class DesktopLauncher {
	public static void main (String[] args) throws URISyntaxException, ParseException {
		Options options = new Options();
		options.addOption("p", "port",true, "server port");
		options.addOption("h", "hostname",true, "specify server hostname");
		options.addOption("ssl", "Use SSL");

		CommandLineParser parser = new DefaultParser();
		CommandLine cmd = parser.parse(options, args);

		String host = cmd.getOptionValue("h", "ers.jaeheonshim.dev");
		int port = Integer.parseInt(cmd.getOptionValue("p", "8080"));
		boolean ssl = cmd.hasOption("ssl");

		CommonWebSockets.initiate();

		TexturePacker.Settings settings = new TexturePacker.Settings();
		settings.maxWidth = 2048;
		settings.maxHeight = 2048;
//		TexturePacker.process(settings, "cards_unpacked", "cards", "cards");
//		TexturePacker.process(settings, "images", ".", "images");

		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width = 768;
		config.height = 1024;
		new LwjglApplication(new ERSGame(), config);

		if(ssl) {
			NetManager.initialize(WebSockets.toSecureWebSocketUrl(host, port));
		} else {
			NetManager.initialize(WebSockets.toWebSocketUrl(host, port));
		}
	}
}
