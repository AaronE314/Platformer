package com.ex.plat.physicsObjects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.ex.plat.handlers.PlayerContactListener;

import static com.ex.plat.Constants.PPM;
import static com.ex.plat.Constants.V_HEIGHT;
import static com.ex.plat.Constants.V_WIDTH;

public class B2DWorld {

    private World world;
    private Box2DDebugRenderer b2dr;
    private OrthographicCamera b2dCam;
    private boolean debug;

    public B2DWorld(Vector2 gravity, boolean debug) {

        this.debug = debug;
        world = new World(gravity, true);
        if (debug) {
            b2dr = new Box2DDebugRenderer();
        }

        world.setContactListener(new PlayerContactListener());

        //Setup box2D cam
        b2dCam = new OrthographicCamera();
        b2dCam.setToOrtho(false, V_WIDTH  / PPM, V_HEIGHT  / PPM);

    }

    public void update(float dt) {

        world.step(dt, 6, 2);

    }

    public void render() {

        Gdx.gl20.glClear(GL20.GL_COLOR_BUFFER_BIT);
        if (debug) {
            b2dr.render(world, b2dCam.combined);
        }
    }

    public World getWorld() {
        return world;
    }

}
