package com.ex.plat.handlers;


import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.ex.plat.entities.Player;


public class PlayerContactListener implements ContactListener {

    private Player player;

    public PlayerContactListener(Player player) {
        this.player = player;
    }

    @Override
    public void beginContact(Contact contact) {

        player.beginContact(contact);

    }

    @Override
    public void endContact(Contact contact) {

        player.endContact(contact);
    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }

}
