package com.moltenwolfcub.adventurerpg.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class Constants {

    public static final int DESKTOP_WINDOW_WIDTH = 480;
    public static final int DESKTOP_WINDOW_HEIGHT = 360;

    public static final int BACKGROUND_TILES_WIDTH = 16;
    public static final int BACKGROUND_TILES_HEIGHT = 13;

    public static final int PLAYER_SPEED_MULTIPLIER = 3;
    public static final double PLAYER_DIAGONAL_SPEED_BONUS = 0.99; //smaller the faster

    public static final int TILE_PALETTE_BORDER_SIZE = 4;
    public static final int TILE_PALETTE_VIEWPORT_WIDTH = 5;
    public static final int TILE_PALETTE_VIEWPORT_HEIGHT = 11;
    public static final int TILE_PALETTE_PER_ROW = 20;

    public static final int TILE_SIZE = 32;
    public static final Map<Integer, String> TILE_MAPPING_ID2STR = new HashMap<Integer, String>() {{
        //first 20 for debug tiles
        put(0, "debug.air"); //should not ever be referenced when drawing
        put(1, "debug.missing");

        //next 80 for basic tiles
        put(20, "greenGrass1");
        put(21, "blueGrass1");
        put(22, "stone1");
        put(23, "stones1");
        put(24, "crackedStone1");
        put(25, "orangeGoop1");
        put(40, "bigTestTL");
        put(41, "bigTestTM");
        put(42, "bigTestTR");
        put(60, "bigTestBL");
        put(61, "bigTestBM");
        put(62, "bigTestBR");

        //next 100 for plant-like objects
        put(100, "flower1");
        put(101, "bush1");
        
        //next 100 for other objects
        put(200, "sign1");
        put(201, "chest1");
    }};
    public static final Map<String, Integer> TILE_MAPPING_STR2ID = new HashMap<String, Integer>() {{
        TILE_MAPPING_ID2STR.forEach(
            (k, v) -> put(v, k)
        );
    }};
    public static final List<Integer> TILE_IDS = new ArrayList<Integer>() {{
        TILE_MAPPING_ID2STR.keySet().forEach(
            (i) -> { if (i >= 20) {add(i);}}
        );
        // for(int i = 0; i< 201; i++) {
        //     add(i);
        // }TODO big multi-tile context support
    }};
    static {
        Collections.sort(TILE_IDS);
    }
}
