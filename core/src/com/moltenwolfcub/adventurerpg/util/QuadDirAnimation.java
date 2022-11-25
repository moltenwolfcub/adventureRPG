package com.moltenwolfcub.adventurerpg.util;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;

/**
 * An {@code Animation} class that manages 
 * a animation for four directions.
 * <p>
 * It stores an Up, Down, Left and Right array
 * of {@code TextureRegion}s and contains methods
 * that can be used to switch between each axis'
 * animations.
 * 
 * @author 		MoltenWolfCub
 * @version     %I%
 */
public class QuadDirAnimation extends Animation<TextureRegion> {

    /** An Array of {@code TextureRegion}s for the up direction */
    public TextureRegion[] upKeyFrames;
    /** An Array of {@code TextureRegion}s for the down direction */
    public TextureRegion[] downKeyFrames;
    /** An Array of {@code TextureRegion}s for the left direction */
    public TextureRegion[] leftKeyFrames;
    /** An Array of {@code TextureRegion}s for the right direction */
    public TextureRegion[] rightKeyFrames;

    /** 
     * The constructor for this animation
     *
     * @param frameDuration     The number of ticks the animation holds
     *                          on one frame for.
     * @param up                The set of {@code TextureRegion}s the upwards direction
     * @param down              The set of {@code TextureRegion}s the downwars direction
     * @param left              The set of {@code TextureRegion}s the leftwards direction
     * @param right             The set of {@code TextureRegion}s the rightwards direction
	 * @see						TextureRegion
     */
    public QuadDirAnimation(Float frameDuration, Array<? extends TextureRegion> up, Array<? extends TextureRegion> down, Array<? extends TextureRegion> left, Array<? extends TextureRegion> right) {
        super(frameDuration, up);

        TextureRegion[] frames = new TextureRegion[up.size];
        for (Integer i = 0, n = up.size; i < n; i++) {
            frames[i] = up.get(i);
        }
        this.upKeyFrames = frames;

        frames = new TextureRegion[down.size];
        for (Integer i = 0, n = down.size; i < n; i++) {
            frames[i] = down.get(i);
        }
        this.downKeyFrames = frames;

        frames = new TextureRegion[left.size];
        for (Integer i = 0, n = left.size; i < n; i++) {
            frames[i] = left.get(i);
        }
        this.leftKeyFrames = frames;

        frames = new TextureRegion[right.size];
        for (Integer i = 0, n = right.size; i < n; i++) {
            frames[i] = right.get(i);
        }
        this.rightKeyFrames = frames;
    }

	/**
	 * Start animating upwards.
	 * <p>
	 * Sets the animation to use the upwards frame set.
	 * @see 		#upKeyFrames
	 */
    public void setUp() {
        setKeyFrames(upKeyFrames);
    }
	/**
	 * Start animating downwards.
	 * <p>
	 * Sets the animation to use the downwards frame set.
	 * @see 		#downKeyFrames
	 */
    public void setDown() {
        setKeyFrames(downKeyFrames);
    }
	/**
	 * Start animating leftwards.
	 * <p>
	 * Sets the animation to use the leftwards frame set.
	 * @see 		#leftKeyFrames
	 */
    public void setLeft() {
        setKeyFrames(leftKeyFrames);
    }
	/**
	 * Start animating rightwards.
	 * <p>
	 * Sets the animation to use the rightwards frame set.
	 * @see 		#rightKeyFrames
	 */
    public void setRight() {
        setKeyFrames(rightKeyFrames);
    }
    
}
