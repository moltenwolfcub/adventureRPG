package com.moltenwolfcub.adventurerpg;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.moltenwolfcub.adventurerpg.util.Constants;

public class Player {
	private final Rpg game;

	private Sprite sprite;
	private int playerX = 0;
	private int playerY = 0;
	private PlayerDir playerDir = PlayerDir.UP;

	private double joyX = 0;
	private double joyY = 0;
	private double joyDist = 0;
    
	public Player(Rpg game) {
        this.game = game;

		this.sprite = game.spriteTextureAtlas.createSprite("player/Idle"+playerDir.getTextureSuffix());
		this.sprite.setScale(2);
    }

    public void tick() {
		controls();
		if (joyDist > 0) {
			movement();
		}
	}
	private void movement() {
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
	}
	private void controls() {
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

	public void paint() {
		sprite.setCenter(playerX, playerY);

		setRotatedTexture("player/Idle");

		sprite.draw(game.batch);
	}

	public void setRotatedTexture(String texture) {
		setRotatedTexture(texture, "");
	}
	public void setRotatedTexture(String texture, String suffix) {
		setTexture(texture+playerDir.getTextureSuffix()+suffix);
	}
	public void setTexture(String texture) {
		sprite.setRegion(game.spriteTextureAtlas.findRegion(texture));
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
