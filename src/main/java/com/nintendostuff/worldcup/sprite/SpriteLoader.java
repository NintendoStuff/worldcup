/*
 * Copyright (C) Klaus Hauschild - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Klaus Hauschild <klaus.hauschild.1984@gmail.com>, 2016
 */
package com.nintendostuff.worldcup.sprite;

import com.jme3.asset.AssetManager;
import com.jme3.texture.Texture;
import com.nintendostuff.worldcup.GsonAssetLoader;

/**
 * @author Klaus Hauschild
 */
public class SpriteLoader extends GsonAssetLoader<SpriteDefinition, Sprite> {

    private static boolean registered;

    public SpriteLoader() {
        super(SpriteDefinition.class);
    }

    public static void register(final AssetManager assetManager) {
        if (registered) {
            return;
        }
        assetManager.registerLoader(SpriteLoader.class, "sprite");
        registered = true;
    }

    @Override
    protected Sprite process(final SpriteDefinition spriteDefinition, final AssetManager manager) {
        final Texture texture = manager.loadTexture(spriteDefinition.texture);
        return new Sprite(spriteDefinition.name, texture, spriteDefinition.size, spriteDefinition.transparent,
                spriteDefinition.rotation, spriteDefinition.translation, manager);
    }

}
