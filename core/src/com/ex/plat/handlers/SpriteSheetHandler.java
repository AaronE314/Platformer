package com.ex.plat.handlers;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class SpriteSheetHandler {

    private TextureAtlas textureAtlas;

    public SpriteSheetHandler(String atlasPath) {

        textureAtlas = new TextureAtlas(Gdx.files.internal(atlasPath));
    }

    public TextureRegion getTexture(String name) {
        return textureAtlas.findRegion(name);
    }

    public TextureRegion[] getAnimation(String name, int maxFrames) {
        TextureRegion[] textures = new TextureRegion[maxFrames];

        for (int i = 1; i<=maxFrames; i++) {
            textures[i-1] = textureAtlas.findRegion(name + i);
        }

        return textures;
    }

}
