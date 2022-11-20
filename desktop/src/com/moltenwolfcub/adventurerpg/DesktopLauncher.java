package com.moltenwolfcub.adventurerpg;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import com.badlogic.gdx.tools.texturepacker.TexturePacker;
import com.moltenwolfcub.adventurerpg.util.Constants;

// Please note that on macOS your application needs to be started with the -XstartOnFirstThread JVM argument
public class DesktopLauncher {
	public static void main (String[] arg) {
		packTextures();

		Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
		config.setTitle("Adventure RPG");
		config.setWindowedMode(Constants.WINDOW_WIDTH, Constants.WINDOW_HEIGHT);
		config.setForegroundFPS(60);
		new Lwjgl3Application(new Rpg(), config);
	}

	private static void packTextures() {
		TexturePacker.process("main/textures", "main/atlases", "spriteTextureMap.atlas");
	}
}
