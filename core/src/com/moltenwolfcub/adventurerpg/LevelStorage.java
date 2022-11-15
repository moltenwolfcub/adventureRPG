package com.moltenwolfcub.adventurerpg;

import java.util.Random;

import com.badlogic.gdx.utils.Array;
import com.moltenwolfcub.adventurerpg.util.Constants;

public class LevelStorage {
    public Array<Integer> GRID = new Array<>();
    public int GMAX;

    public LevelStorage() {
        loadLevel();
    }

    public void loadLevel() {
        newLevel();
    }

    private void newLevel() {
        GRID.clear();
        GMAX = 32;
        
        addMultipleTilesToGrid(GMAX, Constants.TILE_MAPPING_STR2ID.get("bush1"));
        for (int i = 0; i < GMAX-2; i++) {
            GRID.add(Constants.TILE_MAPPING_STR2ID.get("bush1"));
            addMultipleTilesToGrid(GMAX-2, Constants.TILE_MAPPING_STR2ID.get("debug.air"));
            GRID.add(Constants.TILE_MAPPING_STR2ID.get("bush1"));
        }
        addMultipleTilesToGrid(GMAX, Constants.TILE_MAPPING_STR2ID.get("bush1"));
    }

    private void addMultipleTilesToGrid(int count, int tile) {
        Random random = new Random();
        for (int i = 0; i < count; i++) {
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
    
}
