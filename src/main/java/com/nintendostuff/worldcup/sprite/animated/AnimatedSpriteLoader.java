/*
 * Copyright (C) Klaus Hauschild - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Klaus Hauschild <klaus.hauschild.1984@gmail.com>, 2016
 */
package com.nintendostuff.worldcup.sprite.animated;

import java.util.stream.Collectors;

import com.jme3.asset.AssetManager;
import com.nintendostuff.worldcup.GsonAssetLoader;
import com.nintendostuff.worldcup.sprite.animated.Animation.Frame;

/**
 * @author Klaus Hauschild
 */
public class AnimatedSpriteLoader extends GsonAssetLoader<AnimatedSpriteDefinition, AnimatedSprite> {

    private static boolean registered;

    public static void register(final AssetManager assetManager) {
        if (registered) {
            return;
        }
        assetManager.registerLoader(AnimatedSpriteLoader.class, "animatedsprite");
        registered = true;
    }

    public AnimatedSpriteLoader() {
        super(AnimatedSpriteDefinition.class);
    }

    @Override
    protected AnimatedSprite process(final AnimatedSpriteDefinition animatedSpriteDefinition, final AssetManager manager) {
        final AnimatedSprite animatedSprite = new AnimatedSprite(animatedSpriteDefinition.name, animatedSpriteDefinition.size,
                animatedSpriteDefinition.transparent, animatedSpriteDefinition.rotation, animatedSpriteDefinition.translation,
                manager);
        final AnimatedSpriteControl animatedSpriteControl = new AnimatedSpriteControl(animatedSpriteDefinition.animations
                .stream()
                .map(animationDefinition -> new Animation(animationDefinition.name,
                        animationDefinition.frames
                                .stream()
                                .map(frameDefinition -> new Frame(manager.loadTexture(frameDefinition.texture),
                                        frameDefinition.duration)).collect(Collectors.toList()))).collect(Collectors.toList()),
                animatedSpriteDefinition.initialAnimation);
        animatedSprite.addControl(animatedSpriteControl);
        return animatedSprite;
    }

}
