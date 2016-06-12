/*
 * Copyright (C) Klaus Hauschild - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Klaus Hauschild <klaus.hauschild.1984@gmail.com>, 2016
 */
package com.nintendostuff.worldcup;

import com.jme3.app.FlyCamAppState;
import com.jme3.app.SimpleApplication;
import com.jme3.system.AppSettings;
import com.nintendostuff.worldcup.match.MatchAppState;

/**
 * @author Klaus Hauschild
 */
public class WorldCup extends SimpleApplication {

    {
        setShowSettings(false);
        final AppSettings settings = new AppSettings(true);
        settings.setUseJoysticks(true);
        settings.setWidth(1024);
        settings.setHeight(768);
        settings.setVSync(true);
        settings.setTitle("World Cup");
        setSettings(settings);
        setDisplayStatView(false);
    }

    @Override
    public void simpleInitApp() {
        final MatchAppState matchAppState = new MatchAppState(rootNode);
        stateManager.attach(matchAppState);

        stateManager.detach(stateManager.getState(FlyCamAppState.class));
        // stateManager.detach(stateManager.getState(DebugKeysAppState.class));
    }

}
