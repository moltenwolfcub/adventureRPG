package com.moltenwolfcub.adventurerpg;

import java.util.Random;

import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;
import com.moltenwolfcub.adventurerpg.util.Constants;

/**
 * The code for storing and generating a level.
 * Stores the level in {@code GRID} and can create
 * new empty levels.
 * 
 * @author 		MoltenWolfCub
 * @version     %I%
 * @see         #GRID
 */
public class LevelStorage implements Disposable {
    /** The level stored in this instance.*/
    public Array<Integer> GRID = new Array<>();
    /** The number of tiles along the X-axis.*/
    public Integer GMAX;
    /** The number of tiles in an entire grid layer.*/
    public Integer GMUL;

    /**
     * Constructor for a level map. Creates
     * an empty map on creation.
     */
    public LevelStorage() {
        loadLevel();
    }

    /**
     * Loads in a level to the {@code GRID}.
     * <p>
     * Currently creates a new level each time
     * but will eventually load in from JSON.
     */
    public void loadLevel() {
        newLevel();
    }

    /**
     * Creates a new empty level. Makes an empty
     * level with a border of bushes and has flowers
     * and bushes randomly spotted throughout
     */
    protected void newLevel() {
        GRID.clear();
        GMAX = 64;
        GMUL = GMAX*GMAX;
        
        addMultipleTilesToGrid(GMAX, Constants.TILE_MAPPING_STR2ID.get("bush1"));
        for (Integer i = 0; i < GMAX-2; i++) {
            GRID.add(Constants.TILE_MAPPING_STR2ID.get("bush1"));
            addMultipleTilesToGrid(GMAX-2, Constants.TILE_MAPPING_STR2ID.get("debug.air"));
            GRID.add(Constants.TILE_MAPPING_STR2ID.get("bush1"));
        }
        addMultipleTilesToGrid(GMAX, Constants.TILE_MAPPING_STR2ID.get("bush1"));
        for (Integer i = 0; i < (Constants.GRID_LAYERS-1)*GMUL; i++) {
            GRID.add(Constants.TILE_MAPPING_STR2ID.get("debug.air"));
        }
    }

    /**
     * Adds multiple consecutive tiles to.
     * {@code GRID}.
     * <p>
     * (If the tile parsed is blank(0), then
     * it will randomly place bushes and flowers
     * instead of a blank tile.)
     * 
     * @param count     The number of tiles to be
     *                  placed together.
     * @param tile      The ID of tile that is being
     *                  placed.
     * @see             #GRID
     */
    protected void addMultipleTilesToGrid(Integer count, Integer tile) {
        Random random = new Random();
        for (Integer i = 0; i < count; i++) {
            if (tile == 0) {
                GRID.add(
                    switch (random.nextInt(125)) {
                        case 0 -> Constants.TILE_MAPPING_STR2ID.get("bush1");
                        case 1 -> Constants.TILE_MAPPING_STR2ID.get("bush1");
                        case 2 -> Constants.TILE_MAPPING_STR2ID.get("flower1");
                        case 3 -> Constants.TILE_MAPPING_STR2ID.get("flower1");
                        case 4 -> Constants.TILE_MAPPING_STR2ID.get("flower1");
                        default -> tile;
                    }
                );
            } else {
                GRID.add(tile);
            }
        }
    }

    @Override
    public void dispose() {
        
    }
    
}
