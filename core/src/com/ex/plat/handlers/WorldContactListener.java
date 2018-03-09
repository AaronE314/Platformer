package com.ex.plat.handlers;


import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.ex.plat.entities.Player;

import static com.ex.plat.Constants.BIT_PLAYER;
import static com.ex.plat.Constants.BRICK_BIT;
import static com.ex.plat.Constants.SAW_BIT;
import static com.ex.plat.Constants.FOOT_BIT;
import static com.ex.plat.Constants.GROUND_BIT;
import static com.ex.plat.Constants.LEFT_WALL_BIT;
import static com.ex.plat.Constants.SPIKE_BIT;
import static com.ex.plat.Constants.RIGHT_WALL_BIT;


public class WorldContactListener implements ContactListener {

    @Override
    public void beginContact(Contact contact) {

        Fixture fa = contact.getFixtureA();
        Fixture fb = contact.getFixtureB();

        int cDef = fa.getFilterData().categoryBits | fb.getFilterData().categoryBits;

        switch (cDef) {
            case GROUND_BIT | FOOT_BIT:
            case BRICK_BIT | FOOT_BIT:
            case SPIKE_BIT | FOOT_BIT:
            case SAW_BIT | FOOT_BIT:
                if (fa.getFilterData().categoryBits == FOOT_BIT){
                    ((Player) fa.getUserData()).setPlayerOnGround(true);
                } else {
                    ((Player) fb.getUserData()).setPlayerOnGround(true);
                }
                break;
            case LEFT_WALL_BIT | GROUND_BIT:
            case LEFT_WALL_BIT | BRICK_BIT:
            case LEFT_WALL_BIT | SPIKE_BIT:
            case LEFT_WALL_BIT | SAW_BIT:
                if (fa.getFilterData().categoryBits == LEFT_WALL_BIT) {
                    ((Player) fa.getUserData()).setPlayerCanWallJump(1);
                } else {
                    ((Player) fb.getUserData()).setPlayerCanWallJump(1);
                }
                break;
            case RIGHT_WALL_BIT | GROUND_BIT:
            case RIGHT_WALL_BIT | BRICK_BIT:
            case RIGHT_WALL_BIT | SPIKE_BIT:
            case RIGHT_WALL_BIT | SAW_BIT:
                if (fa.getFilterData().categoryBits == RIGHT_WALL_BIT) {
                    ((Player) fa.getUserData()).setPlayerCanWallJump(-1);
                } else {
                    ((Player) fb.getUserData()).setPlayerCanWallJump(-1);
                }
                break;
            case BIT_PLAYER | SAW_BIT:
            case BIT_PLAYER | SPIKE_BIT:
                if (fa.getFilterData().categoryBits == BIT_PLAYER) {
                    ((Player) fa.getUserData()).die();
                } else {
                    ((Player) fb.getUserData()).die();
                }
                break;
        }

    }

    @Override
    public void endContact(Contact contact) {

        Fixture fa = contact.getFixtureA();
        Fixture fb = contact.getFixtureB();

        int cDef = fa.getFilterData().categoryBits | fb.getFilterData().categoryBits;

        switch (cDef) {
            case GROUND_BIT | FOOT_BIT:
            case BRICK_BIT | FOOT_BIT:
            case SPIKE_BIT | FOOT_BIT:
            case SAW_BIT | FOOT_BIT:
                if (fa.getFilterData().categoryBits == FOOT_BIT){
                    ((Player) fa.getUserData()).setPlayerOnGround(false);
                } else {
                    ((Player) fb.getUserData()).setPlayerOnGround(false);
                }
                break;
            case LEFT_WALL_BIT | GROUND_BIT:
            case LEFT_WALL_BIT | BRICK_BIT:
            case LEFT_WALL_BIT | SPIKE_BIT:
            case LEFT_WALL_BIT | SAW_BIT:
                if (fa.getFilterData().categoryBits == LEFT_WALL_BIT) {
                    ((Player) fa.getUserData()).setPlayerCanWallJump(-1);
                } else {
                    ((Player) fb.getUserData()).setPlayerCanWallJump(-1);
                }
                break;
            case RIGHT_WALL_BIT | GROUND_BIT:
            case RIGHT_WALL_BIT | BRICK_BIT:
            case RIGHT_WALL_BIT | SPIKE_BIT:
            case RIGHT_WALL_BIT | SAW_BIT:
                if (fa.getFilterData().categoryBits == RIGHT_WALL_BIT) {
                    ((Player) fa.getUserData()).setPlayerCanWallJump(1);
                } else {
                    ((Player) fb.getUserData()).setPlayerCanWallJump(1);
                }
                break;
        }
    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }

}
