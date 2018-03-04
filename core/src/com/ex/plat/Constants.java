package com.ex.plat;

public abstract class Constants {

    public static final String TITLE = "Platformer";
//    public static final int V_WIDTH = 640;
//    public static final int V_HEIGHT = 480;
    public static final int V_WIDTH = 400;
    public static final int V_HEIGHT = 208;
    public static final int SCALE = 1;
    public static final float STEP = 1 / 60f;


    //Box2D

    public static final float PPM = 100;

    public static final short NOTHING_BIT = 0;
    public static final short GROUND_BIT = 1;
    public static final short BIT_PLAYER = 2;
    public static final short BRICK_BIT = 4;
    public static final short COIN_BIT = 8;
    public static final short OBJECT_BIT = 16;
    public static final short FOOT_BIT = 32;

    public static final short getBits(int i) {
        switch (i) {
            case 2:
                return GROUND_BIT;
            case 3:
                return OBJECT_BIT;
            case 4:
                return COIN_BIT;
            case 5:
                return BRICK_BIT;
            default:
                return NOTHING_BIT;
        }
    }

}
