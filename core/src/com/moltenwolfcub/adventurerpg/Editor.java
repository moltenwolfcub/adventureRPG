package com.moltenwolfcub.adventurerpg;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Buttons;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.moltenwolfcub.adventurerpg.util.CachedSprites;
import com.moltenwolfcub.adventurerpg.util.Constants;
import com.moltenwolfcub.adventurerpg.util.Keybinds;

/**
 * A class to manage the game's editor.
 * <p>
 * Holds all the sprites for the different
 * elements of the editor and palette.
 * Controls and draws the tile palette.
 * Manages tile placing, removal and picking.
 */
public class Editor implements Disposable {
    /** The game instance holding the {@code SpriteBatch} and {@code TextureAtlas}.*/
	protected final Rpg game;
    /** The place where the level is stored and generated.*/
	protected final LevelStorage levelStorage;
    /** The viewport for unprojecting mouse position.*/
	protected final Viewport viewport;

    /** The checkered outline showing the selected tile in the level.*/
	protected final Sprite selectionOutline;
    /** The checkered outline showing the tile that's selected in the palette.*/
	protected final Sprite paletteSelectionOutline;
    /**
     * The black background behind all of the palette.
     * <p>
     * (This is used as the shapeRenderer didn't work with the spriteBatch)
     */
	protected final Sprite paletteBackground;
    /** The checkered background for the tile palette.*/
	protected final Sprite paletteChecker;
    /** A Collision pin*/
	protected final Sprite collisionPin;
    /** The translucent copy of the tile currently selected for drawing. This follows the mouse pointer with the outline in the level.*/
	protected Sprite drawingTexture;

    /** Boolean of whether the editor is open or not.*/
    protected Boolean inEditor = false;
    /** The tile ID that is currently beind drawn with.*/
    protected Integer drawingTile = 0;
    /** The width of the tile palette in pixels.<p>(Without the vieport modification. Will be drawn differently.)*/
    protected Integer paletteWidth = 0;
    /** How many tiles down the palette has been scrolled.*/
    protected Integer paletteCamY = 0;
    /** The number of full rows in the palette.*/
    protected Integer paletteLoadedRows;
	/** The idx in the palette of the currently selected tile.*/
	protected Integer currentTileIdx = 0;
	/** The distance the mouse has moved along the X with tracing.*/
	protected Integer dragX = null;
	/** The distance the mouse has moved along the Y with tracing.*/
	protected Integer dragY = null;
	/** The layer that is being edited.*/
	public Integer currentLayer = 1;
	/** The layer that is being edited.*/
	public Boolean focusLayer = false;

    /** The current mode of the palette */
    public PaletteMode paletteEditingMode = PaletteMode.NONE;
    /** A map to store the collision pin data for tiles */
    public Map<Integer, Integer> collisionPins = new HashMap<Integer, Integer>();

    /** The direction scrolled in the last tick as a signum(-1, 0, 1)*/
    protected Integer scroll = 0;
    /** {@code InputAdapter} used for the palette scrolling.*/
    protected InputAdapter input = new InputAdapter(){
        @Override
        public boolean scrolled(float amountX, float amountY) {
            scroll = (int)amountY;
            return true;
        };
    };


