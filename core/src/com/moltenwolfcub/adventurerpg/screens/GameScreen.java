package com.moltenwolfcub.adventurerpg.screens;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.moltenwolfcub.adventurerpg.Player;
import com.moltenwolfcub.adventurerpg.Rpg;
import com.moltenwolfcub.adventurerpg.util.Constants;

public class GameScreen implements Screen {
	private Viewport view;
	private OrthographicCamera camera;

	private final Rpg game;

	private Player player;

	private boolean isLoopRunning = true;

	public GameScreen(Rpg game) {
		this.game = game;

		camera = new OrthographicCamera();
		camera.setToOrtho(false);
		view = new FitViewport(Constants.DESKTOP_WINDOW_WIDTH, Constants.DESKTOP_WINDOW_HEIGHT, camera);

		this.player = new Player(game);
	}

	private void draw() {
		ScreenUtils.clear(1, 1, 1, 1);

		camera.update();
		game.batch.setProjectionMatrix(camera.combined);

		game.batch.begin();

		this.player.paint();
		
		game.batch.end();

	}


	
	@Override
	public void render(float delta) {
		if (isLoopRunning) {
			this.player.tick();
			draw();
		}	
	}
	public void startGameLoop() {
		isLoopRunning = true;
	}
	public void stopGameLoop() {
		isLoopRunning = true;
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
