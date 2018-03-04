package com.ex.plat.states;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.ex.plat.Platformer;

import static com.ex.plat.Constants.V_HEIGHT;
import static com.ex.plat.Constants.V_WIDTH;

public abstract class GameState implements Screen{

    //protected GameStateManager gsm;
    protected Platformer game;

    protected OrthographicCamera cam;
    protected Viewport gamePort;


    protected  GameState(Platformer game) {
        //this.gsm = gsm;
        this.game = game;
        cam = new OrthographicCamera();
        gamePort = new FitViewport(V_WIDTH, V_HEIGHT, cam);

        cam.position.set(gamePort.getWorldWidth() / 2, gamePort.getWorldHeight() / 2, 0);

    }


    public abstract void handleInput();
    public abstract void update(float dt);
    //public abstract void render(SpriteBatch sb);
    //public abstract void dispose();

    public OrthographicCamera getCamera() {
        return cam;
    }

    @Override
    public void resize(int width, int height) {
        gamePort.update(width, height);
    }
}
