package com.moltenwolfcub.adventurerpg.screens;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.moltenwolfcub.adventurerpg.Background;
import com.moltenwolfcub.adventurerpg.Editor;
import com.moltenwolfcub.adventurerpg.LevelStorage;
import com.moltenwolfcub.adventurerpg.Player;
import com.moltenwolfcub.adventurerpg.Rpg;
import com.moltenwolfcub.adventurerpg.Tiles;
import com.moltenwolfcub.adventurerpg.util.Constants;

public class GameScreen implements Screen {
	private Viewport view;
	private OrthographicCamera camera;

	public final Rpg game;

	private Background background;
	private Tiles tiles;
	private Editor editor;
	private LevelStorage levelStorage;
	private Player player;

	private boolean isLoopRunning = true;
	public int camX = 0;
	public int camY = 0;

	public GameScreen(Rpg game) {
		this.game = game;

		camera = new OrthographicCamera();
		camera.setToOrtho(false);
		view = new FitViewport(Constants.DESKTOP_WINDOW_WIDTH, Constants.DESKTOP_WINDOW_HEIGHT, camera);

		this.background = new Background(game);
		this.levelStorage = new LevelStorage();
		this.tiles = new Tiles(game, levelStorage);
		this.editor = new Editor(game);

		this.player = new Player(this.game);
	}

	private void draw() {
		ScreenUtils.clear(1, 1, 1, 1);

		camera.update();
		game.batch.setProjectionMatrix(camera.combined);


		game.batch.begin();

		this.background.paint(camX, camY);
		this.tiles.paint(camX, camY);
		this.editor.paint(view, camX, camY);

		this.player.paint(camX, camY);
		
		game.batch.end();

	}


	
	@Override
	public void render(float delta) {
		if (isLoopRunning) {
			this.player.tick();
			this.editor.tick();
			camX = Math.min(Math.max(player.playerX, 0), levelStorage.GMAX*Constants.TILE_SIZE- Constants.DESKTOP_WINDOW_WIDTH);
			camY = Math.min(Math.max(player.playerY, 0), levelStorage.GMAX*Constants.TILE_SIZE- Constants.DESKTOP_WINDOW_HEIGHT);
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
		background.dispose();
		player.dispose();
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
