package com.ex.plat.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.ex.plat.entities.Player;
import com.ex.plat.entities.objects.Platform;
import com.ex.plat.handlers.GameStateManager;
import com.ex.plat.handlers.InputHandler;
import com.ex.plat.handlers.InputProcessor;
import com.ex.plat.handlers.InputProcessor.KEYS;
import com.ex.plat.handlers.PlayerContactListener;
import com.ex.plat.physicsObjects.B2DWorld;

import static com.ex.plat.Constants.PPM;
import static com.ex.plat.Constants.V_HEIGHT;
import static com.ex.plat.Constants.V_WIDTH;

public class Play extends GameState{

    private B2DWorld world;
    private Player player;
    private Platform platform;

    public Play(GameStateManager gsm) {
        super(gsm);

        world = new B2DWorld(new Vector2(0, -9.8f), true);
        player = new Player(world, new Vector2(V_WIDTH/2, V_HEIGHT/2));
        platform = new Platform(world, new Vector2(V_WIDTH/2,100));

        world.getWorld().setContactListener(new PlayerContactListener(player));

    }

    @Override
    public void handleInput() {

        player.handleInput();

    }

    @Override
    public void update(float dt) {

        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        handleInput();

        if (player.getPosition().y > V_HEIGHT /1.5f) {
            cam.position.set(player.getPosition().x * PPM, player.getPosition().y * PPM-V_HEIGHT/6, 0);
        } else {
            cam.position.set(player.getPosition().x * PPM, V_HEIGHT/2, 0);
        }

        if (player.getPosition().y < -10) {
            player.setPos(V_WIDTH/2, V_HEIGHT/2);
        }

        cam.update();
        player.update(dt);
        world.update(dt);
    }

    @Override
    public void render(SpriteBatch sb) {

        sb.setProjectionMatrix(cam.combined);
        world.render(cam.combined.cpy());
        player.render(sb);
    }

    @Override
    public void dispose() {

    }

}
