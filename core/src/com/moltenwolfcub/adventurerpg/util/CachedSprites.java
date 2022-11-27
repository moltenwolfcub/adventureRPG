package com.moltenwolfcub.adventurerpg.util;

import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

/**
 * A class to manage and store {@code Sprite}s
 * for the entire project so that a cached
 * version of each sprite can be referenced
 * rather than generating a new one multiple 
 * times per tick.
 * 
 * @author      MoltenWolfCub
 * @version     %I%
 * @see         Sprite
 */
public class CachedSprites {

    private static Map<String, Sprite> textures = new HashMap<String, Sprite>();

    /**
     * Obtains a {@code Sprite} from
     * the atlas.
     * 
     * @param atlas     The atlas obtaining it from
     * @param texture   The string name of the texture
     *                  in the atlas
     * @return          The sprite in the atlas
     */
    public static Sprite getSprite(TextureAtlas atlas, String texture) {
        if (!textures.containsKey(texture)) {
            textures.put(texture, atlas.createSprite(texture));
            System.out.println("Creating new texture");
        }
        System.out.println("Obtained Texture");
        return textures.get(texture);
    }
}
