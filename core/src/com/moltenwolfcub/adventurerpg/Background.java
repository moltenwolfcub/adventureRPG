package com.moltenwolfcub.adventurerpg;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.utils.Disposable;
import com.moltenwolfcub.adventurerpg.util.Constants;
import com.moltenwolfcub.adventurerpg.util.CachedSprites;

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
	/** The editor that is editing this tilemap.*/
	public final Editor editor;
    
    /** Left edge where the background tiles should start being drawn from.*/
    protected Integer originX = 0;
    /** Left edge where the background tiles should start being drawn from.*/
    protected Integer originY = 0;

    /** The sprite that should be tiled.*/
	protected Sprite texture;

    /**
     * Constructor for a Background.
     * 
     * @param game      The parent game used to get and draw textures.
     * @param editor    The editor that controls the focus of tiles 
	 * 					being drawn.
     */
    public Background(Rpg game, Editor editor) {
        this.game = game;
        this.editor = editor;

		this.texture = CachedSprites.getSprite(game.spriteTextureAtlas, "tiles/greenGrass1");
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
	public void paint(Integer camX, Integer camY) {
        texture.setAlpha(editor.focusLayer? Constants.EDITOR_DEFOCUSED_ALPHA: 1);

        Integer tileSizeW = texture.getRegionWidth()*2;
        Integer tileSizeH = texture.getRegionHeight()*2;

		originX = 0- Math.floorMod(camX, tileSizeW);
        originY = 0- Math.floorMod(camY, tileSizeH);

        for (Integer j = 0; j < Constants.BACKGROUND_TILES_HEIGHT; j++) {
            for (Integer i = 0; i < Constants.BACKGROUND_TILES_WIDTH; i++) {
                texture.setBounds(originX+tileSizeW*i, originY+tileSizeH*j, tileSizeW, tileSizeH);
                texture.draw(game.batch);
            }  
        }
	}
}
