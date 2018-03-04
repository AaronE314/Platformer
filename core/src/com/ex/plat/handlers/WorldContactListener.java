package com.ex.plat.handlers;


import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.ex.plat.entities.Player;

import static com.ex.plat.Constants.BRICK_BIT;
import static com.ex.plat.Constants.COIN_BIT;
import static com.ex.plat.Constants.FOOT_BIT;
import static com.ex.plat.Constants.GROUND_BIT;
import static com.ex.plat.Constants.OBJECT_BIT;


public class WorldContactListener implements ContactListener {

    @Override
    public void beginContact(Contact contact) {

        Fixture fa = contact.getFixtureA();
        Fixture fb = contact.getFixtureB();

        int cDef = fa.getFilterData().categoryBits | fb.getFilterData().categoryBits;

        switch (cDef) {
            case GROUND_BIT | FOOT_BIT:
            case BRICK_BIT | FOOT_BIT:
            case OBJECT_BIT | FOOT_BIT:
            case COIN_BIT | FOOT_BIT:
                if (fa.getFilterData().categoryBits == FOOT_BIT){
                    ((Player) fa.getUserData()).setPlayerOnGround(true);
                } else {
                    ((Player) fb.getUserData()).setPlayerOnGround(true);
                }
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
            case OBJECT_BIT | FOOT_BIT:
            case COIN_BIT | FOOT_BIT:
                if (fa.getFilterData().categoryBits == FOOT_BIT){
                    ((Player) fa.getUserData()).setPlayerOnGround(false);
                } else {
                    ((Player) fb.getUserData()).setPlayerOnGround(false);
                }
        }
    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }

}
