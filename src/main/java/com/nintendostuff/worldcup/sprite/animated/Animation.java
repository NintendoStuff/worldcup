/*
 * Copyright (C) Klaus Hauschild - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Klaus Hauschild <klaus.hauschild.1984@gmail.com>, 2016
 */
package com.nintendostuff.worldcup.sprite.animated;

import com.jme3.texture.Texture;

import java.util.List;

/**
 * @author Klaus Hauschild
 */
public class Animation {

    private final String      name;
    private final List<Frame> frames;

    private int               frame;

    public Animation(final String name, final List<Frame> frames) {
        this.name = name;
        this.frames = frames;
        reset();
    }

    public String getName() {
        return name;
    }

    public Frame nextFrame() {
        frame++;
        frame = frame % frames.size();
        return frames.get(frame);
    }

    public void reset() {
        frame = -1;
    }

    static class Frame {

        private Texture texture;
        private long duration;

        public Frame(final Texture texture, final long duration) {
            this.texture = texture;
            this.duration = duration;
        }

        public Texture getTexture() {
            return texture;
        }

        public long getDuration() {
            return duration;
        }

    }

}
