package com.moltenwolfcub.adventurerpg;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.moltenwolfcub.adventurerpg.util.Constants;
import com.moltenwolfcub.adventurerpg.util.Keybinds;

public class Editor {
	private final Rpg game;

	private final Sprite selectionOutline;

    private boolean inEditor = false;


    public Editor(Rpg game) {
        this.game = game;

        this.selectionOutline = game.spriteTextureAtlas.createSprite("editor/selectionOutline");
        this.selectionOutline.setBounds(0, 0, 36, 36);
    }


    public void tick() {
        if (Gdx.input.isKeyJustPressed(Keybinds.TOGGLE_EDITOR.getKeyCode())) {
            inEditor = !inEditor;
        }
    }

    public void paint(Viewport viewport, int camX, int camY) {
        if (inEditor) {
            Vector3 mousePos = viewport.unproject(new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0));
            int mouseX = (int) mousePos.x;
            int mouseY =  (int) mousePos.y;

            int gx = Math.floorDiv(mouseX + camX, Constants.TILE_SIZE);
            int gy = Math.floorDiv(mouseY + camY, Constants.TILE_SIZE);

            selectionOutline.setCenter(gx*Constants.TILE_SIZE-camX+Constants.TILE_SIZE/2, gy*Constants.TILE_SIZE-camY+Constants.TILE_SIZE/2);

            draw();
        }
    }
    private void draw() {
        selectionOutline.draw(game.batch);
    }

    public void dispose() {

    }
}
