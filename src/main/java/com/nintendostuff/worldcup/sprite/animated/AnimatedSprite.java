/*
 * Copyright (C) Klaus Hauschild - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Klaus Hauschild <klaus.hauschild.1984@gmail.com>, 2016
 */
package com.nintendostuff.worldcup.sprite.animated;

import com.jme3.asset.AssetManager;
import com.jme3.math.Vector2f;
import com.jme3.math.Vector3f;
import com.nintendostuff.worldcup.sprite.Sprite;

/**
 * @author Klaus Hauschild
 */
public class AnimatedSprite extends Sprite {

    public static AnimatedSprite load(final String name, final AssetManager assetManager) {
        AnimatedSpriteLoader.register(assetManager);
        return assetManager.loadAsset(new AnimatedSpriteKey(name));
    }

    public AnimatedSprite(final String name, final Vector2f size, final boolean transparent, final Vector3f rotation,
            final Vector3f translation, final AssetManager assetManager) {
        super(name, null, size, transparent, rotation, translation, assetManager);
    }

}
