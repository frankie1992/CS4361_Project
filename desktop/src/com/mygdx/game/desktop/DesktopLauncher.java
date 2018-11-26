package com.mygdx.game.desktop;


import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.MainMenu;
public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
 		// Set the game title
		config.title = "Asteroid";
		// Set the game window resolution
		config.width = 1152;
		config.height = 864;

		// Dis allow the change of screen size
		config.fullscreen = false;
		config.resizable = false;

 		new LwjglApplication(new MyGdxGame(), config);

	}
}
