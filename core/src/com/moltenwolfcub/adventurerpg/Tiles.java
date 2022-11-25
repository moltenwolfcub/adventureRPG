package com.moltenwolfcub.adventurerpg;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Disposable;
import com.moltenwolfcub.adventurerpg.util.Constants;

/**
 * Render engine for the tile level grid.
 * <p>
 * Draws a grid of tiles obtained from the
 * {@code LevelStorage} using the 
 * {@code TextureAtlas} from {@code Rpg}.
 * 
 * @author 		MoltenWolfCub
 * @version     %I%
 * @see			LevelStorage
 * @see			Rpg
 * @see			com.badlogic.gdx.graphics.g2d.TextureAtlas
 */
public class Tiles implements Disposable {
	/** Instance of {@code Rpg} to render and obtain Sprites.*/
	public final Rpg game;
	/** The Level that is being drawn.*/
	public final LevelStorage levelStorage;
    
	/** The tile position on the left of the screen within the entire map.*/
    protected Integer originX = 0;
	/** The tile position on the bottom of the screen within the entire map.*/
    protected Integer originY = 0;

	/** The texture used to draw all the tiles onto the screen.*/
	protected TextureRegion currentTexture;

	/**
	 * Construtor for a tile drawing engine
	 * 
	 * @param game			The instance of {@code}
	 * 						used to render and get
	 * 						sprites.
	 * @param lvlStore		The level to be rendered.
	 */
    public Tiles(Rpg game, LevelStorage lvlStore) {
        this.game = game;
        this.levelStorage = lvlStore;

		this.currentTexture = game.spriteTextureAtlas.createSprite("tiles/bush1");
    }

    @Override
    public void dispose() {

    }

	/**
	 * Calculates the positions of objects
	 * before drawing to screen.
	 * 
	 * @param camX		The X-position of the camera
	 * 					in the entire level
	 * @param camY		The Y-position of the camera
	 * 					in the entire level
	 */
	public void paint(Integer camX, Integer camY) {
        draw(camX, camY);
	}

    /**
     * Draws the game tile grid to the screen. 
     * Get's the level from the {@code LevelStorage} GRID.
     * 
     * @param camX      The X-position on the entire 
     *                  level that the camera is looking at.
     * @param camY      The Y-position on the entire 
     *                  level that the camera is looking at.
     * @see 			LevelStorage
	 * @see				LevelStorage#GRID
     */
    protected void draw(Integer camX, Integer camY) {
        Integer tileSizeW = Constants.TILE_SIZE;
        Integer tileSizeH = Constants.TILE_SIZE;

        Integer gx = camX;
        Integer gy = camY;


		originX = 0-Math.floorMod(gx, Constants.TILE_SIZE);
        originY = 0-Math.floorMod(gy, Constants.TILE_SIZE);

        for (Integer j = 0; j < Math.floorDiv(Constants.WINDOW_HEIGHT, Constants.TILE_SIZE)+2; j++) {
            for (Integer i = 0; i < Math.floorDiv(Constants.WINDOW_WIDTH, Constants.TILE_SIZE)+1; i++) {
                
                Integer gidx = Math.floorDiv(gx, Constants.TILE_SIZE);
                gidx += levelStorage.GMAX*Math.floorDiv(gy, Constants.TILE_SIZE);
                gidx += j*levelStorage.GMAX+i;

                Integer tileId;
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
