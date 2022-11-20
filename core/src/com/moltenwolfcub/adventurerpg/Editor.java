package com.moltenwolfcub.adventurerpg;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Buttons;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.moltenwolfcub.adventurerpg.util.Constants;
import com.moltenwolfcub.adventurerpg.util.Keybinds;

public class Editor {
	private final Rpg game;
	public final LevelStorage levelStorage;
	private final Viewport viewport;

	private final Sprite selectionOutline;
	private final Sprite paletteSelectionOutline;
	private final Sprite paletteBackground;
	private final Sprite paletteChecker;
	private Sprite drawingTexture;

    private boolean inEditor = false;
    private int drawingTile = 0;
    private int paletteWidth = 0;
	private TextureRegion currentPaletteTile;


    public Editor(Rpg game, LevelStorage lvlStore, Viewport view) {
        this.game = game;
        this.levelStorage = lvlStore;
        this.viewport = view;

        this.selectionOutline = game.spriteTextureAtlas.createSprite("editor/selectionOutline");
        this.selectionOutline.setBounds(0, 0, Constants.TILE_SIZE+4, Constants.TILE_SIZE+4);
        this.selectionOutline.setAlpha(0.7f);
        this.paletteSelectionOutline = new Sprite();
        this.paletteSelectionOutline.set(selectionOutline);
        this.paletteSelectionOutline.setAlpha(1);

        this.paletteBackground = game.spriteTextureAtlas.createSprite("editor/paletteBase");
        this.paletteChecker = game.spriteTextureAtlas.createSprite("editor/paletteCheckeredBg");
        this.paletteChecker.setBounds(0, 0, Constants.TILE_SIZE, Constants.TILE_SIZE);
    }

    public void dispose() {
        
    }


    private int getGridPosFromMouse(int axis, int camX, int camY) {
        Vector3 mousePos = viewport.unproject(new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0));
        int mouseX = (int) mousePos.x;
        int mouseY =  (int) mousePos.y;

        int gx = Math.floorDiv(mouseX + camX, Constants.TILE_SIZE);
        int gy = Math.floorDiv(mouseY + camY, Constants.TILE_SIZE);

