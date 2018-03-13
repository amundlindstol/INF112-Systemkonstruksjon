package com.grnn.chess.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.grnn.chess.ChessForKids;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width = 1000;
		config.height = 700;
		config.title = "Chess for kids";
		config.resizable = false;
		new LwjglApplication(new ChessForKids(), config);
	}
}
