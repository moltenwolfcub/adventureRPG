package com.moltenwolfcub.adventurerpg;

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
        GMAX = 20;
        
        addMultipleTilesToGrid(GMAX, Constants.TILE_MAPPING_STR2ID.get("bush1"));
        for (int i = 0; i < GMAX-2; i++) {
            GRID.add(Constants.TILE_MAPPING_STR2ID.get("bush1"));
            addMultipleTilesToGrid(GMAX-2, Constants.TILE_MAPPING_STR2ID.get("blueGrass"));
            GRID.add(Constants.TILE_MAPPING_STR2ID.get("bush1"));
        }
        addMultipleTilesToGrid(GMAX, Constants.TILE_MAPPING_STR2ID.get("bush1"));
    }

    private void addMultipleTilesToGrid(int count, int tile) {
        for (int i = 0; i < count; i++) {
            GRID.add(tile);
        }
    }
    
}
