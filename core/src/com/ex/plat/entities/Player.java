package com.ex.plat.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.utils.Array;
import com.ex.plat.states.Play;

import static com.ex.plat.Constants.BIT_PLAYER;
import static com.ex.plat.Constants.BRICK_BIT;
import static com.ex.plat.Constants.SAW_BIT;
import static com.ex.plat.Constants.FOOT_BIT;
import static com.ex.plat.Constants.GROUND_BIT;
import static com.ex.plat.Constants.LEFT_WALL_BIT;
import static com.ex.plat.Constants.SPIKE_BIT;
import static com.ex.plat.Constants.PPM;
import static com.ex.plat.Constants.RIGHT_WALL_BIT;

/**
 * Player Class
 */
public class Player extends Entity {

    public enum State { FALLING, JUMPING, STANDING, RUNNING, SPRINTING}

    private State currentState;
    private State prevState;

    private int playerOnGround = 0;
    private boolean jumping = false;
    private int playerCanWallJump = 0;

    private final float MAX_VEL_WALK = 1;
    private final float MAX_VEL_SPRINT = 4;

    private float maxVel = MAX_VEL_WALK;
    private float deceleration = 0.15f;

    private Animation<TextureRegion> runAnim;
    private TextureRegion stand;
    private TextureRegion jump;

    private float stateTimer;
    private boolean runningRight;


    public Player(Play play, Vector2 pos) {
        //super(world, pos,"badlogic.jpg", "Player");
        super(play.getWorld(), play.getAtlas().findRegion("little_mario"), pos, 5, 5);

        currentState = State.STANDING;
        prevState = State.STANDING;
        maxVel = MAX_VEL_SPRINT;

        stateTimer = 0;
        runningRight = true;

        Array<TextureRegion> frames = new Array<TextureRegion>();

        for (int i = 1; i < 4; i++) {
            frames.add(new TextureRegion(play.getAtlas().findRegion("little_mario"), i * 16, 0, 16, 16));
        }
        runAnim = new Animation<TextureRegion>(0.1f, frames);

        frames.clear();

        jump = new TextureRegion(play.getAtlas().findRegion("little_mario"), 80, 0, 16, 16);

        stand = new TextureRegion(play.getAtlas().findRegion("little_mario"), 0, 0, 16, 16);

        setBounds(0, 0, 16 /PPM, 16 / PPM);

        setRegion(stand);
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
        fdef.filter.maskBits = GROUND_BIT | SAW_BIT | BRICK_BIT | SPIKE_BIT;
        fdef.friction = 0;
        fdef.restitution = 0;
        this.body.createFixture(fdef).setUserData(this);


        //Foot FixtureDef
        PolygonShape shape2 = new PolygonShape();
        shape2.setAsBox((this.bodyW * 0.8f) / PPM, 1 / PPM, new Vector2(0, -this.bodyH / PPM), 0);
        fdef.shape = shape2;
        fdef.filter.categoryBits = FOOT_BIT;
        fdef.filter.maskBits = GROUND_BIT | SAW_BIT | BRICK_BIT | SPIKE_BIT;
        fdef.isSensor = true;
        this.body.createFixture(fdef).setUserData(this);

        //Right fixtures
        shape2.setAsBox(1 / PPM, (this.bodyH * 0.8f) / PPM, new Vector2(this.bodyW / PPM, 0), 0);
        fdef.shape = shape2;
        fdef.filter.categoryBits = RIGHT_WALL_BIT;
        fdef.filter.maskBits = GROUND_BIT | SAW_BIT | BRICK_BIT | SPIKE_BIT;
        fdef.isSensor = true;
        this.body.createFixture(fdef).setUserData(this);

        //Left fixtures
        shape2.setAsBox(1 / PPM, (this.bodyH * 0.8f) / PPM, new Vector2(-this.bodyW / PPM, 0), 0);
        fdef.shape = shape2;
        fdef.filter.categoryBits = LEFT_WALL_BIT;
        fdef.filter.maskBits = GROUND_BIT | SAW_BIT | BRICK_BIT | SPIKE_BIT;
        fdef.isSensor = true;
        this.body.createFixture(fdef).setUserData(this);

        shape.dispose();
        shape2.dispose();

    }

    @Override
    public void update(float dt) {

        setPosition(body.getPosition().x - getWidth() / 2, body.getPosition().y - getHeight() / 2);
        setRegion(getFrame(dt));
    }

