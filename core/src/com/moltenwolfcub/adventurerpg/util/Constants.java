package com.moltenwolfcub.adventurerpg.util;

import java.util.HashMap;
import java.util.Map;

/**
 * A class to store the Constant values 
 * that never change.
 * 
 * @author      MoltenWolfCub
 * @version     %I%
 */
public abstract class Constants {

    /** The width of the desktop window in pixels.<p>(Will be rendered differently by {@code Viewport}). */
    public static final Integer WINDOW_WIDTH = 480;
    /** The height of the desktop window in pixels.<p>(Will be rendered differently by {@code Viewport}). */
    public static final Integer WINDOW_HEIGHT = 360;

    /** The number of times that the background tile is repeated along the X-axis.*/
    public static final Integer BACKGROUND_TILES_WIDTH = 16;
    /** The number of times that the background tile is repeated along the Y-axis.*/
    public static final Integer BACKGROUND_TILES_HEIGHT = 13;

    /** The number of 3d layers in the grid.*/
    public static final Integer GRID_LAYERS = 3;
    /** The number of the 3d layers that should be drawn behind the player*/
    public static final Integer GRID_LAYERS_BG = 2;

    /** The speed multiplier applied to the player's movement.*/
    public static final Integer PLAYER_SPEED_MULTIPLIER = 3;
    /** The divider on the player's speed when moving at a diagonal. <p>So the smaller the value the faster the movement. E.G. 1/0.8(This value) = 1.25 total speed.*/
    public static final Double PLAYER_DIAGONAL_SPEED_BONUS = 0.99;

    /** The alpha value of tiles that aren't on the focused layer when a layer is focused*/
    public static final Float EDITOR_DEFOCUSED_ALPHA = 0.25f;
    /** The width of the Black border around the tile palette in the editor in pixels.<p>(Will be rendered differently by {@code Viewport}).*/
    public static final Integer PALETTE_BORDER_SIZE = 4;
    /** Number of Tiles visible on the palette along the X-axis.*/
    public static final Integer PALETTE_VIEWPORT_WIDTH = 5;
    /** Number of Tiles visible on the palette along the Y-axis.*/
    public static final Integer PALETTE_VIEWPORT_HEIGHT = 11;
    /**
     * The number of tiles in a row in the palette. Currently no support for sideways 
     * scolling so if it's bigger than {@code PALETTE_VIEWPORT_WIDTH} then some tiles
     * will become inaccessible.
     * 
     * @see #PALETTE_VIEWPORT_WIDTH
     */ 
    public static final Integer PALETTE_PER_ROW = 5;
    /** 
     * The number of pixels to the side of the tile palette that don't effect the level.
     * <p>
     * A little room for error so if you misclick close to the palette you don't change
     * the level.
     */
    public static final Integer PALETTE_FOCUS_ERROR = 5;
    /**
     * A mapping of the index in the palette to a tile ID.
     * <p>
     * The Indicies go from the top-left of the palette and
     * increase to the left until you hit the {@code PALETTE_PER_ROW}
     * and then it goes onto the next row.
     * <p>
     * Not every tile has to go into this map for it to be in
     * the palette. Any tile that's not in this map will get
     * auto-added into the first free space unless it's a debug
     * tile (id of less than 20).
     */
    public static final Map<Integer, Integer> PALETTE_MAPPING_IDX2ID = new HashMap<Integer, Integer>() {{
        put(10, 26);
        put(11, 27);
        put(12, 28);
        put(15, 29);
        put(16, 30);
        put(17, 31);

        put(0, 304);
        put(5, 303);
        put(6, 302);

        put(2, 305);
        put(3, 306);
        put(7, 307);
        put(8, 308);
    }};

    /** The width of a single tile in pixels. */
    public static final Integer TILE_SIZE = 32;
    /**
     * ID mapping for each tile to it's name in the {@code TextureAtlas}.
     * 
     * @see     com.badlogic.gdx.graphics.g2d.TextureAtlas
     */
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
        put(26, "bricksBlue1");
        put(27, "bricksRed1");
        put(28, "crackedLavaStone1");
        put(29, "portal1");
        put(30, "purpleStars1");
        put(31, "sciFiFloor1");
        put(32, "stoneFloor1");
        put(33, "woodPlanks1");
        put(34, "woodStripped1");

        //next 100 for natural-like objects
        put(200, "flower1");
        put(201, "bush1");
        put(202, "rock1");
        put(203, "smallTree1");
        
        //next 100 for other objects
        put(300, "sign1");
        put(301, "chest1");
        put(302, "bookshelf1");
        put(303, "bookshelf2B");
        put(304, "bookshelf2T");
        put(305, "gigaComputer1TL");
        put(306, "gigaComputer1TR");
        put(307, "gigaComputer1BL");
        put(308, "gigaComputer1BR");
        put(309, "blueCrystal1");
    }};
    /**
     * Reverse mapping of {@code TILE_MAPPING_ID2STR}<p> Name in the {@code TextureAtlas}: ID
     * 
     * @see     #TILE_MAPPING_ID2STR
     * @see     com.badlogic.gdx.graphics.g2d.TextureAtlas
     */
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