        switch (axis) {
            case 0: return gx;
            case 1: return gy;
            default: return 0;
        }
    }

    public void tick(int camX, int camY) {
        if (Gdx.input.isKeyJustPressed(Keybinds.TOGGLE_EDITOR.getKeyCode())) {
            inEditor = !inEditor;
        }
        if (inEditor) {
            tickTilePalette();
            if (!paletteInFocus()) {
                int gx = getGridPosFromMouse(0, camX, camY);
                int gy = getGridPosFromMouse(1, camX, camY);
                int gidx = gx+gy*levelStorage.GMAX;
    
                if (Gdx.input.isButtonPressed(Buttons.LEFT)) {
                    levelStorage.GRID.set(gidx, drawingTile);
                }
                if (Gdx.input.isButtonPressed(Buttons.RIGHT)) {
                    levelStorage.GRID.set(gidx, 0);
                }
                if (Gdx.input.isButtonPressed(Buttons.MIDDLE)) {
                    drawingTile = levelStorage.GRID.get(gidx);
                }
            }
        }
    }

    public void paint(int camX, int camY) {
        if (inEditor) {
            int gx = getGridPosFromMouse(0, camX, camY);
            int gy = getGridPosFromMouse(1, camX, camY);

            if (!paletteInFocus()) {
                selectionOutline.setCenter(gx*Constants.TILE_SIZE-camX+Constants.TILE_SIZE/2, gy*Constants.TILE_SIZE-camY+Constants.TILE_SIZE/2);

                if (drawingTile != 0) {
                    drawingTexture = game.spriteTextureAtlas.createSprite(
                        "tiles/"+Constants.TILE_MAPPING_ID2STR.get(drawingTile)
                    );
                    drawingTexture.setBounds(0, 0, Constants.TILE_SIZE, Constants.TILE_SIZE);
                    drawingTexture.setAlpha(0.5f);
                    drawingTexture.setCenter(gx*Constants.TILE_SIZE-camX+Constants.TILE_SIZE/2, gy*Constants.TILE_SIZE-camY+Constants.TILE_SIZE/2);
                }
            }   

            paintTilePalette();


            draw();
        }
    }
    private void draw() {
        if (!paletteInFocus()) {
            if(drawingTile != 0) {
                drawingTexture.draw(game.batch);
            }
            selectionOutline.draw(game.batch);
        }

        drawTilePalette();
    }

    private void paintTilePalette() {
        paletteWidth = Constants.TILE_PALETTE_VIEWPORT_WIDTH*Constants.TILE_SIZE+2*Constants.TILE_PALETTE_BORDER_SIZE;
        
        paletteBackground.setBounds(
            Constants.DESKTOP_WINDOW_WIDTH-paletteWidth, 
            0, 
            paletteWidth,
            Constants.DESKTOP_WINDOW_HEIGHT
        );
    }
    private void drawTilePalette() {
        paletteBackground.draw(game.batch);

        boolean hasPaletteOutline = false;
        for (int i = 0; i < Constants.TILE_PALETTE_VIEWPORT_HEIGHT; i++) {
            for (int j = 0; j < Constants.TILE_PALETTE_VIEWPORT_WIDTH; j++) {

                int x = Constants.DESKTOP_WINDOW_WIDTH-paletteWidth+Constants.TILE_PALETTE_BORDER_SIZE+j*Constants.TILE_SIZE;
                int y = Constants.DESKTOP_WINDOW_HEIGHT-Constants.TILE_SIZE-Constants.TILE_PALETTE_BORDER_SIZE-i*Constants.TILE_SIZE;
                int gidx = j + i*(Constants.TILE_PALETTE_PER_ROW);

                paletteChecker.setPosition(x, y);
                paletteChecker.draw(game.batch);

                Integer tileId = Constants.PALETTE_MAPPING_IDX2ID.get(gidx);
                if (tileId != null) {
                    String tileTextureName = Constants.TILE_MAPPING_ID2STR.get(tileId);
                    this.currentPaletteTile = game.spriteTextureAtlas.createSprite("tiles/"+tileTextureName);
    
                    if (this.currentPaletteTile != null) {
                        game.batch.draw(currentPaletteTile, x, y, Constants.TILE_SIZE, Constants.TILE_SIZE);
                    }
                    if (tileId == drawingTile) {
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
    private void tickTilePalette() {
        if (Gdx.input.isButtonJustPressed(Buttons.LEFT)) {
            Vector3 mousePos = viewport.unproject(new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0));
            int mouseX = (int) mousePos.x;
            int mouseY =  (int) mousePos.y;
            if (mouseX >= Constants.DESKTOP_WINDOW_WIDTH-paletteWidth+Constants.TILE_PALETTE_BORDER_SIZE &&//left edge
                    mouseX < Constants.DESKTOP_WINDOW_WIDTH-Constants.TILE_PALETTE_BORDER_SIZE &&//right edge
                    mouseY > Constants.TILE_PALETTE_BORDER_SIZE &&//bottom edge
                    mouseY < Constants.DESKTOP_WINDOW_HEIGHT-Constants.TILE_PALETTE_BORDER_SIZE) {
                int clickedX = Math.floorDiv(mouseX -(Constants.DESKTOP_WINDOW_WIDTH-paletteWidth+Constants.TILE_PALETTE_BORDER_SIZE) +/*TODO will be a camX when palette scrolling*/ 0, Constants.TILE_SIZE);
                int clickedY = Math.floorDiv(Constants.DESKTOP_WINDOW_HEIGHT-Constants.TILE_PALETTE_BORDER_SIZE-mouseY + /*TODO will be a camY when palette scrolling*/0, Constants.TILE_SIZE);
                int clickedIdx = clickedX+clickedY*Constants.TILE_PALETTE_PER_ROW;
                Integer clickedId = Constants.PALETTE_MAPPING_IDX2ID.get(clickedIdx);
                drawingTile = clickedId!=null ? clickedId : 0;
            }
        }
    }

    public int getPalletteOffset() {
		return inEditor ? paletteWidth : 0;
    }
    public boolean paletteInFocus() {
        return viewport.unproject(new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0)).x >= Constants.DESKTOP_WINDOW_WIDTH-paletteWidth-Constants.TILE_PALETTE_FOCUS_ERROR;
    }
}
