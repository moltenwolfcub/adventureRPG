package com.moltenwolfcub.adventurerpg;

import com.badlogic.gdx.graphics.g2d.Sprite;
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
	/** The editor that is editing this tilemap.*/
	public final Editor editor;
    
	/** The tile position on the left of the screen within the entire map.*/
    protected Integer originX = 0;
	/** The tile position on the bottom of the screen within the entire map.*/
    protected Integer originY = 0;

	/** The sprite used to draw all the tiles onto the screen.*/
	protected Sprite currentTexture;

	/**
	 * Construtor for a tile drawing engine
	 * 
	 * @param game			The instance of {@code}
	 * 						used to render and get
	 * 						sprites.
	 * @param lvlStore		The level to be rendered.
     * @param editor        The editor that controls the
     *                      focus of tiles being drawn.
	 */
    public Tiles(Rpg game, LevelStorage lvlStore, Editor editor) {
        this.game = game;
        this.levelStorage = lvlStore;
        this.editor = editor;

		this.currentTexture = game.spriteTextureAtlas.createSprite("tiles/bush1");
    }

    @Override
    public void dispose() {

    }

	/**
	 * Calculates the positions of objects behind
     * the player before drawing to screen.
	 * 
	 * @param camX		The X-position of the camera
	 * 					in the entire level
	 * @param camY		The Y-position of the camera
	 * 					in the entire level
	 */
	public void paintBg(Integer camX, Integer camY) {
        for (Integer i = 1; i <= Constants.GRID_LAYERS_BG; ++i) {
            drawLayer(i, camX, camY);
        }
	}
	/**
	 * Calculates the positions of objects infront
     * of the player before drawing to screen.
	 * 
	 * @param camX		The X-position of the camera
	 * 					in the entire level
	 * @param camY		The Y-position of the camera
	 * 					in the entire level
	 */
    public void paintFg(Integer camX, Integer camY) {
        Integer fgLayerCount = Constants.GRID_LAYERS-Constants.GRID_LAYERS_BG;
        for (Integer i = 1; i <= fgLayerCount; ++i) {
            drawLayer(i+Constants.GRID_LAYERS_BG, camX, camY);
        }
    }

    /**
     * Draws the a single 3d layer of tiles to the screen.
     * Get's the level from the {@code LevelStorage} GRID.
     * 
     * @param camX      The X-position on the entire 
     *                  level that the camera is looking at.
     * @param camY      The Y-position on the entire 
     *                  level that the camera is looking at.
     * @param layer     The layer id that is being drawn.
     * @see 			LevelStorage
	 * @see				LevelStorage#GRID
     */
    protected void drawLayer(Integer layer, Integer camX, Integer camY) {
        Boolean shouldFade = editor.focusLayer & editor.currentLayer!=layer;

        Integer tileSizeW = Constants.TILE_SIZE;
        Integer tileSizeH = Constants.TILE_SIZE;

        Integer gx = camX;
        Integer gy = camY;


		originX = 0-Math.floorMod(gx, Constants.TILE_SIZE);
        originY = 0-Math.floorMod(gy, Constants.TILE_SIZE);

        for (Integer j = 0; j < Math.floorDiv(Constants.WINDOW_HEIGHT, Constants.TILE_SIZE)+2; j++) {
            for (Integer i = 0; i < Math.floorDiv(Constants.WINDOW_WIDTH, Constants.TILE_SIZE)+1; i++) {
                
                Integer gidx = Math.floorDiv(gx, Constants.TILE_SIZE);              //camX offset
                gidx += levelStorage.GMAX*Math.floorDiv(gy, Constants.TILE_SIZE);   //camY offset
                gidx += j*levelStorage.GMAX+i;                                      //index offset
                gidx += (layer-1)*levelStorage.GMUL;                                //layer offset

                Integer tileId;
                try {
                    tileId = levelStorage.GRID.get(gidx);
                } catch (IndexOutOfBoundsException exception) {
                    tileId = 0;
                }
                if (tileId != 0) {
                    String tileTextureName = Constants.TILE_MAPPING_ID2STR.get(tileId);

                    this.currentTexture = game.spriteTextureAtlas.createSprite("tiles/"+tileTextureName);
                    this.currentTexture.setAlpha(shouldFade?0.5f:1);
                    this.currentTexture.setBounds(originX+tileSizeW*i, originY+tileSizeH*j, tileSizeW, tileSizeH);

                    this.currentTexture.draw(game.batch);
                }
            }  
        }
    }
}
