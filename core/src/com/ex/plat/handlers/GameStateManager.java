package com.ex.plat.handlers;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.ex.plat.Platformer;
import com.ex.plat.states.GameState;
import com.ex.plat.states.Play;

import java.util.Stack;

public class GameStateManager {

    private Platformer game;

    private Stack<GameState> gameStates;

    public static final int PLAY = 1;

    public GameStateManager(Platformer game) {
        this.game = game;
        gameStates = new Stack<GameState>();
        pushState(PLAY);
    }

    public void update(float dt) {
        gameStates.peek().update(dt);
    }

    public void render(SpriteBatch sb) {
        //gameStates.peek().render(sb);
    }

    public Platformer game() {
        return game;
    }

    public void setState(int state) {
        popState();
        pushState(state);
    }

    public void pushState(int state) {
        gameStates.push(getState(state));
    }

    public void popState() {
        GameState g = gameStates.pop();
        g.dispose();
    }

    private GameState getState(int state) {
//        if (state == PLAY) {
//            return new Play(this);
//        }
        return null;
    }

}
