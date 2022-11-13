package com.moltenwolfcub.adventurerpg.util;

import java.util.HashMap;
import java.util.Map;

public abstract class Constants {

    public static final int DESKTOP_WINDOW_WIDTH = 480;
    public static final int DESKTOP_WINDOW_HEIGHT = 360;

    public static final int BACKGROUND_TILES_WIDTH = 16;
    public static final int BACKGROUND_TILES_HEIGHT = 13;

    public static final int PLAYER_SPEED_MULTIPLIER = 3;
    public static final double PLAYER_DIAGONAL_SPEED_BONUS = 0.75; //smaller the faster

    public static final Map<Integer, String> TILE_MAPPING_ID2STR = new HashMap<Integer, String>() {{
        put(0, "debug.air"); //should not ever be referenced when drawing
        put(1, "debug.missing");
        put(21, "grass");
        put(22, "blueGrass");
        put(23, "stone");
        put(24, "bush1");
        put(25, "sign1");
    }};
    public static final Map<String, Integer> TILE_MAPPING_STR2ID = new HashMap<String, Integer>() {{
        TILE_MAPPING_ID2STR.forEach(
            (k, v) -> put(v, k)
        );
    }};
}
