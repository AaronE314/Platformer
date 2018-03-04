package com.ex.plat.physicsObjects;

import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;

import static com.ex.plat.Constants.PPM;
import static com.ex.plat.Constants.STEP;

public class B2DWorld {

    private World world;
    private Box2DDebugRenderer b2dr;
    private boolean debug;

    public B2DWorld(Vector2 gravity, boolean debug) {

        this.debug = debug;
        world = new World(gravity, true);
        if (debug) {
            b2dr = new Box2DDebugRenderer();
        }

    }

    public void update() {

        world.step(STEP, 6, 2);

    }

    public void render(Matrix4 camCpy) {

        if (debug) {
            b2dr.render(world, camCpy.scl(PPM));
        }
    }

    public World getWorld() {
        return world;
    }


    public void dispose() {
        world.dispose();
        if (debug){
            b2dr.dispose();
        }
    }


}