    /**
     * The construtor for a level editor.
     * 
     * @param game          The instance of {@code Rpg}
     *                      that holds sprite rendering
     *                      objects
     * @param lvlStore		The {@code LevelStorage} that
	 * 						contains the level that should
	 * 						be drawn
     * @param view			vieport used for unprojecting
	 * 						mouse pos
     */
    public Editor(Rpg game, LevelStorage lvlStore, Viewport view) {
        this.game = game;
        this.levelStorage = lvlStore;
        this.viewport = view;

        this.selectionOutline = CachedSprites.getSprite(game.spriteTextureAtlas, "editor/selectionOutline");
        this.selectionOutline.setBounds(0, 0, Constants.TILE_SIZE+4, Constants.TILE_SIZE+4);
        this.selectionOutline.setAlpha(0.7f);
        this.paletteSelectionOutline = new Sprite();
        this.paletteSelectionOutline.set(selectionOutline);
        this.paletteSelectionOutline.setAlpha(1);

		Pixmap paletteBgPixmap = new Pixmap(8, 8, Pixmap.Format.RGBA8888);
        paletteBgPixmap.setColor(Constants.PALETTE_BORDER_COLOR);
		paletteBgPixmap.fill();
        this.paletteBackground = new Sprite(new Texture(paletteBgPixmap));
		Pixmap paletteCheckerPixmap = new Pixmap(2, 2, Pixmap.Format.RGBA8888);
        paletteCheckerPixmap.setColor(Constants.PALETTE_CHECKER_COLOR1);
		paletteCheckerPixmap.fill();
        paletteCheckerPixmap.setColor(Constants.PALETTE_CHECKER_COLOR2);
		paletteCheckerPixmap.fillRectangle(paletteCheckerPixmap.getWidth()/2, 0, paletteCheckerPixmap.getWidth()/2, paletteCheckerPixmap.getHeight()/2);
		paletteCheckerPixmap.fillRectangle(0, paletteCheckerPixmap.getHeight()/2, paletteCheckerPixmap.getWidth()/2, paletteCheckerPixmap.getHeight()/2);
        this.paletteChecker = new Sprite(new Texture(paletteCheckerPixmap));
        this.paletteChecker.setBounds(0, 0, Constants.TILE_SIZE, Constants.TILE_SIZE);

		Pixmap colidePinPixmap = new Pixmap(8, 8, Pixmap.Format.RGBA8888);
        colidePinPixmap.setColor(Constants.PALETTE_COLLISION_PIN_COLOR);
        colidePinPixmap.fillCircle(colidePinPixmap.getWidth()/2, colidePinPixmap.getHeight()/2, colidePinPixmap.getWidth()/2-1);
		this.collisionPin = new Sprite(new Texture(colidePinPixmap));
		this.collisionPin.setBounds(0, 0, 5, 5);

        Gdx.input.setInputProcessor(input);

        Integer biggestIndex = 0;
        Iterator<Integer> tileIndicies = Constants.PALETTE_MAPPING_IDX2ID.keySet().iterator();
        while (tileIndicies.hasNext()) {
            biggestIndex = Math.max(biggestIndex, tileIndicies.next());
        }
        paletteLoadedRows = Math.floorDiv(biggestIndex, Constants.PALETTE_PER_ROW);
    }

    @Override
    public void dispose() {
        
    }


	/**
	 * Used for obtaining the tilepos that is currently being 
	 * hovered over by the mouse.
	 * 
	 * @param axis		0-> x coordinate 
	 * 					1-> y coordinate
	 * @param camX		the camera offset along the x axis
	 * @param camY		the camera offset along the y axis
	 * @return			the current tile being hovered on
	 * 					by the mouse point. (will only return
	 * 					one axis based on the axis parameter.)
	 */
    public Integer getGridPosFromMouse(Integer axis, Integer camX, Integer camY) {
        Vector3 mousePos = viewport.unproject(new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0));
        Integer mouseX = (int) mousePos.x;
        Integer mouseY =  (int) mousePos.y;

        Integer gx = Math.floorDiv(mouseX + camX, Constants.TILE_SIZE);
        Integer gy = Math.floorDiv(mouseY + camY, Constants.TILE_SIZE);

