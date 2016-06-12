/*
 * Copyright (C) Klaus Hauschild - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Klaus Hauschild <klaus.hauschild.1984@gmail.com>, 2016
 */
package com.nintendostuff.worldcup.sprite.animated;

import java.util.List;

import com.nintendostuff.worldcup.sprite.SpriteDefinition;

/**
 * @author Klaus Hauschild
 */
public class AnimatedSpriteDefinition extends SpriteDefinition {

    public List<AnimationDefinition> animations;
    public String                    initialAnimation;

    static class AnimationDefinition {

        public String                name;
        public List<FrameDefinition> frames;

        static class FrameDefinition {

            public String texture;
            public long   duration;

        }

    }

}
