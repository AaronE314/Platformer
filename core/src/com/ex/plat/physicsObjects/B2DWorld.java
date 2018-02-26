package com.ex.plat.physicsObjects;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;

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

        //Setup box2D cam
        b2dCam = new OrthographicCamera();
        b2dCam.setToOrtho(false, V_WIDTH  / PPM, V_HEIGHT  / PPM);

    }

    public void update(float dt) {

        world.step(dt, 6, 2);

    }

    public void render(Matrix4 camCpy) {

        if (debug) {
            b2dr.render(world, camCpy.scl(PPM));
        }
    }

    public World getWorld() {
        return world;
    }


}
