package com.ex.plat.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.ex.plat.Platformer;
import com.ex.plat.handlers.Animation;
import com.ex.plat.physicsObjects.B2DWorld;

import static com.ex.plat.Constants.GROUND_BIT;
import static com.ex.plat.Constants.PPM;

public abstract class Entity extends Sprite {

    protected B2DWorld world;
    protected Body body;
    protected Vector2 pos;
    protected Vector2 startingPos;
    protected Animation animation;
    //protected float width;
    //protected float height;
    protected float bodyH;
    protected float bodyW;
    protected boolean hasImage;

    public Entity(B2DWorld world, TextureAtlas.AtlasRegion region, Vector2 pos, float w, float h) {
        super(region);
        this.world = world;
        this.pos = pos;
        this.startingPos = pos;
        //this.width = w;
        //this.height = h;
        this.bodyH = w;
        this.bodyW = h;
        //this.hasImage = false;
        createEntity();
    }

//    public void setAnimation(TextureRegion[] reg, float delay) {
//        animation.setFrames(reg, delay);
//        width = reg[0].getRegionWidth();
//        height = reg[0].getRegionHeight();
//    }

    public abstract void update(float dt);
    protected abstract void createEntity();

    public void setPos(Vector2 pos) {
        body.setTransform(pos.x / PPM, pos.y / PPM, 0);
    }

    public Vector2 getPosition() {
        return body.getPosition();
    }
    public Body getBody() {
        return body;
    }
//    public float getWidth() {
//        return width;
//    }
//    public float getHeight() {
//        return height;
//    }

//    protected void setTexture(String path, String name) {
//        Platformer.res.loadTexture(path, name);
//        Texture tex = Platformer.res.getTexture(name);
//        TextureRegion[] sprites = TextureRegion.split(tex, 256, 256)[0];
//        setAnimation(sprites, 1/12f);
//    }

}