    /**
     * handles user input
     */
    public void handleInput() {

        //Check if sprinting
        //maxVel = (Gdx.input.isKeyPressed(Input.Keys.SHIFT_LEFT)) ? MAX_VEL_SPRINT : MAX_VEL_WALK;
        if (currentState != State.STANDING) {
            this.body.setLinearVelocity(maxVel * (Math.signum(body.getLinearVelocity().x)), body.getLinearVelocity().y);
        }

        //Check if on the ground to jump
        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE) && (playerOnGround > 0 || playerCanWallJump != 0)) {
            if (playerCanWallJump != 0 && playerOnGround == 0) {
                this.wallJump(playerCanWallJump);
            }
            body.applyLinearImpulse(new Vector2(0,4f), body.getWorldCenter(), true);
            jumping = true;
        }

        //Adds Dampening to add variable jump height
        if (!Gdx.input.isKeyPressed(Input.Keys.SPACE) && jumping) {
            body.applyForceToCenter(new Vector2(0,-10f), true);
        }

        if (this.currentState == State.STANDING && Gdx.input.isKeyPressed(Input.Keys.D)) {
            this.body.setLinearVelocity(maxVel, this.body.getLinearVelocity().y);
        }

//        //Move Right
//        if (Gdx.input.isKeyPressed(Input.Keys.D) && body.getLinearVelocity().x <= maxVel) {
//            body.applyLinearImpulse(new Vector2(0.1f, 0), body.getWorldCenter(), true);
//        }
//        //Move Left
//        if (Gdx.input.isKeyPressed(Input.Keys.A) && body.getLinearVelocity().x >= -maxVel) {
//            body.applyLinearImpulse(new Vector2(-0.1f, 0), body.getWorldCenter(), true);
//        }
//
//        //Adds resistance to stop player when not pressing a key or > max Velocity
//        if ((body.getLinearVelocity().x != 0 && !Gdx.input.isKeyPressed(Input.Keys.D) && !Gdx.input.isKeyPressed(Input.Keys.A)) || body.getLinearVelocity().x > maxVel) {
//            float velX = Math.min(Math.abs(body.getLinearVelocity().x), this.deceleration);
//            velX = (body.getLinearVelocity().x > 0) ? -velX : velX;
//            body.applyLinearImpulse(new Vector2(velX, 0), body.getWorldCenter(), true);
//        }

        //Checks if the player is jumping
        if (playerOnGround > 0 && !Gdx.input.isKeyPressed(Input.Keys.SPACE)) {
            jumping = false;
        }


    }

    public TextureRegion getFrame(float dt) {

        currentState = getState();

        TextureRegion region;

        switch (currentState) {

            case JUMPING:
                region = jump;
                break;
            case RUNNING:
                region = runAnim.getKeyFrame(stateTimer, true);
                break;
            case FALLING:
            case STANDING:
            default:
                region = stand;
                break;

        }

        if ((body.getLinearVelocity().x < 0 || !runningRight) && !region.isFlipX()) {
            region.flip(true, false);
            runningRight = false;
        } else if ((body.getLinearVelocity().x > 0 || runningRight) && region.isFlipX()) {
            region.flip(true, false);
            runningRight = true;
        }

        stateTimer = (currentState == prevState) ? stateTimer + dt : 0;

        prevState = currentState;

        return region;
    }

    public State getState() {

        if ((body.getLinearVelocity().y > 0 && currentState == State.JUMPING) || (body.getLinearVelocity().y < 0 && prevState == State.JUMPING)) {
            return State.JUMPING;
        } else if (body.getLinearVelocity().y < 0) {
            return State.FALLING;
        } else if (body.getLinearVelocity().x != 0) {
            return State.RUNNING;
        } else {
            return State.STANDING;
        }
    }


    /**
     * sets if the palyer is on the ground, player is on the ground if playerOnGround > 0
     * @param b true if player is on the ground
     */
    public void setPlayerOnGround(boolean b) {
        this.playerOnGround += (b) ? 1 : -1;
    }

    public void setPlayerCanWallJump(int dir) {
        this.playerCanWallJump += dir;
    }

    public void wallJump(int dir) {

        dir = Integer.signum(dir);

        this.body.setLinearVelocity(dir * maxVel, this.body.getLinearVelocity().y);

    }

    public void die() {
        this.currentState = State.STANDING;
        this.body.setLinearVelocity(0,0);
        this.setPos(startingPos);
    }

}
