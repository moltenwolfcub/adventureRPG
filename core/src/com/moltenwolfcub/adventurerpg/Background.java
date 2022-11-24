package com.moltenwolfcub.adventurerpg;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Disposable;
import com.moltenwolfcub.adventurerpg.util.Constants;

/**
 * A class used to manage and draw a repeated tiled
 * background of a single tile
 * 
 * @author 		MoltenWolfCub
 * @version     %I%
 */
public class Background implements Disposable {
    /** The game instance holding the {@code SpriteBatch} and {@code TextureAtlas}*/
	public final Rpg game;
    
    /** Left edge where the background tiles should start being drawn from.*/
    protected int originX = 0;
    /** Left edge where the background tiles should start being drawn from.*/
    protected int originY = 0;

    /** The tile texture that should be repeated.*/
	protected TextureRegion texture;

    /**
     * Constructor for a Background.
     * 
     * @param game      The parent game used to get and draw textures.
     */
    public Background(Rpg game) {
        this.game = game;

		this.texture = game.spriteTextureAtlas.createSprite("tiles/greenGrass1");
    }

    @Override
    public void dispose() {

    }

    /**
     * Draws the background tiles.
	 * <p>
	 * Calculates the tile offset based on the camera offset
	 * and then draws all the tiles on the screen.
     * 
     * @param camX      Camera offset along the X-axis
     * @param camY      Camera offset along the Y-axis
     */
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
