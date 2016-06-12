/*
 * Copyright (C) Klaus Hauschild - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Klaus Hauschild <klaus.hauschild.1984@gmail.com>, 2016
 */
package com.nintendostuff.worldcup.sprite.animated;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.jme3.renderer.RenderManager;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.control.AbstractControl;
import com.nintendostuff.worldcup.sprite.Sprite;
import com.nintendostuff.worldcup.sprite.animated.Animation.Frame;

/**
 * @author Klaus Hauschild
 */
public class AnimatedSpriteControl extends AbstractControl implements Cloneable {

    private final Map<String, Animation> animations;

    private String                       initialAnimation;
    private Animation                    animation;
    private Frame                        frame;
    private long                         time = System.currentTimeMillis();

    public AnimatedSpriteControl(final List<Animation> animations, final String initialAnimation) {
        this.initialAnimation = initialAnimation;
        this.animations = animations.stream().collect(Collectors.toMap(animation -> animation.getName(), animation -> animation));
    }

    public void setAnimation(final String name) {
        if (animation != null && animation.getName().equals(name)) {
            return;
        }
        animation = animations.get(name);
        animation.reset();
        updateFrame();
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    @Override
    protected void controlUpdate(final float tpf) {
        if (animation == null) {
            setAnimation(initialAnimation);
        }

        final long span = System.currentTimeMillis() - time;
        if (frame.getDuration() > 0 && span > frame.getDuration()) {
            time = System.currentTimeMillis();
            updateFrame();
        }
    }

    @Override
    protected void controlRender(final RenderManager rm, final ViewPort vp) {
    }

    private void updateTexture() {
        ((Sprite) spatial).setTexture(frame.getTexture());
    }

    private void updateFrame() {
        frame = animation.nextFrame();
        updateTexture();
    }

}
