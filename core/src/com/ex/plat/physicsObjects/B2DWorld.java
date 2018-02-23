package com.ex.plat.physicsObjects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.Shape;
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

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(50 / PPM, 5 / PPM);
        Body body1 = createBody(160,100, BodyDef.BodyType.StaticBody, shape);

        PolygonShape shape2 = new PolygonShape();
        shape.setAsBox(5 / PPM, 5 / PPM);
        Body body2 = createBody(160,200, BodyDef.BodyType.DynamicBody, shape);

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

    /**
     * Creates and returns a body with a shape fixture
     * @param x x pos
     * @param y y pos
     * @param type body type, Static, Dynamic, or Kinematic
     * @param shape a shape object to define the shape of the body
     * @return the created body
     */
    public Body createBody(float x, float y, BodyDef.BodyType type, Shape shape) {
        BodyDef bdef = new BodyDef();
        bdef.position.set(x / PPM, y / PPM);
        bdef.type = type;
        Body body = world.createBody(bdef);

        FixtureDef fdef = new FixtureDef();
        fdef.shape = shape;
        body.createFixture(fdef);

        return body;
    }
}
