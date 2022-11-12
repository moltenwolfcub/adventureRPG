package com.moltenwolfcub.adventurerpg;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.moltenwolfcub.adventurerpg.util.Constants;

public class Background {
	public final Rpg game;
    
    private int originX = 0;
    private int originY = 0;

	private TextureRegion texture;

    public Background(Rpg game) {
        this.game = game;

		this.texture = game.spriteTextureAtlas.createSprite("grassTile");
    }

    public void dispose() {

    }

	public void paint(int camX, int camY) {
        int tileSizeW = texture.getRegionWidth()*2;
        int tileSizeH = texture.getRegionHeight()*2;

		originX = 0- Math.floorMod(camX, tileSizeW);
        originY = 0- Math.floorMod(camY, tileSizeH);

        for (int j = 0; j < Constants.BACKGROUND_TILES_HEIGHT; j++) {
            for (int i = 0; i < Constants.BACKGROUND_TILES_WIDTH; i++) {
                game.batch.draw(texture, originX+tileSizeW*i, originY+tileSizeH*j, tileSizeW, tileSizeH);
            }  
        }
	}
}
