package com.ex.plat;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.ex.plat.handlers.GameStateManager;
import com.ex.plat.handlers.InputHandler;
import com.ex.plat.handlers.InputProcessor;
import com.ex.plat.handlers.ResourceManager;
import com.ex.plat.states.Play;

import static com.ex.plat.Constants.STEP;

public class Platformer extends Game {

	private SpriteBatch sb;
	//private GameStateManager gsm;

	private float accum;

	public static ResourceManager res;

	public SpriteBatch getSpriteBatch() {
		return sb;
	}

	@Override
	public void create() {

		Gdx.input.setInputProcessor(new InputProcessor());


		res = new ResourceManager();

		sb = new SpriteBatch();
		//gsm = new GameStateManager(this);

		setScreen(new Play(this));
	}

	@Override
	public void render() {

		super.render();
//		accum += Gdx.graphics.getDeltaTime();
//		while (accum >= STEP) {
//			accum -= STEP;
//			gsm.update(STEP);
//			gsm.render(sb);
//			InputHandler.update();
//		}

	}

	@Override
	public void dispose() {

	}
}
