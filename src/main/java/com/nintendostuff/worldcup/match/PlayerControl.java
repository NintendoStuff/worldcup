/*
 * Copyright (C) Klaus Hauschild - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Klaus Hauschild <klaus.hauschild.1984@gmail.com>, 2016
 */
package com.nintendostuff.worldcup.match;

import com.jme3.math.FastMath;
import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;
import com.jme3.renderer.RenderManager;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.Node;
import com.jme3.scene.control.AbstractControl;
import com.nintendostuff.worldcup.sprite.animated.AnimatedSprite;
import com.nintendostuff.worldcup.sprite.animated.AnimatedSpriteControl;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

/**
 * @author Klaus Hauschild
 */
public class PlayerControl extends AbstractControl {

    enum Direction {

        RIGHT(0f, 1),

        LEFT(180f, -1),

        ;

        private float angle;
        private int rotation;

        Direction(final float angle, int rotation) {
            this.angle = angle;
            this.rotation = rotation;
        }

        public float getAngle() {
            return angle;
        }

        public int getRotation() {
            return rotation;
        }

    }

    private Direction direction = Direction.RIGHT;
    private boolean   up;
    private boolean   down;
    private boolean   right;
    private boolean   left;

    public void up(final boolean pressed) {
        up = pressed;
        if (pressed) {
            down = false;
        }
    }

    public void down(final boolean pressed) {
        down = pressed;
        if (pressed) {
            up = false;
        }
    }

    public void right(final boolean pressed) {
        right = pressed;
        if (pressed) {
            left = false;
            direction = Direction.RIGHT;
        }
    }

    public void left(final boolean pressed) {
        left = pressed;
        if (pressed) {
            right = false;
            direction = Direction.LEFT;
        }
    }

    @Override
    protected void controlUpdate(final float tpf) {
        // check rotation
        final float rotation = spatial.getWorldRotation().toAngles(null)[1] * FastMath.RAD_TO_DEG;
        final float dist = FastMath.abs(direction.getAngle() - rotation);
        if(dist > 10f) {
            spatial.rotate(0f, direction.getRotation() * tpf * 15f, 0f);
        } else {
            spatial.lookAt(spatial.getWorldTranslation().add(0f, 0f, direction.getRotation()), Vector3f.UNIT_Y);
        }

        // get sprite and its control
        final AnimatedSprite animatedSprite = (AnimatedSprite) ((Node) spatial).getChild(0);
        final AnimatedSpriteControl animatedSpriteControl = animatedSprite.getControl(AnimatedSpriteControl.class);

        // handle 'halt'
        if (!up && !down && !right && !left) {
            animatedSpriteControl.setAnimation("idle");
            return;
        }

        // handle 'walk'
        animatedSpriteControl.setAnimation("walk");
        final Vector3f move = new Vector3f();
        if (up) {
            move.z = -1;
        }
        if (down) {
            move.z = 1;
        }
        if (right) {
            move.x = 1;
        }
        if (left) {
            move.x = -1;
        }
        if (!move.equals(Vector3f.ZERO)) {
            spatial.move(move.mult(tpf * 20f));
        }
    }

    @Override
    protected void controlRender(final RenderManager rm, final ViewPort vp) {
    }

}
