package com.moltenwolfcub.adventurerpg.util;

import java.util.HashMap;
import java.util.Map;

public abstract class Constants {

    public static final int WINDOW_WIDTH = 480;
    public static final int WINDOW_HEIGHT = 360;

    public static final int BACKGROUND_TILES_WIDTH = 16;
    public static final int BACKGROUND_TILES_HEIGHT = 13;

    public static final int PLAYER_SPEED_MULTIPLIER = 3;
    public static final double PLAYER_DIAGONAL_SPEED_BONUS = 0.99; //smaller the faster

    public static final int PALETTE_BORDER_SIZE = 4;
    public static final int PALETTE_VIEWPORT_WIDTH = 5;
    public static final int PALETTE_VIEWPORT_HEIGHT = 11;
    public static final int PALETTE_PER_ROW = 5;
    public static final int PALETTE_FOCUS_ERROR = 5;
    public static final int PALETTE_SCROLL_REGION_SIZE = 10;
    public static final int PALETTE_SCROLLS_REQ = 10;
    public static final Map<Integer, Integer> PALETTE_MAPPING_IDX2ID = new HashMap<Integer, Integer>() {{
        put(10, 26);
        put(11, 27);
        put(12, 28);
        put(15, 29);
        put(16, 30);
        put(17, 31);
    }};

    public static final int TILE_SIZE = 32;
    public static final Map<Integer, String> TILE_MAPPING_ID2STR = new HashMap<Integer, String>() {{
        //first 20 for debug tiles
        put(0, "debug.air"); //should not ever be referenced when drawing
        put(1, "debug.missing");

        //next 180 for basic tiles
        put(20, "greenGrass1");
        put(21, "blueGrass1");
        put(22, "stone1");
        put(23, "stones1");
        put(24, "crackedStone1");
        put(25, "orangeGoop1");
        put(26, "bigTestTL");
        put(27, "bigTestTM");
        put(28, "bigTestTR");
        put(29, "bigTestBL");
        put(30, "bigTestBM");
        put(31, "bigTestBR");

        //next 100 for plant-like objects
        put(200, "flower1");
        put(201, "bush1");
        
        //next 100 for other objects
        put(300, "sign1");
        put(301, "chest1");
    }};
    public static final Map<String, Integer> TILE_MAPPING_STR2ID = new HashMap<String, Integer>() {{
        TILE_MAPPING_ID2STR.forEach(
            (k, v) -> put(v, k)
        );
    }};

    static {
        TILE_MAPPING_ID2STR.forEach((k, v) -> {
            if (k >= 20) {
                Integer freeKey = 0;
                if (!PALETTE_MAPPING_IDX2ID.containsValue(k)) {
                    while (PALETTE_MAPPING_IDX2ID.containsKey(freeKey)) { freeKey++; }

                    PALETTE_MAPPING_IDX2ID.put(freeKey, k);
                }
            }
        });
    }
}
