package com.ex.plat.states;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.ex.plat.Platformer;
import com.ex.plat.camera.OrthoCamera;
import com.ex.plat.handlers.GameStateManager;

import static com.ex.plat.Constants.V_HEIGHT;
import static com.ex.plat.Constants.V_WIDTH;

public abstract class GameState {

    protected GameStateManager gsm;
    protected Platformer game;

    protected SpriteBatch sb;
    protected OrthoCamera cam;
    protected OrthographicCamera hudCam;


    protected  GameState(GameStateManager gsm) {
        this.gsm = gsm;
        game = gsm.game();
        sb = game.getSpriteBatch();
        cam = new OrthoCamera();
        hudCam = new OrthographicCamera();
        cam.setToOrtho(false, V_WIDTH, V_HEIGHT);
        hudCam.setToOrtho(false, V_WIDTH, V_HEIGHT);

    }


    public abstract void handleInput();
    public abstract void update(float dt);
    public abstract void render(SpriteBatch sb);
    public abstract void dispose();

    public OrthographicCamera getCamera() {
        return cam;
    }

    public OrthographicCamera getHudCam() {
        return hudCam;
    }

}
