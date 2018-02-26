package com.ex.plat.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.ex.plat.Platformer;
import com.ex.plat.handlers.InputHandler;
import com.ex.plat.physicsObjects.B2DWorld;
import com.ex.plat.states.GameState;

import static com.ex.plat.Constants.BIT_PLAYER;
import static com.ex.plat.Constants.PPM;
import static com.ex.plat.Constants.V_HEIGHT;
import static com.ex.plat.Constants.V_WIDTH;
import static com.ex.plat.handlers.InputProcessor.*;

public class Player extends Entity {

    private int playerOnGround = 0;

    private final float MAX_VEL_WALK = 1;
    private final float MAX_VEL_SPRINT = 4;
    private final float groundAcc = 0.20f;
    private final float airAcc = 0.05f;

    private float maxVel = MAX_VEL_WALK;
    private float deceleration = 0.15f;
    private float decel;
    private Vector2 movement;
    private Vector2 vel;


    public Player(B2DWorld world, Vector2 pos) {
        //super(world, pos,"badlogic.jpg", "Player");
        super(world, pos, 5, 5);
        movement = new Vector2();
    }

    @Override
    protected void createEntity() {
        System.out.println(width / PPM + " " + height / PPM);
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(this.bodyW / PPM, this.bodyH / PPM);
        BodyDef bdef = new BodyDef();

        bdef.position.set(this.pos.x / PPM, this.pos.y / PPM);
        bdef.type = BodyDef.BodyType.DynamicBody;
        bdef.fixedRotation = true;
        this.body = world.getWorld().createBody(bdef);

        FixtureDef fdef = new FixtureDef();
        fdef.shape = shape;
        fdef.filter.categoryBits = BIT_PLAYER;
        fdef.friction = 0;
        fdef.restitution = 0;
        this.body.createFixture(fdef).setUserData("Player");


        shape.setAsBox((float)(this.width * 0.8) / PPM, 2 / PPM, new Vector2(0, -this.height / PPM), 0);
        fdef.shape = shape;
        fdef.filter.categoryBits = BIT_PLAYER;
        fdef.isSensor = true;
        this.body.createFixture(fdef).setUserData("Foot");

    }

    public void handleInput() {

        movement = Vector2.Zero;
        vel = body.getLinearVelocity();

        if (InputHandler.isDown(KEYS.SPRINT)) {
            maxVel = MAX_VEL_SPRINT;
        } else {
            maxVel = MAX_VEL_WALK;
        }

        if (InputHandler.isDown(KEYS.JUMP) && playerOnGround > 0) {
            body.applyLinearImpulse(new Vector2(0, 2), body.getWorldCenter(), true);
        }

        if (InputHandler.isDown(KEYS.RIGHT) && vel.x < maxVel) {
            movement.x += 1;
        }

        if (InputHandler.isDown(KEYS.LEFT) && vel.x > -maxVel) {
            movement.x += -1;
        }

        if (vel.x != 0) {
            decel = Math.min(Math.abs(vel.x), this.deceleration);
            decel = (vel.x > 0) ? -decel : decel;
        }

        if (playerOnGround > 0) {
            body.applyLinearImpulse(new Vector2(decel, 0), body.getWorldCenter(), true);
        }


        body.applyLinearImpulse(movement.scl((playerOnGround > 0) ? groundAcc : airAcc), body.getWorldCenter(), true);

    }


    public void beginContact(Contact contact) {

        Fixture fa = contact.getFixtureA();
        Fixture fb = contact.getFixtureB();

        if (fa.getUserData() != null && fa.getUserData().equals("Foot")) {
            playerOnGround++;
        }

        if (fb.getUserData() != null && fb.getUserData().equals("Foot")) {
            playerOnGround++;
        }

    }

    public void endContact(Contact contact) {

        Fixture fa = contact.getFixtureA();
        Fixture fb = contact.getFixtureB();

        if (fa.getUserData() != null && fa.getUserData().equals("Foot")) {
            playerOnGround--;
        }

        if (fb.getUserData() != null && fb.getUserData().equals("Foot")) {
            playerOnGround--;
        }
    }

}
