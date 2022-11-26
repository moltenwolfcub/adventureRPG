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

/**
 * A class implementing {@code Screen} that manages the
 * main game loop, camera and the instances of the other
 * game elements.
 * <p>
 * Contains the main tick loop which can be toggled on and off.
 * All instances of game elements will get ticked and drawn in this loop too.
 * <p>
 * Also holds and manages the {@code OrthographicCamera} and {@code Viewport}.
 * 
 * @author 		MoltenWolfCub
 * @version     %I%
 * @see 		Screen
 * @see 		OrthographicCamera
 * @see 		Viewport
 */
public class GameScreen implements Screen {
	/** The {@code Viewport} used to render the display at different resolutions.*/
	protected Viewport view;
	/** The camera used to render the screen.*/
	protected OrthographicCamera camera;

	/** 
	 * The {@code Rpg}game that is using this screen.
	 * Used for getting {@code SpriteBatch} and {@code TextureAtlas}.
	*/
	public final Rpg game;

	/** The instance of {@code Background} that will be used for the lifetime of this screen.*/
	protected final Background background;
	/** The instance of {@code Tiles} that will be used for the lifetime of this screen.*/
	protected final Tiles tiles;
	/** The instance of {@code Editor} that will be used for the lifetime of this screen.*/
	protected final Editor editor;
	/** The instance of {@code LevelStorage} that will be used for the lifetime of this screen.*/
	protected final LevelStorage levelStorage;
	/** The instance of {@code Player} that will be used for the lifetime of this screen.*/
	protected final Player player;

	/** Keeps track of Whether the gameLoop is currently being ran.*/
	protected Boolean isLoopRunning = true;
	/** The camera X position within the entire level.*/
	public Integer camX = 0;
	/** The camera Y position within the entire level.*/
	public Integer camY = 0;

	/**
	 * Constructor for {@code GameScreen} that initializes instances
	 * of the following:
	 * <ul>
	 * <li>{@code Background}</li>
	 * <li>{@code LevelStorage}</li>
	 * <li>{@code Tiles}</li>
	 * <li>{@code Editor}</li>
	 * <li>{@code Player}</li>
	 * </ul>
	 * Also sets up a viewport and camera for this screen and it's instances
	 * to draw on.
	 * 
	 * @param game 		The instance of {@code Rpg} that is making this screen.
	 * @see 			Rpg
	 * @see 			Background
	 * @see 			LevelStorage
	 * @see 			Tiles
	 * @see 			Editor
	 * @see 			Player
	 */
	public GameScreen(Rpg game) {
		this.game = game;

		camera = new OrthographicCamera();
		camera.setToOrtho(false);
		view = new FitViewport(Constants.WINDOW_WIDTH, Constants.WINDOW_HEIGHT, camera);

		this.background = new Background(game);
		this.levelStorage = new LevelStorage();
		this.tiles = new Tiles(game, levelStorage);
		this.editor = new Editor(game, levelStorage, view);

		this.player = new Player(this.game);
	}

	/**
	 * Clears the screen to White and runs the various paint scripts using a SpriteBatch.
	 * 
	 * @see 	com.badlogic.gdx.graphics.g2d.SpriteBatch
	 * @see 	Background#paint
	 * @see 	Tiles#paint
	 * @see 	Editor#paint
	 * @see 	Player#paint
	 */
	protected void draw() {
		ScreenUtils.clear(1, 1, 1, 1);

		camera.update();
		game.batch.setProjectionMatrix(camera.combined);


		game.batch.begin();

		this.background.paint(camX, camY);
		this.tiles.paintBg(camX, camY);
		this.player.paint(camX, camY);
		this.tiles.paintFg(camX, camY);
		this.editor.paint(camX, camY);
		
		game.batch.end();

	}


	
	@Override
	public void render(float delta) {
		if (isLoopRunning) {
			this.player.tick();
			this.editor.tick(camX, camY);
			updateCameraPos();
			draw();
		}	
	}
	/**
	 * Allows the game loop to start ticking objects and drawing everything to the screen
	 */
	public void startGameLoop() {
		isLoopRunning = true;
	}
	/**
	 * Stops the game loop from ticking objects and drawing everything to the screen
	 */
	public void stopGameLoop() {
		isLoopRunning = true;
	}

	/**
	 * Updates the camX and camY variables based on the player's current position.
	 * It will also fence the camera within the level so you don't go off the level edge.
	 * <p>
	 * If the level editor is open it will offset the fencing by the nesessary amount to
	 * allow give the edge of the map visiblity while the palette is open.
	 * 
	 * @see		#camX
	 * @see		#camY
	 */
	public void updateCameraPos() {
		Integer offsetWidth = editor.getPalletteOffset();

		camX = Math.min(Math.max(player.playerX+offsetWidth/2, 0), levelStorage.GMAX*Constants.TILE_SIZE- Constants.WINDOW_WIDTH+offsetWidth);
		camY = Math.min(Math.max(player.playerY, 0), levelStorage.GMAX*Constants.TILE_SIZE- Constants.WINDOW_HEIGHT);

	}


	@Override
	public void dispose() {
		background.dispose();
		player.dispose();
		tiles.dispose();
		editor.dispose();
		levelStorage.dispose();
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
