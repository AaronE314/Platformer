package com.ex.plat.entities;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.ex.plat.handlers.InputHandler;
import com.ex.plat.handlers.InputProcessor;
import com.ex.plat.physicsObjects.B2DWorld;

import static com.ex.plat.Constants.BIT_PLAYER;
import static com.ex.plat.Constants.PPM;
import static com.ex.plat.handlers.InputProcessor.*;

public class Player extends Entity {

    public Player(B2DWorld world, Vector2 pos) {
        super(world, pos, 5, 5);
    }

    @Override
    protected void createEntity() {
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(this.width / PPM, this.height / PPM);
        BodyDef bdef = new BodyDef();

        bdef.position.set(this.pos.x / PPM, this.pos.y / PPM);
        bdef.type = BodyDef.BodyType.DynamicBody;
        this.body = world.getWorld().createBody(bdef);

        FixtureDef fdef = new FixtureDef();
        fdef.shape = shape;
        fdef.filter.categoryBits = BIT_PLAYER;
        this.body.createFixture(fdef).setUserData("Player");


        shape.setAsBox((float)(this.width * 0.8) / PPM, 2 / PPM, new Vector2(0, -this.height / PPM), 0);
        fdef.shape = shape;
        fdef.filter.categoryBits = BIT_PLAYER;
        fdef.isSensor = true;
        this.body.createFixture(fdef).setUserData("Foot");

    }

    public void handleInput() {

        //FIXME: Make jump
        if (InputHandler.isPressed(KEYS.UP)) {
        }
    }


}
