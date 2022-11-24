package com.moltenwolfcub.adventurerpg;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.moltenwolfcub.adventurerpg.screens.GameScreen;

/**
 * A class that impliments {@code Game} that holds the
 * {@code SpriteBatch} and {@code TextureAtlas} for all
 * the screens and other elements. Stores and controls
 * which {@code Screen} is currently active.
 * 
 * @author 		MoltenWolfCub
 * @version     %I%
 * @see			Game
 * @see			TextureAtlas
 * @see			SpriteBatch
 */
public class Rpg extends Game {
	/** The batch used for efficient rendering of multiple objects.*/
	public SpriteBatch batch;
	/** The atlas to load in all the texture assets into the game.*/
    public TextureAtlas spriteTextureAtlas;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
        spriteTextureAtlas = new TextureAtlas("main/atlases/spriteTextureMap.atlas");
		
		this.setScreen(new GameScreen(this));
	}

	@Override
	public void render () {
		super.render();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
        spriteTextureAtlas.dispose();
	}
}
