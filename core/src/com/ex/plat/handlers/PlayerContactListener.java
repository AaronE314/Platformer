package com.ex.plat.handlers;


import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;


//FIXME: Make more specific to player
public class PlayerContactListener implements ContactListener {

    private boolean playerOnGround = false;

    @Override
    public void beginContact(Contact contact) {

        Fixture fa = contact.getFixtureA();
        Fixture fb = contact.getFixtureB();

        if (fa.getUserData() != null && fa.getUserData().equals("Foot")) {
            playerOnGround = true;
        }

        if (fb.getUserData() != null && fb.getUserData().equals("Foot")) {
            playerOnGround = true;
        }

    }

    @Override
    public void endContact(Contact contact) {

        Fixture fa = contact.getFixtureA();
        Fixture fb = contact.getFixtureB();

        if (fa.getUserData() != null && fa.getUserData().equals("Foot")) {
            playerOnGround = false;
        }

        if (fb.getUserData() != null && fb.getUserData().equals("Foot")) {
            playerOnGround = false;
        }
    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }

    public boolean isPlayerOnGround() {
        return playerOnGround;
    }
}
