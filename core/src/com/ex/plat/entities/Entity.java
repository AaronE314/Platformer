package com.ex.plat.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.ex.plat.Platformer;
import com.ex.plat.handlers.Animation;
import com.ex.plat.physicsObjects.B2DWorld;

import static com.ex.plat.Constants.BIT_GROUND;
import static com.ex.plat.Constants.PPM;

public abstract class Entity {

    protected B2DWorld world;
    protected Body body;
    protected Vector2 pos;
    protected Animation animation;
    protected float width;
    protected float height;
    protected float bodyH;
    protected float bodyW;
    protected boolean hasImage;

    public Entity(B2DWorld world, Vector2 pos, float w, float h) {
        this.world = world;
        this.pos = pos;
        this.width = w;
        this.height = h;
        this.bodyH = this.height;
        this.bodyW = this.width;
        this.hasImage = false;
        animation = new Animation();
        createEntity();
    }

    public Entity(B2DWorld world, Vector2 pos, String path, String name) {
        this.world = world;
        this.pos = pos;
        this.hasImage = true;
        this.bodyH = this.height/2;
        this.bodyW = this.width/2;
        animation = new Animation();
        this.setTexture(path, name);
        createEntity();
    }

    protected void createEntity() {
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(this.bodyW / PPM, this.bodyH / PPM);
        BodyDef bdef = new BodyDef();
        bdef.position.set(this.pos.x / PPM, this.pos.y / PPM);
        bdef.type = BodyDef.BodyType.StaticBody;
        this.body = world.getWorld().createBody(bdef);

        FixtureDef fdef = new FixtureDef();
        fdef.shape = shape;
        fdef.filter.categoryBits = BIT_GROUND;

        this.body.createFixture(fdef);
    }

    public void setAnimation(TextureRegion[] reg, float delay) {
        animation.setFrames(reg, delay);
        width = reg[0].getRegionWidth();
        height = reg[0].getRegionHeight();
    }

    public void update(float dt) {
        if (this.hasImage) {
            animation.update(dt);
        }
    }

    public void render(SpriteBatch sb) {

        if (this.hasImage) {
            sb.begin();

            sb.draw(animation.getFrame(), body.getPosition().x * PPM - width / 2,
                    body.getPosition().y * PPM - height / 2);

            sb.end();
        }

    }

    public void setPos(float x, float y) {
        body.setTransform(x * PPM, y * PPM, 0);
    }

    public Vector2 getPosition() {
        return body.getPosition();
    }
    public Body getBody() {
        return body;
    }
    public float getWidth() {
        return width;
    }
    public float getHeight() {
        return height;
    }

    protected void setTexture(String path, String name) {
        Platformer.res.loadTexture(path, name);
        Texture tex = Platformer.res.getTexture(name);
        TextureRegion[] sprites = TextureRegion.split(tex, 256, 256)[0];
        setAnimation(sprites, 1/12f);
    }

}
