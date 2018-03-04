package com.ex.plat.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.ex.plat.physicsObjects.B2DWorld;

import static com.ex.plat.Constants.BRICK_BIT;
import static com.ex.plat.Constants.COIN_BIT;
import static com.ex.plat.Constants.GROUND_BIT;
import static com.ex.plat.Constants.BIT_PLAYER;
import static com.ex.plat.Constants.FOOT_BIT;
import static com.ex.plat.Constants.OBJECT_BIT;
import static com.ex.plat.Constants.PPM;

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
        CircleShape shape = new CircleShape();
        //shape.setAsBox(this.bodyW / PPM, this.bodyH / PPM);
        shape.setRadius(this.bodyH / PPM);
        BodyDef bdef = new BodyDef();

        bdef.position.set(this.pos.x / PPM, this.pos.y / PPM);
        bdef.type = BodyDef.BodyType.DynamicBody;
        bdef.fixedRotation = true;
        this.body = world.getWorld().createBody(bdef);

        FixtureDef fdef = new FixtureDef();
        fdef.shape = shape;
        fdef.filter.categoryBits = BIT_PLAYER;
        fdef.filter.maskBits = GROUND_BIT | COIN_BIT | BRICK_BIT | OBJECT_BIT;
        fdef.friction = 0;
        fdef.restitution = 0;
        this.body.createFixture(fdef).setUserData(this);


        PolygonShape shape2 = new PolygonShape();
        shape2.setAsBox((float)(this.width * 0.8) / PPM, 1 / PPM, new Vector2(0, -this.height / PPM), 0);
        fdef.shape = shape;
        fdef.filter.categoryBits = FOOT_BIT;
        fdef.filter.maskBits = GROUND_BIT | COIN_BIT | BRICK_BIT | OBJECT_BIT;
        fdef.isSensor = true;
        this.body.createFixture(fdef).setUserData(this);

        shape.dispose();
        shape2.dispose();

    }

    public void handleInput() {

//        movement = Vector2.Zero;
//        vel = body.getLinearVelocity();
//
//        if (InputHandler.isDown(KEYS.SPRINT)) {
//            maxVel = MAX_VEL_SPRINT;
//        } else {
//            maxVel = MAX_VEL_WALK;
//        }
//
//        if (InputHandler.isDown(KEYS.JUMP) && playerOnGround > 0) {
//            body.applyLinearImpulse(new Vector2(0, 2), body.getWorldCenter(), true);
//        }
//
//        if (InputHandler.isDown(KEYS.RIGHT) && vel.x < maxVel) {
//            movement.x += 1;
//        }
//
//        if (InputHandler.isDown(KEYS.LEFT) && vel.x > -maxVel) {
//            movement.x += -1;
//        }
//
//        if (vel.x != 0) {
//            decel = Math.min(Math.abs(vel.x), this.deceleration);
//            decel = (vel.x > 0) ? -decel : decel;
//        }
//
//        if (playerOnGround > 0) {
//            body.applyLinearImpulse(new Vector2(decel, 0), body.getWorldCenter(), true);
//        }
//
//
//        body.applyLinearImpulse(movement.scl((playerOnGround > 0) ? groundAcc : airAcc), body.getWorldCenter(), true);

        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE) && playerOnGround > 0) {
            body.applyLinearImpulse(new Vector2(0,4f), body.getWorldCenter(), true);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.D) && body.getLinearVelocity().x <= 2) {
            body.applyLinearImpulse(new Vector2(0.1f, 0), body.getWorldCenter(), true);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.A) && body.getLinearVelocity().x >= -2) {
            body.applyLinearImpulse(new Vector2(-0.1f, 0), body.getWorldCenter(), true);
        }
        if (body.getLinearVelocity().x != 0 && !Gdx.input.isKeyPressed(Input.Keys.D) && !Gdx.input.isKeyPressed(Input.Keys.A)) {
            float velX = Math.min(Math.abs(body.getLinearVelocity().x), this.deceleration);
            velX = (body.getLinearVelocity().x > 0) ? -velX : velX;
            body.applyLinearImpulse(new Vector2(velX, 0), body.getWorldCenter(), true);
        }

    }

    public void setPlayerOnGround(boolean b) {
        this.playerOnGround += (b) ? 1 : -1;
        System.out.println(playerOnGround);
    }

}
