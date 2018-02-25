package com.ex.plat.handlers;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputAdapter;

public class InputProcessor extends InputAdapter {

    public enum KEYS {
        UP(Keys.W, 0), DOWN(Keys.S, 1), LEFT(Keys.A, 2), RIGHT(Keys.D, 3), JUMP(Keys.SPACE, 4), SPRINT(Keys.SHIFT_LEFT, 5);

        public int code;
        public int index;

        KEYS(int keyCode, int i) {
            this.code = keyCode;
            this.index = i;
        }
    }

    @Override
    public boolean keyDown(int keycode) {
        for (KEYS key : KEYS.values()) {
            if (keycode == key.code) {
                InputHandler.setKey(key, true);
            }
        }

        return true;
    }

    @Override
    public boolean keyUp(int keycode) {
        for (KEYS key : KEYS.values()) {
            if (keycode == key.code) {
                InputHandler.setKey(key, false);
            }
        }

        return true;
    }
}
