package com.ex.plat.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.ex.plat.Platformer;
import com.ex.plat.entities.Player;
import com.ex.plat.entities.objects.Platform;
import com.ex.plat.handlers.GameStateManager;
import com.ex.plat.handlers.PlayerContactListener;
import com.ex.plat.physicsObjects.B2DWorld;
import com.ex.plat.scenes.HUD;

import static com.ex.plat.Constants.PPM;
import static com.ex.plat.Constants.V_HEIGHT;
import static com.ex.plat.Constants.V_WIDTH;

public class Play extends GameState{

    private B2DWorld world;
    private Player player;
    private Platform platform;
    private HUD hud;

    public Play(Platformer game) {
        super(game);

        world = new B2DWorld(new Vector2(0, -9.8f), true);
        player = new Player(world, new Vector2(V_WIDTH/2, V_HEIGHT/2));
        platform = new Platform(world, new Vector2(V_WIDTH/2,100));

        world.getWorld().setContactListener(new PlayerContactListener(player));

        hud = new HUD(game.getSpriteBatch());

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

//    @Override
//    public void render(SpriteBatch sb) {
//
//
//    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        update(delta);

        game.getSpriteBatch().setProjectionMatrix(cam.combined);
        world.render(cam.combined.cpy());
        player.render(game.getSpriteBatch());

        hud.render(game.getSpriteBatch());

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }

}
