package com.ex.plat.handlers;


import com.ex.plat.handlers.InputProcessor.KEYS;

public class InputHandler {

    public static boolean[] keys;
    public static boolean[] pkeys;

    public static final int NUM_KEYS = 6;

    static {
        keys = new boolean[NUM_KEYS];
        pkeys = new boolean[NUM_KEYS];
    }

    public static void update() {
        for (int i = 0; i < NUM_KEYS; i++) {
            pkeys[i] = keys[i];
        }
    }

    public static boolean isDown(KEYS key) {
        return keys[key.index];
    }

    public static boolean isPressed(KEYS key) {
        return keys[key.index] && !pkeys[key.index];
    }

    public static void setKey(KEYS key, boolean b) {
        keys[key.index] = b;
    }



}
