package com.moltenwolfcub.adventurerpg.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.moltenwolfcub.adventurerpg.Rpg;
import com.moltenwolfcub.adventurerpg.util.Constants;

public class GameScreen implements Screen {
	private Viewport view;
	private OrthographicCamera camera;

	private final Rpg game;

	private boolean isLoopRunning = true;

	private Sprite player;
	public int playerX = 0;
	public int playerY = 0;
	public PlayerDir playerDir = PlayerDir.UP;

	private double joyX = 0;
	private double joyY = 0;
	private double joyDist = 0;

	public GameScreen(Rpg game) {
		this.game = game;
		this.player = game.spriteTextureAtlas.createSprite("characterOneIdleForward");

		camera = new OrthographicCamera();
		camera.setToOrtho(false);
		view = new FitViewport(Constants.DESKTOP_WINDOW_WIDTH, Constants.DESKTOP_WINDOW_HEIGHT, camera);


		this.player.setScale(2);
	}

	private void tickPlayer() {
		playerControls();
		if (joyDist > 0) {
			playerMovement();
		}
	}
	private void playerMovement() {
		if (joyDist > 1) {
			joyDist = Constants.PLAYER_DIAGONAL_SPEED_BONUS;
		}
		joyX = joyX/joyDist;
		joyY = joyY/joyDist;
		tryMove(Constants.PLAYER_SPEED_MULTIPLIER*joyX, Constants.PLAYER_SPEED_MULTIPLIER*joyY);
		if (joyX < 0) {
			playerDir = PlayerDir.LEFT;
		} else if (joyX > 0) {
			playerDir = PlayerDir.RIGHT;
		} else if (joyY < 0) {
			playerDir = PlayerDir.DOWN;
		} else {
			playerDir = PlayerDir.UP;
		}
		setPlayerRotatedTexture("characterOneIdle");
	}
	private void playerControls() {
		joyX = Gdx.input.isKeyPressed(Keys.RIGHT) == true || Gdx.input.isKeyPressed(Keys.D) == true ? 1 : 0;
		joyX -= Gdx.input.isKeyPressed(Keys.LEFT) == true || Gdx.input.isKeyPressed(Keys.A) == true ? 1 : 0;
		joyY = Gdx.input.isKeyPressed(Keys.UP) == true || Gdx.input.isKeyPressed(Keys.W) == true ? 1 : 0;
		joyY -= Gdx.input.isKeyPressed(Keys.DOWN) == true || Gdx.input.isKeyPressed(Keys.S) == true ? 1 : 0;
		joyDist = Math.sqrt(joyX*joyX+joyY*joyY);
	}
	private void tryMove(double dx, double dy) {
		playerX += dx;
		playerY += dy;
	}

	private void paintPlayer() {
		player.setCenter(playerX, playerY);

		player.draw(game.batch);
	}
	private void draw() {
		ScreenUtils.clear(1, 1, 1, 1);

		camera.update();
		game.batch.setProjectionMatrix(camera.combined);

		game.batch.begin();

		paintPlayer();
		
		game.batch.end();

	}

	@Override
	public void render(float delta) {
		if (isLoopRunning) {
			tickPlayer();
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
	
	
	public void setPlayerRotatedTexture(String texture) {
		setPlayerTexture(texture+playerDir.getTextureSuffix());
	}
	public void setPlayerTexture(String texture) {
		player.setRegion(game.spriteTextureAtlas.findRegion(texture));
	}

	public static enum PlayerDir {
		DOWN("Forward"),
		UP("Backward"),
		LEFT("Left"),
		RIGHT("Right");

		private String textureStringSuffix;

		private PlayerDir(String texStr) {
			textureStringSuffix = texStr;
		}

		public String getTextureSuffix() {
			return textureStringSuffix;
		}
	}
}
