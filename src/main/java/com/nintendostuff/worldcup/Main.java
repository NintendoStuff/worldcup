/*
 * Copyright (C) Klaus Hauschild - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Klaus Hauschild <klaus.hauschild.1984@gmail.com>, 2016
 */
package com.nintendostuff.worldcup;

import java.io.File;

import com.jme3.system.NativeLibraryLoader;

/**
 * @author Klaus Hauschild
 */
public class Main {

    public static void main(final String[] args) {
        final File nativesDirectory = new File(System.getProperty("java.io.tmpdir"), "worldcup-natives");
        if (!nativesDirectory.exists()) {
            nativesDirectory.mkdirs();
        }
        NativeLibraryLoader.setCustomExtractionFolder(nativesDirectory.getAbsolutePath());
        final WorldCup worldCup = new WorldCup();
        worldCup.start();
    }

}
