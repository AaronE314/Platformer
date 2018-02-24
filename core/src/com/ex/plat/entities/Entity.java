package com.ex.plat.entities;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.ex.plat.physicsObjects.B2DWorld;

import static com.ex.plat.Constants.BIT_GROUND;
import static com.ex.plat.Constants.PPM;

public class Entity {

    protected B2DWorld world;
    protected Body body;
    protected Vector2 pos;
    protected float width;
    protected float height;

    public Entity(B2DWorld world, Vector2 pos, float w, float h) {
        this.world = world;
        this.pos = pos;
        this.width = w;
        this.height = h;
        createEntity();
    }

    protected void createEntity() {
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(this.width / PPM, this.height / PPM);
        BodyDef bdef = new BodyDef();
        bdef.position.set(this.pos.x / PPM, this.pos.y / PPM);
        bdef.type = BodyDef.BodyType.StaticBody;
        this.body = world.getWorld().createBody(bdef);

        FixtureDef fdef = new FixtureDef();
        fdef.shape = shape;
        fdef.filter.categoryBits = BIT_GROUND;

        this.body.createFixture(fdef);
    }

}
