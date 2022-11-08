package com.moltenwolfcub.adventurerpg.screens;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.ScreenUtils;
import com.moltenwolfcub.adventurerpg.Rpg;
import com.moltenwolfcub.adventurerpg.util.Constants;

public class GameScreen implements Screen {

	private final Rpg game;

	private OrthographicCamera camera;

	public GameScreen(Rpg game) {
		this.game = game;

		camera = new OrthographicCamera();
		camera.setToOrtho(false, Constants.DESKTOP_WINDOW_WIDTH, Constants.DESKTOP_WINDOW_HEIGHT);

	}

	@Override
	public void render(float delta) {
		ScreenUtils.clear(1, 0, 0, 1);

		camera.update();
		game.batch.setProjectionMatrix(camera.combined);

		game.batch.begin();

		
		game.batch.end();
			
	}

	@Override
	public void dispose() {
		
	}

	@Override
	public void resize(int width, int height) {
			
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
