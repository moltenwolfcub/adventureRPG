package com.moltenwolfcub.adventurerpg;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.moltenwolfcub.adventurerpg.screens.GameScreen;

public class Rpg extends Game {
	public SpriteBatch batch;
    public TextureAtlas spriteTextureAtlas;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
        spriteTextureAtlas = new TextureAtlas("main/textures/spriteTextureMap.txt");
		
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
