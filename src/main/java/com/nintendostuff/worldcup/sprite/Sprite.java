/*
 * Copyright (C) Klaus Hauschild - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Klaus Hauschild <klaus.hauschild.1984@gmail.com>, 2016
 */
package com.nintendostuff.worldcup.sprite;

import com.jme3.asset.AssetManager;
import com.jme3.material.Material;
import com.jme3.material.RenderState.BlendMode;
import com.jme3.material.RenderState.FaceCullMode;
import com.jme3.math.FastMath;
import com.jme3.math.Vector2f;
import com.jme3.math.Vector3f;
import com.jme3.renderer.queue.RenderQueue.Bucket;
import com.jme3.scene.Geometry;
import com.jme3.scene.shape.Quad;
import com.jme3.texture.Texture;

/**
 * @author Klaus Hauschild
 */
public class Sprite extends Geometry {

    public Sprite(final String name, final Texture texture, Vector2f size, final boolean transparent, final Vector3f rotation,
            final Vector3f translation, final AssetManager assetManager) {
        super(name, new Quad(size.getX(), size.getY()));
        final Material material = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        setMaterial(material);
        if (texture != null) {
            setTexture(texture);
        }
        material.getAdditionalRenderState().setBlendMode(BlendMode.Alpha);
        material.getAdditionalRenderState().setFaceCullMode(FaceCullMode.Off);
        if (transparent) {
            setQueueBucket(Bucket.Transparent);
        } else {
            setQueueBucket(Bucket.Opaque);
        }
        if (rotation != null) {
            rotate(rotation.getX() * FastMath.DEG_TO_RAD, rotation.getY() * FastMath.DEG_TO_RAD, rotation.getZ()
                    * FastMath.DEG_TO_RAD);
        }
        if (translation != null) {
            setLocalTranslation(translation);
        }
    }

    public static Sprite load(final String name, final AssetManager assetManager) {
        SpriteLoader.register(assetManager);
        return assetManager.loadAsset(new SpriteKey(name));
    }

    public void setTexture(final Texture texture) {
        getMaterial().setTexture("ColorMap", texture);
    }

}
