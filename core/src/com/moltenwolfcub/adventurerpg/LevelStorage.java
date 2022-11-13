package com.moltenwolfcub.adventurerpg;

import com.badlogic.gdx.utils.Array;

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
        
        addMultipleTilesToGrid(GMAX, 24);
        for (int i = 0; i < GMAX-2; i++) {
            GRID.add(24);
            addMultipleTilesToGrid(GMAX-2, 0);
            GRID.add(24);
        }
        addMultipleTilesToGrid(GMAX, 24);
        System.out.println(GRID.size);
    }

    private void addMultipleTilesToGrid(int count, int tile) {
        for (int i = 0; i < count; i++) {
            GRID.add(tile);
        }
    }
    
}
