package com.moltenwolfcub.adventurerpg;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Tiles {
	public final Rpg game;
    
    private int originX = 0;
    private int originY = 0;

	private TextureRegion currentTexture;

    public Tiles(Rpg game) {
        this.game = game;

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

        this.currentTexture = game.spriteTextureAtlas.createSprite("tiles/bush1");

		originX = 0-camX;
        originY = 0-camY;

        for (int j = 0; j < 10; j++) {
            for (int i = 0; i < 10; i++) {
                game.batch.draw(currentTexture, originX+tileSizeW*i, originY+tileSizeH*j, tileSizeW, tileSizeH);
            }  
        }
    }
}
