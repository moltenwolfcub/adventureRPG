package com.moltenwolfcub.adventurerpg.util;

import java.util.HashMap;
import java.util.Map;

public abstract class Constants {

    public static final int DESKTOP_WINDOW_WIDTH = 480;
    public static final int DESKTOP_WINDOW_HEIGHT = 360;

    public static final int BACKGROUND_TILES_WIDTH = 16;
    public static final int BACKGROUND_TILES_HEIGHT = 13;

    public static final int PLAYER_SPEED_MULTIPLIER = 3;
    public static final double PLAYER_DIAGONAL_SPEED_BONUS = 0.99; //smaller the faster

    public static final Map<Integer, String> TILE_MAPPING_ID2STR = new HashMap<Integer, String>() {{
        put(0, "debug.air"); //should not ever be referenced when drawing
        put(1, "debug.missing");
        put(21, "greenGrass1");
        put(22, "blueGrass1");
        put(23, "stone1");
        put(24, "stones1");
        put(25, "crackedStone1");
        put(26, "orangeGoop1");
        put(101, "flower1");
        put(102, "bush1");
        put(201, "sign1");
        put(202, "chest1");
    }};
    public static final Map<String, Integer> TILE_MAPPING_STR2ID = new HashMap<String, Integer>() {{
        TILE_MAPPING_ID2STR.forEach(
            (k, v) -> put(v, k)
        );
    }};
}
