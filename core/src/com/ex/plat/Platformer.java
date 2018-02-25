package com.ex.plat;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.ex.plat.handlers.GameStateManager;
import com.ex.plat.handlers.InputHandler;
import com.ex.plat.handlers.InputProcessor;
import com.ex.plat.handlers.ResourceManager;

import static com.ex.plat.Constants.STEP;
import static com.ex.plat.Constants.V_HEIGHT;
import static com.ex.plat.Constants.V_WIDTH;

public class Platformer extends ApplicationAdapter {

	private SpriteBatch sb;
	private GameStateManager gsm;

	private float accum;

	public static ResourceManager res;

	public SpriteBatch getSpriteBatch() {
		return sb;
	}

	@Override
	public void create () {

		Gdx.input.setInputProcessor(new InputProcessor());


		res = new ResourceManager();

		sb = new SpriteBatch();
		gsm = new GameStateManager(this);
	}

	@Override
	public void render () {

		accum += Gdx.graphics.getDeltaTime();
		while (accum >= STEP) {
			accum -= STEP;
			gsm.update(STEP);
			gsm.render(sb);
			InputHandler.update();
		}

	}
	
	@Override
	public void dispose () {

	}
}
