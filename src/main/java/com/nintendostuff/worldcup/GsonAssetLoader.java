/*
 * Copyright (C) Klaus Hauschild - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Klaus Hauschild <klaus.hauschild.1984@gmail.com>, 2016
 */
package com.nintendostuff.worldcup;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;

import com.google.gson.Gson;
import com.jme3.asset.AssetInfo;
import com.jme3.asset.AssetLoader;
import com.jme3.asset.AssetManager;

/**
 * @author Klaus Hauschild
 */
public abstract class GsonAssetLoader<DEFINITION, ASSET> implements AssetLoader {

    private final Gson              gson = new Gson();
    private final Class<DEFINITION> assetDefinitionType;

    protected GsonAssetLoader(final Class<DEFINITION> assetDefinitionType) {
        this.assetDefinitionType = assetDefinitionType;
    }

    @Override
    public Object load(final AssetInfo assetInfo) throws IOException {
        try (final Reader reader = new InputStreamReader(assetInfo.openStream())) {
            return process(gson.fromJson(reader, assetDefinitionType), assetInfo.getManager());
        }
    }

    protected abstract ASSET process(final DEFINITION definition, final AssetManager manager);

}