        switch (axis) {
            case 0: return gx;
            case 1: return gy;
            default: return 0;
        }
    }

	/**
	 * Ticking method for the editor.
	 * Should be ran once per tick.
	 * Controls tile placing, removal and
	 * picking. Also controls toggling
	 * between editor.
	 * 
	 * @param camX      the X-offset of the
     *                  camera in the entire level
	 * @param camY      the Y-offset of the
     *                  camera in the entire level
	 */
    public void tick(Integer camX, Integer camY) {
        if (Keybinds.TOGGLE_EDITOR.isJustPressed()) {
            inEditor = !inEditor;
            focusLayer = false;
        }
        if (inEditor) {
            tickTilePalette();
            if (!paletteInFocus()) {
                Integer gx = getGridPosFromMouse(0, camX, camY);
                Integer gy = getGridPosFromMouse(1, camX, camY);
                Integer gidx = gx+gy*levelStorage.GMAX+(currentLayer-1)*levelStorage.GMUL;
    
                if (Keybinds.PLACE_TILE.isPressed()) {
                    levelStorage.GRID.set(gidx, drawingTile);
                }
                if (Keybinds.CLEAR_TILE.isPressed()) {
                    levelStorage.GRID.set(gidx, 0);
                }
                if (Keybinds.PICK_TILE.isPressed()) {
                    drawingTile = levelStorage.GRID.get(gidx);
                }
            }
			if (Keybinds.SET_LAYER_1.isJustPressed()) {
				setLayer(1);
			}
			if (Keybinds.SET_LAYER_2.isJustPressed()) {
				setLayer(2);
			}
			if (Keybinds.SET_LAYER_3.isJustPressed()) {
				setLayer(3);
			}

			if (Keybinds.EDIT_COLLIDE.isJustPressed()) {
				paletteEditingMode = paletteEditingMode == PaletteMode.COLLISION ? PaletteMode.NONE : PaletteMode.COLLISION;
			}
        }
    }

	/**
	 * Used for calculating and setting positions
	 * of elements in the editor.
	 * 
	 * @param camX		camera offset in the X axis
	 * @param camY		camera offset in the Y axis
	 */
    public void paint(Integer camX, Integer camY) {
        if (inEditor) {
            Integer gx = getGridPosFromMouse(0, camX, camY);
            Integer gy = getGridPosFromMouse(1, camX, camY);

            if (Keybinds.TILE_TRACING.isPressed()) {
				if (dragX != null) {
					currentTileIdx += gx-dragX+(-Constants.PALETTE_PER_ROW*(gy-dragY));
					Integer clickedId = Constants.PALETTE_MAPPING_IDX2ID.get(currentTileIdx);
					drawingTile = clickedId!=null ? clickedId : 0;
				}
                dragX = gx;
                dragY = gy;
            } else {
				dragX = null;
				dragY = null;
			}

            if (!paletteInFocus()) {
                selectionOutline.setCenter(gx*Constants.TILE_SIZE-camX+Constants.TILE_SIZE/2, gy*Constants.TILE_SIZE-camY+Constants.TILE_SIZE/2);

                if (drawingTile != 0) {
                    drawingTexture = CachedSprites.getSprite(game.spriteTextureAtlas, 
                        "tiles/"+Constants.TILE_MAPPING_ID2STR.get(drawingTile)
                    );
                    drawingTexture.setBounds(0, 0, Constants.TILE_SIZE, Constants.TILE_SIZE);
                    drawingTexture.setAlpha(Constants.EDITOR_DEFOCUSED_ALPHA);
                    drawingTexture.setCenter(gx*Constants.TILE_SIZE-camX+Constants.TILE_SIZE/2, gy*Constants.TILE_SIZE-camY+Constants.TILE_SIZE/2);
                }
            }   

            paintTilePalette();


            draw();
        }
    }
	/**
	 * Actually draws the elements onto the screen
	 * after the paint has calculated their positions.
	 */
    protected void draw() {
        if (!paletteInFocus()) {
            if(drawingTile != 0) {
                drawingTexture.draw(game.batch);
            }
            selectionOutline.draw(game.batch);
        }
        game.font.draw(game.batch, "Layer: "+currentLayer, 5, Constants.WINDOW_HEIGHT-5);

        drawTilePalette();
    }

    /**
     * Calculates positions and textures for
     * static elements in the tile palette.
     */
    protected void paintTilePalette() {
        paletteWidth = Constants.PALETTE_VIEWPORT_WIDTH*Constants.TILE_SIZE+2*Constants.PALETTE_BORDER_SIZE;
        
        paletteBackground.setBounds(
            Constants.WINDOW_WIDTH-paletteWidth, 
            0, 
            paletteWidth,
            Constants.WINDOW_HEIGHT
        );
    }
    /**
     * Draws the elements of the tile
     * palette using the {@code SpriteBatch}.
     * <p>
     * Also calculates the positions of the
     * non-static elements like the individual
     * tiles in the palette.
     */
    protected void drawTilePalette() {
        paletteBackground.draw(game.batch);

        Boolean hasPaletteOutline = false;
        for (Integer i = 0; i < Constants.PALETTE_VIEWPORT_HEIGHT; i++) {
            for (Integer j = 0; j < Constants.PALETTE_VIEWPORT_WIDTH; j++) {

                Integer x = Constants.WINDOW_WIDTH-paletteWidth+Constants.PALETTE_BORDER_SIZE+j*Constants.TILE_SIZE;
                Integer y = Constants.WINDOW_HEIGHT-Constants.TILE_SIZE-Constants.PALETTE_BORDER_SIZE-i*Constants.TILE_SIZE;
                Integer gidx = j+(int)Math.floor(paletteCamY)*Constants.PALETTE_PER_ROW + i*(Constants.PALETTE_PER_ROW);

                paletteChecker.setPosition(x, y);
                paletteChecker.draw(game.batch);

                Integer tileId = Constants.PALETTE_MAPPING_IDX2ID.get(gidx);
                if (tileId != null) {
                    String tileTextureName = Constants.TILE_MAPPING_ID2STR.get(tileId);
                    TextureRegion currentPaletteTile = CachedSprites.getSprite(game.spriteTextureAtlas, "tiles/"+tileTextureName);
    
                    if (currentPaletteTile != null) {
                        game.batch.draw(currentPaletteTile, x, y, Constants.TILE_SIZE, Constants.TILE_SIZE);
						if (paletteEditingMode == PaletteMode.COLLISION) {
							paintCollisionPin(x, y);
						}
                    }
                    if (tileId.equals(drawingTile)) {
                        paletteSelectionOutline.setPosition(x-2, y-2);
                        hasPaletteOutline = true;
                    }
                }
            }
        }
        if (hasPaletteOutline) {
            paletteSelectionOutline.draw(game.batch);
        }
    }
    /**
     * Calculates and interprets mouse
     * input on the palette.
     * Checks for selecting a tile and
     * scrolling with the mouse to see
     * more of the palette.
     */
    protected void tickTilePalette() {
        Vector3 mousePos = viewport.unproject(new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0));
        Integer mouseX = (int)mousePos.x;
        Integer mouseY =  (int)mousePos.y;
        if (mouseX >= Constants.WINDOW_WIDTH-paletteWidth+Constants.PALETTE_BORDER_SIZE &&//left edge
                mouseX < Constants.WINDOW_WIDTH-Constants.PALETTE_BORDER_SIZE &&//right edge
                mouseY > Constants.PALETTE_BORDER_SIZE &&//bottom edge
                mouseY < Constants.WINDOW_HEIGHT-Constants.PALETTE_BORDER_SIZE) {//top edge

            if (Gdx.input.isButtonJustPressed(Buttons.LEFT)) {

                Integer clickedX = Math.floorDiv(mouseX -(Constants.WINDOW_WIDTH-paletteWidth+Constants.PALETTE_BORDER_SIZE), Constants.TILE_SIZE);
                Integer clickedY = Math.floorDiv(Constants.WINDOW_HEIGHT-Constants.PALETTE_BORDER_SIZE-mouseY, Constants.TILE_SIZE) + (int)Math.floor(paletteCamY);
                currentTileIdx = clickedX+clickedY*Constants.PALETTE_PER_ROW;
                Integer clickedId = Constants.PALETTE_MAPPING_IDX2ID.get(currentTileIdx);
                drawingTile = clickedId!=null ? clickedId : 0;
            }

            paletteCamY += scroll;
            scroll = 0;
            paletteCamY = Math.max(Math.min(paletteCamY, paletteLoadedRows), 0);
        }
    }

    /**
     * Calculates how to draw the collision pin for a
     * given tile position
     * 
     * @param x		Left pos of the tile
     * @param y		Bottom pos of the tile
     */
    protected void paintCollisionPin(Integer x, Integer y) {
		int collidePerRow = 3;
		int tileSeperation = Math.floorDiv(Constants.TILE_SIZE, collidePerRow);

		this.collisionPin.setCenter(
			x+Constants.TILE_SIZE/2-tileSeperation,
			y+Constants.TILE_SIZE/2-tileSeperation
		);

		for (int i = 0; i < collidePerRow; i++) {
			for (int j = 0; j < collidePerRow; j++) {
				drawCollisionPin();
				
				this.collisionPin.setX(this.collisionPin.getX()+tileSeperation);
			}
			this.collisionPin.setX(this.collisionPin.getX()-collidePerRow*tileSeperation);
			this.collisionPin.setY(this.collisionPin.getY()+tileSeperation);
		}
    }

    /**
	 * Draws the collision pin
	 * */
    protected void drawCollisionPin() {
		this.collisionPin.draw(game.batch);
    }

	/**
	 * Gets the offset nessessary if the
	 * palette is open.
	 * @return		palette offset if the
	 * 				in the editor.
	 * @see			#paletteWidth
	 */
    public Integer getPaletteOffset() {
		return inEditor ? paletteWidth : 0;
    }
	/**
	 * Used for checking whether the palette is
	 * being hovered over
	 * 
	 * @return		if the palette is currently
	 * 				being hovered.
	 */
    public Boolean paletteInFocus() {
        return viewport.unproject(new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0)).x >= Constants.WINDOW_WIDTH-paletteWidth-Constants.PALETTE_FOCUS_ERROR;
    }

	/**
	 * Used to change the layer that is
	 * currently being edited on.
	 * 
	 * @param layer			the desired layer.
	 */
	public void setLayer(Integer layer) {
        if (currentLayer.equals(layer)) {
            focusLayer = !focusLayer;
        } else {
            currentLayer = layer;
        }
	}

    /**
	 * A class to represent the current mode of the palette
	 * */
    public enum PaletteMode {
        /** Default Palette mode */
        NONE,
        /** The mode for modifying collision pins */
        COLLISION;
    }
}
