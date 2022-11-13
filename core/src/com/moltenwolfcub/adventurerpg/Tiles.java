package com.moltenwolfcub.adventurerpg;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.moltenwolfcub.adventurerpg.util.Constants;

public class Tiles {
	public final Rpg game;
	public final LevelStorage levelStorage;
    
    private int originX = 0;
    private int originY = 0;

	private TextureRegion currentTexture;

    public Tiles(Rpg game, LevelStorage lvlStore) {
        this.game = game;
        this.levelStorage = lvlStore;

		this.currentTexture = game.spriteTextureAtlas.createSprite("tiles/bush1");
    }

    public void dispose() {

    }

	public void paint(int camX, int camY) {
        draw(camX, camY);
	}

    private void draw(int camX, int camY) {
        int tileSizeW = currentTexture.getRegionWidth()*2;
        int tileSizeH = currentTexture.getRegionHeight()*2;

		originX = 0-camX;
        originY = 0-camY;

        for (int j = 0; j < levelStorage.GMAX; j++) {
            for (int i = 0; i < levelStorage.GMAX; i++) {
                int gidx = j*levelStorage.GMAX+i;
                int tileId = levelStorage.GRID.get(gidx);
                if (tileId != 0) {
                    String tileTextureName = Constants.TILE_MAPPING.get(tileId);
                    this.currentTexture = game.spriteTextureAtlas.createSprite("tiles/"+tileTextureName);
    
                    game.batch.draw(currentTexture, originX+tileSizeW*i, originY+tileSizeH*j, tileSizeW, tileSizeH);
                }
            }  
        }
    }
}
