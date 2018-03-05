package com.ex.plat;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.ex.plat.handlers.ResourceManager;
import com.ex.plat.states.Play;

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

		//Gdx.input.setInputProcessor(new InputProcessor());


		res = new ResourceManager();

		sb = new SpriteBatch();
		//gsm = new GameStateManager(this);

		setScreen(new Play(this));
	}

	@Override
	public void render() {

		super.render();

	}

	@Override
	public void dispose() {
		super.dispose();
		sb.dispose();
	}
}
