package com.ex.plat.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.ex.plat.Constants;
import com.ex.plat.Platformer;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();

		config.title = Constants.TITLE;
		config.width = Constants.V_WIDTH * Constants.SCALE;
		config.height = Constants.V_HEIGHT * Constants.SCALE;

		new LwjglApplication(new Platformer(), config);
	}
}
