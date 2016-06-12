/*
 * Copyright (C) Klaus Hauschild - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Klaus Hauschild <klaus.hauschild.1984@gmail.com>, 2016
 */
package com.nintendostuff.worldcup.match;

import com.jme3.app.Application;
import com.jme3.app.state.AbstractAppState;
import com.jme3.app.state.AppStateManager;
import com.jme3.audio.AudioNode;
import com.jme3.input.KeyInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.math.ColorRGBA;
import com.jme3.math.FastMath;
import com.jme3.math.Vector3f;
import com.jme3.renderer.RenderManager;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.scene.control.AbstractControl;
import com.nintendostuff.worldcup.sprite.Sprite;
import com.nintendostuff.worldcup.sprite.animated.AnimatedSprite;

/**
 * @author Klaus Hauschild
 */
public class MatchAppState extends AbstractAppState {

    private Node rootNode;
    private Node matchRoot = new Node("match root");

    public MatchAppState(final Node rootNode) {
        this.rootNode = rootNode;
    }

    @Override
    public void initialize(final AppStateManager stateManager, final Application app) {
        super.initialize(stateManager, app);
        matchRoot.detachAllChildren();
        rootNode.attachChild(matchRoot);
        initializeCam(app);
        initializeField(app);
        initializeTeams(app);
        initializeSound(app);
    }

    private void initializeCam(final Application app) {
        app.getCamera().setLocation(new Vector3f(-28.0f, 30.0f, 75.0f));
    }

    private void initializeTeams(final Application app) {
        final AnimatedSprite kunioSprite = AnimatedSprite.load("textures/teams/japan/kunio/kunio.animatedsprite",
                app.getAssetManager());
        final Node kunioNode = new Node();
        kunioNode.attachChild(kunioSprite);
        matchRoot.attachChild(kunioNode);
        kunioNode.addControl(new PlayerControl());
        if (app.getInputManager().getJoysticks() != null && app.getInputManager().getJoysticks().length > 0) {
            app.getInputManager().getJoysticks()[0].getPovXAxis().assignAxis("move right", "move left");
            app.getInputManager().getJoysticks()[0].getPovYAxis().assignAxis("move up", "move down");
        }

        app.getInputManager().addMapping("move right", new KeyTrigger(KeyInput.KEY_RIGHT));
        app.getInputManager().addMapping("move left", new KeyTrigger(KeyInput.KEY_LEFT));
        app.getInputManager().addMapping("move up", new KeyTrigger(KeyInput.KEY_UP));
        app.getInputManager().addMapping("move down", new KeyTrigger(KeyInput.KEY_DOWN));
        app.getInputManager().addListener((ActionListener) (name, isPressed, tpf) -> {
            switch (name) {
                case "move right":
                    kunioNode.getControl(PlayerControl.class).right(isPressed);
                    break;
                case "move left":
                    kunioNode.getControl(PlayerControl.class).left(isPressed);
                    break;
                case "move up":
                    kunioNode.getControl(PlayerControl.class).up(isPressed);
                    break;
                case "move down":
                    kunioNode.getControl(PlayerControl.class).down(isPressed);
            }

        }, "move right", "move left", "move up", "move down");
        kunioNode.addControl(new AbstractControl() {
            protected void controlUpdate(float tpf) {
                app.getCamera().lookAt(this.spatial.getWorldTranslation(), Vector3f.UNIT_Y);

                final float distance = spatial.getWorldTranslation().getX() - app.getCamera().getLocation().getX();
                if (FastMath.abs(distance) > 0.5f) {
                    app.getCamera().setLocation(app.getCamera().getLocation().add(distance * tpf * 3, 0f, 0f));
                }
            }

            protected void controlRender(RenderManager rm, ViewPort vp) {
            }
        });
    }

    private void initializeField(final Application app) {
        // 'sky'
        app.getViewPort().setBackgroundColor(ColorRGBA.Blue);

        // field
        final Sprite fieldSprite = Sprite.load("sprites/match/field/field.sprite", app.getAssetManager());
        matchRoot.attachChild(fieldSprite);

        // goal
        final Node goalNode = new Node("Goal");
        goalNode.move(-107.5f, 0f, 0f);
        matchRoot.attachChild(goalNode);
        final Sprite goalSideSprite = Sprite.load("sprites/match/field/goal_side.sprite", app.getAssetManager());
        goalSideSprite.move(0f, 0f, 11f);
        goalNode.attachChild(goalSideSprite);
        final Geometry otherGoalSideSprite = goalSideSprite.clone();
        otherGoalSideSprite.move(0f, 0f, -22f);
        goalNode.attachChild(otherGoalSideSprite);
        final Sprite goalTopSprite = Sprite.load("sprites/match/field/goal_top.sprite", app.getAssetManager());
        goalTopSprite.move(7f, 20.5f, 0f);
        goalNode.attachChild(goalTopSprite);
        final Sprite goalBackSprite = Sprite.load("sprites/match/field/goal_back.sprite", app.getAssetManager());
        goalBackSprite.move(10f, 0f, 0f);
        goalNode.attachChild(goalBackSprite);

        // other goal
        final Spatial otherGoal = goalNode.clone();
        otherGoal.rotate(0f, 180f * FastMath.DEG_TO_RAD, 0f);
        otherGoal.move(215f, 0f, 0f);
        matchRoot.attachChild(otherGoal);

        // border
        final Sprite longBorderSprite = Sprite.load("sprites/match/field/field_border_long.sprite", app.getAssetManager());
        longBorderSprite.move(115.2f, 0f, -52.35f);
        matchRoot.attachChild(longBorderSprite);
        final Geometry otherLongBorderSprite = longBorderSprite.clone();
        otherLongBorderSprite.move(0f, 0f, 104.7f);
        matchRoot.attachChild(otherLongBorderSprite);
        final Sprite shortBorderSprite = Sprite.load("sprites/match/field/field_border_short.sprite", app.getAssetManager());
        shortBorderSprite.move(-115.2f, 0f, -52.35f);
        matchRoot.attachChild(shortBorderSprite);
        final Geometry otherShortBorderSprite = shortBorderSprite.clone();
        otherShortBorderSprite.move(230f, 0f, 0f);
        matchRoot.attachChild(otherShortBorderSprite);
    }

    private void initializeSound(final Application app) {
        AudioNode matchAudio = new AudioNode(app.getAssetManager(), "sound/Match_1.ogg");
        matchAudio.setLooping(true);
        matchAudio.setPositional(false);
        matchAudio.setVolume(3);
        rootNode.attachChild(matchAudio);
        matchAudio.play();
    }

    @Override
    public void update(final float tpf) {
        super.update(tpf);
    }

    @Override
    public void cleanup() {
        super.cleanup();
        matchRoot.removeFromParent();
    }

}
