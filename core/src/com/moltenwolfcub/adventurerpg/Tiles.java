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

        int gx = camX;
        int gy = camY;


		originX = 0-Math.floorMod(gx, 32);
        originY = 0-Math.floorMod(gy, 32);

        for (int j = 0; j < Math.floorDiv(Constants.DESKTOP_WINDOW_HEIGHT, 32)+2; j++) {
            for (int i = 0; i < Math.floorDiv(Constants.DESKTOP_WINDOW_WIDTH, 32)+1; i++) {
                
                int gidx = Math.floorDiv(gx, 32);
                gidx += levelStorage.GMAX*Math.floorDiv(gy, 32);
                gidx += j*levelStorage.GMAX+i;

                int tileId;
                try {
                    tileId = levelStorage.GRID.get(gidx);
                } catch (IndexOutOfBoundsException exception) {
                    tileId = 0;
                }
                if (tileId != 0) {
                    String tileTextureName = Constants.TILE_MAPPING_ID2STR.get(tileId);
                    this.currentTexture = game.spriteTextureAtlas.createSprite("tiles/"+tileTextureName);
    
                    game.batch.draw(currentTexture, originX+tileSizeW*i, originY+tileSizeH*j, tileSizeW, tileSizeH);
                }
            }  
        }
    }
}
