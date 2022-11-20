package com.moltenwolfcub.adventurerpg;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.moltenwolfcub.adventurerpg.util.Constants;
import com.moltenwolfcub.adventurerpg.util.Keybinds;
import com.moltenwolfcub.adventurerpg.util.QuadDirAnimation;

public class Player {
	private final Rpg game;

	public final Sprite sprite;
	public int playerX = 0;
	public int playerY = 0;
	private PlayerDir playerDir = PlayerDir.DOWN;

    private QuadDirAnimation walkingAnimation;
	private int animationFrame = 1;
	private float animationSpeed = 3f;
	private TextureRegion currentTexture;

	private double joyX = 0;
	private double joyY = 0;
	private double joyDist = 0;
    
	public Player(Rpg game) {
        this.game = game;

		this.sprite = game.spriteTextureAtlas.createSprite("player/idle"+playerDir.getTextureSuffix());
		this.sprite.setScale(2);
		setTexture(game.spriteTextureAtlas.findRegion("player/idle"+playerDir.getTextureSuffix()));

        walkingAnimation = new QuadDirAnimation(animationSpeed,
            game.spriteTextureAtlas.findRegions("player/walk"+PlayerDir.UP.getTextureSuffix()),
            game.spriteTextureAtlas.findRegions("player/walk"+PlayerDir.DOWN.getTextureSuffix()),
            game.spriteTextureAtlas.findRegions("player/walk"+PlayerDir.LEFT.getTextureSuffix()),
            game.spriteTextureAtlas.findRegions("player/walk"+PlayerDir.RIGHT.getTextureSuffix())
        );
    }

	public void dispose() {
		
	}

    public void tick() {
		controls();
		if (joyDist > 0) {
			movement();
			currentTexture = walkingAnimation.getKeyFrame(animationFrame, true);
		} else {
			currentTexture = getRotatedTexture("player/idle");
			animationFrame = 0;
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
		animationFrame += 1;
	}
	private void controls() {
		joyX = Keybinds.RIGHT.isPressed() == true ? 1 : 0;
		joyX -= Keybinds.LEFT.isPressed() == true ? 1 : 0;
		joyY = Keybinds.FORWARDS.isPressed() == true ? 1 : 0;
		joyY -= Keybinds.BACKWARDS.isPressed() == true ? 1 : 0;
		joyDist = Math.sqrt(joyX*joyX+joyY*joyY);
	}
	private void tryMove(double dx, double dy) {
		playerX += dx;
		playerY += dy;
	}

	public void paint(int camX, int camY) {
		sprite.setCenter(playerX- camX + Constants.WINDOW_WIDTH/2, playerY- camY + Constants.WINDOW_HEIGHT/2);

		this.playerDir.updateQuadAnim(walkingAnimation);
		setTexture(currentTexture);

		sprite.draw(game.batch);
	}

	public TextureRegion getRotatedTexture(String texture) {
		return getRotatedTexture(texture, "");
	}
	public TextureRegion getRotatedTexture(String texture, String suffix) {
		return getTexture(texture+playerDir.getTextureSuffix()+suffix);
	}
	public TextureRegion getTexture(String texture) {
		return game.spriteTextureAtlas.findRegion(texture);
	}
	public void setTexture(TextureRegion texture) {
		sprite.setRegion(texture);
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

		public void updateQuadAnim(QuadDirAnimation animation) {
			switch (this) {
				case UP: animation.setUp(); break;
				case DOWN: animation.setDown(); break;
				case LEFT: animation.setLeft(); break;
				case RIGHT: animation.setRight(); break;
			}
		}
	}
}
