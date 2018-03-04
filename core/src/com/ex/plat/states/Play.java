package com.ex.plat.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.math.Vector2;
import com.ex.plat.Platformer;
import com.ex.plat.entities.Player;
import com.ex.plat.handlers.LevelHandler;
import com.ex.plat.handlers.WorldContactListener;
import com.ex.plat.physicsObjects.B2DWorld;
import com.ex.plat.scenes.HUD;

import static com.ex.plat.Constants.PPM;
import static com.ex.plat.Constants.V_HEIGHT;
import static com.ex.plat.Constants.V_WIDTH;

public class Play extends GameState{

    private B2DWorld world;
    private Player player;
    private HUD hud;
    private LevelHandler levelHandler;

    public Play(Platformer game) {
        super(game);

        world = new B2DWorld(new Vector2(0, -9.8f), true);
        world.getWorld().setContactListener(new WorldContactListener());

        hud = new HUD(game.getSpriteBatch());

        levelHandler = new LevelHandler(world.getWorld());

        levelHandler.setMap("level1");

        player = new Player(world, levelHandler.getPlayerStart());

    }

    @Override
    public void handleInput() {

        player.handleInput();

    }

    @Override
    public void update(float dt) {

        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        handleInput();

        if (player.getBody().getPosition().x * PPM > 200) {
            cam.position.set(player.getPosition().x * PPM, V_HEIGHT / 2, 0);
        } else {
            cam.position.set(gamePort.getWorldWidth()/2, gamePort.getWorldHeight()/2, 0);
        }

        if (player.getPosition().y < -10) {
            player.setPos(levelHandler.getPlayerStart());
        }

        cam.update();
        player.update(dt);
        world.update();
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        update(delta);

        levelHandler.render(cam);
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
        levelHandler.dispose();
        world.dispose();
        hud.dispose();
    }
}
