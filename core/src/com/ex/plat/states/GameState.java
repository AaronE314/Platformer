package com.ex.plat.states;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.ex.plat.Platformer;
import com.ex.plat.handlers.GameStateManager;

public abstract class GameState {

    protected GameStateManager gsm;
    protected Platformer game;

    protected SpriteBatch sb;
    protected OrthographicCamera cam;
    protected  OrthographicCamera hudCam;


    protected  GameState(GameStateManager gsm) {
        this.gsm = gsm;
        game = gsm.game();
        sb = game.getSpriteBatch();
        cam = game.getCamera();
        hudCam = game.getHudCam();

    }


    public abstract void handleInput();
    public abstract void update(float dt);
    public abstract void render();
    public abstract void dispose();

}
