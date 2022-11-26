package com.moltenwolfcub.adventurerpg;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import com.moltenwolfcub.adventurerpg.screens.GameScreen;

/**
 * A class that impliments {@code Game} that holds the
 * {@code SpriteBatch}, {@code TextureAtlas} and 
 * {@code BitmapFont} for all the screens and other 
 * elements. Stores and controls which {@code Screen} 
 * is currently active.
 * 
 * @author 		MoltenWolfCub
 * @version     %I%
 * @see			Game
 * @see			TextureAtlas
 * @see			SpriteBatch
 * @see			BitmapFont
 */
public class Rpg extends Game {
	/** The batch used for efficient rendering of multiple objects.*/
	public SpriteBatch batch;
	/** The atlas to load in all the texture assets into the game.*/
    public TextureAtlas spriteTextureAtlas;
	/** The font used for drawing text*/
	public BitmapFont font;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
        spriteTextureAtlas = new TextureAtlas("main/atlases/spriteTextureMap.atlas");

		FreeTypeFontGenerator fontGen = new FreeTypeFontGenerator(Gdx.files.internal("main/fonts/8bitFont.ttf"));
		FreeTypeFontParameter fontSettings = new FreeTypeFontParameter();
		fontSettings.size = 16;
		font = fontGen.generateFont(fontSettings);
		fontGen.dispose();
		
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
		font.dispose();
	}
}
