package com.moltenwolfcub.adventurerpg;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Buttons;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.moltenwolfcub.adventurerpg.util.Constants;
import com.moltenwolfcub.adventurerpg.util.Keybinds;

public class Editor {
	private final Rpg game;
	public final LevelStorage levelStorage;

	private final Sprite selectionOutline;
	private Sprite drawingTexture;

    private boolean inEditor = false;
    private int drawingTile = 1;


    public Editor(Rpg game, LevelStorage lvlStore) {
        this.game = game;
        this.levelStorage = lvlStore;

        this.selectionOutline = game.spriteTextureAtlas.createSprite("editor/selectionOutline");
        this.selectionOutline.setBounds(0, 0, 36, 36);
        this.selectionOutline.setAlpha(0.7f);
    }

    private int[] getGridPosFromMouse(Viewport viewport, int camX, int camY) {
        Vector3 mousePos = viewport.unproject(new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0));
        int mouseX = (int) mousePos.x;
        int mouseY =  (int) mousePos.y;

        int gx = Math.floorDiv(mouseX + camX, Constants.TILE_SIZE);
        int gy = Math.floorDiv(mouseY + camY, Constants.TILE_SIZE);

        return new int[]{gx, gy};
    }


    public void tick(Viewport viewport, int camX, int camY) {
        if (Gdx.input.isKeyJustPressed(Keybinds.TOGGLE_EDITOR.getKeyCode())) {
            inEditor = !inEditor;
        }
        if (inEditor) {
            int[] mouseTile = getGridPosFromMouse(viewport, camX, camY);
            int gx = mouseTile[0];
            int gy = mouseTile[1];
            int gidx = gx+gy*levelStorage.GMAX;

            if (Gdx.input.isButtonPressed(Buttons.LEFT)) {
                levelStorage.GRID.set(gidx, drawingTile);
            }
            if (Gdx.input.isButtonPressed(Buttons.RIGHT)) {
                levelStorage.GRID.set(gidx, 0);
            }
        }
    }

    public void paint(Viewport viewport, int camX, int camY) {
        if (inEditor) {
            int[] mouseTile = getGridPosFromMouse(viewport, camX, camY);
            int gx = mouseTile[0];
            int gy = mouseTile[1];
            
            drawingTexture = game.spriteTextureAtlas.createSprite(
                "tiles/"+Constants.TILE_MAPPING_ID2STR.get(drawingTile)
            );
            drawingTexture.setBounds(0, 0, 32, 32);
            drawingTexture.setAlpha(0.5f);

            selectionOutline.setCenter(gx*Constants.TILE_SIZE-camX+Constants.TILE_SIZE/2, gy*Constants.TILE_SIZE-camY+Constants.TILE_SIZE/2);
            drawingTexture.setCenter(gx*Constants.TILE_SIZE-camX+Constants.TILE_SIZE/2, gy*Constants.TILE_SIZE-camY+Constants.TILE_SIZE/2);

            draw();
        }
    }
    private void draw() {
        drawingTexture.draw(game.batch);
        selectionOutline.draw(game.batch);
    }

    public void dispose() {

    }
}
