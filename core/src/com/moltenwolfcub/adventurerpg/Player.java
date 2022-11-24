package com.moltenwolfcub.adventurerpg;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Disposable;
import com.moltenwolfcub.adventurerpg.util.Constants;
import com.moltenwolfcub.adventurerpg.util.Keybinds;
import com.moltenwolfcub.adventurerpg.util.QuadDirAnimation;

/**
 * A class to manage a player in the game.
 * It manages player controls, movement, animation
 * and textures.
 * <p>
 * It stores an X and Y pos for the player,
 * a set of animations, and a {@code Player.PlayerDir}
 * 
 * @author 		MoltenWolfCub
 * @version     %I%
 */
public class Player implements Disposable {
    /** The game instance holding the {@code SpriteBatch} and {@code TextureAtlas} for drawing tiles.*/
	protected final Rpg game;

	/** The Sprite used to draw the player.*/
	public final Sprite sprite;
	/** The X position of the player in the entire map.*/
	public int playerX = 0;
	/** The Y position of the player in the entire map.*/
	public int playerY = 0;
	/** The player's current direction.*/
	protected PlayerDir playerDir = PlayerDir.DOWN;

	/** The player's 4-directional walking animation.*/
    protected QuadDirAnimation walkingAnimation;
	/** The current frame of animation the player is on.*/
	protected int animationFrame = 1;
	/** The number of ticks between frame changes.*/
	protected float animationSpeed = 3f;
	/** The player's currently being drawn texture.*/
	protected TextureRegion currentTexture;

	/** Represents the player's delta along the X-axis as a signum(1, 0, -1)*/
	protected double joyX = 0;
	/** Represents the player's delta along the Y-axis as a signum(1, 0, -1)*/
	protected double joyY = 0;
	/** Represents the player's total delta in 2d space.*/
	protected double joyDist = 0;
    
	/**
	 * Constructor for {@code Player} that sets up the textures, 
	 * {@code Sprite}s and animations.
	 * These are got from the {@code Rpg}'s {@code TextureAtlas}
	 * 
	 * @param game 	The instance of {@code Rpg} that is used by
	 * 				the screen instancing this player.
	 * @see 		QuadDirAnimation
	 * @see 		Rpg#spriteTextureAtlas
	 */
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

	@Override
	public void dispose() {
		
	}

