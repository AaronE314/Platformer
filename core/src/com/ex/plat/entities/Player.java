package com.ex.plat.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.ex.plat.physicsObjects.B2DWorld;

import static com.ex.plat.Constants.BIT_PLAYER;
import static com.ex.plat.Constants.BRICK_BIT;
import static com.ex.plat.Constants.COIN_BIT;
import static com.ex.plat.Constants.FOOT_BIT;
import static com.ex.plat.Constants.GROUND_BIT;
import static com.ex.plat.Constants.OBJECT_BIT;
import static com.ex.plat.Constants.PPM;

/**
 * Player Class
 */
public class Player extends Entity {

    private int playerOnGround = 0;
    private boolean jumping = false;

    private final float MAX_VEL_WALK = 1;
    private final float MAX_VEL_SPRINT = 2;

    private float maxVel = MAX_VEL_WALK;
    private float deceleration = 0.15f;


    public Player(B2DWorld world, Vector2 pos) {
        //super(world, pos,"badlogic.jpg", "Player");
        super(world, pos, 5, 5);
    }

    /**
     * Creates the box2D body for the player
     */
    @Override
    protected void createEntity() {
        //CircleShape shape = new CircleShape();
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(this.bodyW / PPM, this.bodyH / PPM);
        //shape.setRadius(this.bodyH / PPM);
        BodyDef bdef = new BodyDef();

        //Player BodyDef
        bdef.position.set(this.pos.x / PPM, this.pos.y / PPM);
        bdef.type = BodyDef.BodyType.DynamicBody;
        bdef.fixedRotation = true;
        this.body = world.getWorld().createBody(bdef);

        //Player Fixture Def
        FixtureDef fdef = new FixtureDef();
        fdef.shape = shape;
        fdef.filter.categoryBits = BIT_PLAYER;
        fdef.filter.maskBits = GROUND_BIT | COIN_BIT | BRICK_BIT | OBJECT_BIT;
        fdef.friction = 0;
        fdef.restitution = 0;
        this.body.createFixture(fdef).setUserData(this);


        //Foot FixtureDef
        PolygonShape shape2 = new PolygonShape();
        shape2.setAsBox((float)(this.width * 0.8) / PPM, 1 / PPM, new Vector2(0, -this.height / PPM), 0);
        fdef.shape = shape2;
        fdef.filter.categoryBits = FOOT_BIT;
        fdef.filter.maskBits = GROUND_BIT | COIN_BIT | BRICK_BIT | OBJECT_BIT;
        fdef.isSensor = true;
        this.body.createFixture(fdef).setUserData(this);

        shape.dispose();
        shape2.dispose();

    }

    /**
     * handles user input
     */
    public void handleInput() {

        //Check if sprinting
        maxVel = (Gdx.input.isKeyPressed(Input.Keys.SHIFT_LEFT)) ? MAX_VEL_SPRINT : MAX_VEL_WALK;

        //Check if on the ground to jump
        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE) && playerOnGround > 0) {
            body.applyLinearImpulse(new Vector2(0,4f), body.getWorldCenter(), true);
            jumping = true;
        }

        //Adds Dampening to add variable jump height
        if (!Gdx.input.isKeyPressed(Input.Keys.SPACE) && jumping) {
            body.applyForceToCenter(new Vector2(0,-10f), true);
        }

        //Move Right
        if (Gdx.input.isKeyPressed(Input.Keys.D) && body.getLinearVelocity().x <= maxVel) {
            body.applyLinearImpulse(new Vector2(0.1f, 0), body.getWorldCenter(), true);
        }
        //Move Left
        if (Gdx.input.isKeyPressed(Input.Keys.A) && body.getLinearVelocity().x >= -maxVel) {
            body.applyLinearImpulse(new Vector2(-0.1f, 0), body.getWorldCenter(), true);
        }

        //Adds resistance to stop player when not pressing a key or > max Velocity
        if ((body.getLinearVelocity().x != 0 && !Gdx.input.isKeyPressed(Input.Keys.D) && !Gdx.input.isKeyPressed(Input.Keys.A)) || body.getLinearVelocity().x > maxVel) {
            float velX = Math.min(Math.abs(body.getLinearVelocity().x), this.deceleration);
            velX = (body.getLinearVelocity().x > 0) ? -velX : velX;
            body.applyLinearImpulse(new Vector2(velX, 0), body.getWorldCenter(), true);
        }

        //Checks if the player is jumping
        if (playerOnGround > 0 && !Gdx.input.isKeyPressed(Input.Keys.SPACE)) {
            jumping = false;
        }


    }

    /**
     * sets if the palyer is on the ground, player is on the ground if playerOnGround > 0
     * @param b true if player is on the ground
     */
    public void setPlayerOnGround(boolean b) {
        this.playerOnGround += (b) ? 1 : -1;
    }

}
