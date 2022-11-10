package com.moltenwolfcub.adventurerpg.screens;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.moltenwolfcub.adventurerpg.Rpg;
import com.moltenwolfcub.adventurerpg.util.Constants;

public class GameScreen implements Screen {

	private final Rpg game;
	private Sprite testSprite;

	private Viewport view;
	private OrthographicCamera camera;

	public GameScreen(Rpg game) {
		this.game = game;
		this.testSprite = game.spriteTextureAtlas.createSprite("grassTile");

		camera = new OrthographicCamera();
		camera.setToOrtho(false, Constants.DESKTOP_WINDOW_WIDTH, Constants.DESKTOP_WINDOW_HEIGHT);
		view = new FitViewport(Constants.DESKTOP_WINDOW_WIDTH, Constants.DESKTOP_WINDOW_HEIGHT, camera);

	}

	@Override
	public void render(float delta) {
		ScreenUtils.clear(1, 1, 1, 1);


		testSprite.getTexture().setFilter(TextureFilter.Nearest, TextureFilter.Nearest);
		this.testSprite.setScale(2);

		this.testSprite.setPosition(view.getWorldWidth()-this.testSprite.getWidth(), view.getWorldHeight()-this.testSprite.getHeight());



		camera.update();
		game.batch.setProjectionMatrix(camera.combined);

		game.batch.begin();

		testSprite.draw(game.batch);
		
		game.batch.end();
			
	}

	@Override
	public void dispose() {
		
	}

	@Override
	public void resize(int width, int height) {
		view.update(width, height);
	}


	@Override
	public void show() {
			
	}

	@Override
	public void hide() {
			
	}

	@Override
	public void pause() {
			
	}

	@Override
	public void resume() {
			
	}
		
}