	/**
	 * Game loop for the {@code Player}. It checks
	 * for keyboard input and updates the player's
	 * animations and locations based upon it.
	 * 
	 * @see		#controls()
	 * @see		#movement()
	 */
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
	/**
	 * Calculates how many pixels the player should move
	 * based on {@code joyX}, {@code joyY} and some
	 * {@code Constants}. It reduces diagonal boost effect.
	 * {@code tryMove} is called from here. The {@code playerDir}
	 * value is updated based on the direction of movement. The 
	 * animation frame is also increased here.
	 * 
	 * @see 	Constants
	 * @see 	#animationFrame
	 * @see 	#joyX
	 * @see 	#joyY
	 * @see 	#playerDir
	 * @see 	#tryMove
	 */
	protected void movement() {
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
	/**
	 * Sets {@code joyX} and {@code joyY} based on input
	 * from the user. Uses {@code Keybinds}. {@code joyDist}
	 * is calculated using Pythagoras for the combined distance
	 * of {@code joyX} and {@code joyY}.
	 * 
	 * @see 	#joyX
	 * @see 	#joyY
	 * @see 	#joyDist
	 * @see 	Keybinds
	 */
	protected void controls() {
		joyX = Keybinds.RIGHT.isPressed() == true ? 1 : 0;
		joyX -= Keybinds.LEFT.isPressed() == true ? 1 : 0;
		joyY = Keybinds.FORWARDS.isPressed() == true ? 1 : 0;
		joyY -= Keybinds.BACKWARDS.isPressed() == true ? 1 : 0;
		joyDist = Math.sqrt(joyX*joyX+joyY*joyY);
	}
	/**
	 * Moves {@code playerX} and {@code playerY}. Will 
	 * handle collision detection.
	 * 
	 * @param dx 	How much to try and move the x axis by.
	 * @param dy 	How much to try and move the y axis by.
	 * @see 		#playerX
	 * @see 		#playerY
	 */
	protected void tryMove(double dx, double dy) {
		playerX += dx;
		playerY += dy;
	}
	/**
	 * Updates the {@code sprite} position from
	 * {@code playerX} and {@code playerY} taking
	 * into account the camera position. The texture
	 * of the {@code sprite} is update too from
	 * the {@code playerDir} and {@code walkingAnimation}.
	 * The sprite is drawn useing the {@code SpriteBatch}.
	 * 
	 * @param camX		The current camera X
	 * @param camY		The current camera Y
	 * @see				#sprite
	 * @see				com.badlogic.gdx.graphics.g2d.SpriteBatch
	 */
	public void paint(int camX, int camY) {
		sprite.setCenter(playerX- camX + Constants.WINDOW_WIDTH/2, playerY- camY + Constants.WINDOW_HEIGHT/2);

		this.playerDir.updateQuadAnim(walkingAnimation);
		setTexture(currentTexture);

		sprite.draw(game.batch);
	}

	/**
	 * Gets a {@code TextureRegion} through 
	 * {@code getRotatedTexture(String, String)} 
	 * method using the player's rotation without
	 * a suffix on the texture name.
	 * 
	 * @param texture	The name of the texture in
	 * 					the {@code TextureAtlas} 
	 * 					being retrieved
	 * @return			{@code TextureRegion} from 
	 * 					{@code getRotatedTexture(String, String)} 
	 * 					method
	 * @see				#getRotatedTexture(String, String)
	 */
	public TextureRegion getRotatedTexture(String texture) {
		return getRotatedTexture(texture, "");
	}
	/**
	 * Gets a {@code TextureRegion} through {@code getTexture}
	 * method with the player's current rotation added into
	 * the string name
	 * 
	 * @param texture	The name of the texture in the 
	 * 					{@code TextureAtlas} being retrieved
	 * @param suffix	Any part of the texture name that is
	 * 					appended after the player's direction
	 * @return			{@code TextureRegion} from the 
	 * 					{@code getTexture} method
	 * @see				#getTexture
	 */
	public TextureRegion getRotatedTexture(String texture, String suffix) {
		return getTexture(texture+playerDir.getTextureSuffix()+suffix);
	}
	/**
	 * Gets a {@code TextureRegion} from the {@code TextureAtlas}.
	 * 
	 * @param texture		The name of the texture that is being requested
	 * @return				The {@code TextureRegion} retrieved.
	 * @see					TextureRegion
	 * @see					com.badlogic.gdx.graphics.g2d.TextureAtlas
	 */
	public TextureRegion getTexture(String texture) {
		return game.spriteTextureAtlas.findRegion(texture);
	}
	/**
	 * Sets the texture of the Player's {@code sprite} to
	 * a given texture
	 * @param texture		The {@code TextureRegion} that the sprite
	 * 						is being set to
	 * @see 				#sprite
	 * @see					TextureRegion
	 */
	public void setTexture(TextureRegion texture) {
		sprite.setRegion(texture);
	}

	/**
	 * An enum storing the directions a player can face.
	 * <p>
	 * It stores a string for the name of the direction
	 * used in the {@code TextureAtlas} name.
	 * 
	 * @author 		MoltenWolfCub
	 */
	public static enum PlayerDir {
		/** Downwards direction*/DOWN("Forward"),
		/** Upwards direction*/UP("Backward"),
		/** Leftwards direction*/LEFT("Left"),
		/** Rightwards direction*/RIGHT("Right");

		/**The name of the direction in the {@code TextureAtlas} */
		protected String textureStringSuffix;

		/**
		 * Constructor for a player direction.
		 * 
		 * @param texStr	name of direction when retrieving textures
		 */
		private PlayerDir(String texStr) {
			textureStringSuffix = texStr;
		}

		/**
		 * The suffix used when retrieving textures
		 * @return The name of the direction when getting
		 * from the {@code TextureAtlas}
		 */
		public String getTextureSuffix() {
			return textureStringSuffix;
		}

		/**
		 * Sets the {@code QuadDirAnimation} parsed to 
		 * use the texture of the direction that this
		 * is currently set to.
		 * @param animation		The animation to be update
		 */
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
