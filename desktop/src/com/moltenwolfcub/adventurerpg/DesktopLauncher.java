package com.moltenwolfcub.adventurerpg;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import com.moltenwolfcub.adventurerpg.Rpg;
import com.moltenwolfcub.adventurerpg.util.Constants;

// Please note that on macOS your application needs to be started with the -XstartOnFirstThread JVM argument
public class DesktopLauncher {
	public static void main (String[] arg) {
		Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
		config.setTitle("Adventure RPG");
		config.setWindowedMode(Constants.DESKTOP_WINDOW_WIDTH, Constants.DESKTOP_WINDOW_HEIGHT);
		config.setForegroundFPS(60);
		new Lwjgl3Application(new Rpg(), config);
	}
}
