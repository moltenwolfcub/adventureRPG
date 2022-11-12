package com.moltenwolfcub.adventurerpg.util;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;

public class QuadDirAnimation extends Animation<TextureRegion> {

    public TextureRegion[] upKeyFrames;
    public TextureRegion[] downKeyFrames;
    public TextureRegion[] leftKeyFrames;
    public TextureRegion[] rightKeyFrames;

    public QuadDirAnimation(float frameDuration, Array<? extends TextureRegion> up, Array<? extends TextureRegion> down, Array<? extends TextureRegion> left, Array<? extends TextureRegion> right) {
        super(frameDuration, up);

        TextureRegion[] frames = new TextureRegion[up.size];
        for (int i = 0, n = up.size; i < n; i++) {
            frames[i] = up.get(i);
        }
        this.upKeyFrames = frames;

        frames = new TextureRegion[down.size];
        for (int i = 0, n = down.size; i < n; i++) {
            frames[i] = down.get(i);
        }
        this.downKeyFrames = frames;

        frames = new TextureRegion[left.size];
        for (int i = 0, n = left.size; i < n; i++) {
            frames[i] = left.get(i);
        }
        this.leftKeyFrames = frames;

        frames = new TextureRegion[right.size];
        for (int i = 0, n = right.size; i < n; i++) {
            frames[i] = right.get(i);
        }
        this.rightKeyFrames = frames;
    }

    public void setUp() {
        setKeyFrames(upKeyFrames);
    }
    public void setDown() {
        setKeyFrames(downKeyFrames);
    }
    public void setLeft() {
        setKeyFrames(leftKeyFrames);
    }
    public void setRight() {
        setKeyFrames(rightKeyFrames);
    }
    
}
