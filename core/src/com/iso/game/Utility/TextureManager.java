package com.iso.game.Utility;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;

public class TextureManager {
    private static final int TEXTUREHEIGHT = 16;
    private static final int TEXTUREWIDTH = 16;
    private static final int ROWS = 2;
    private static final int COLS = 8;
    private static TextureManager instance;

    private static Texture tex;
    private static Array<TextureRegion> textures;

    private TextureManager() {
        tex = new Texture(Gdx.files.internal("tiles.png"));
        textures = new Array<>();

        for (int y = 0; y < ROWS; y++) {
            for (int x = 0; x < COLS; x++) {
                textures.add(new TextureRegion(tex, x * TEXTUREWIDTH, y * TEXTUREHEIGHT, TEXTUREWIDTH, TEXTUREHEIGHT));
            }
        }
    }

    public static TextureManager getInstance() {
        if (instance == null) instance = new TextureManager();
        return instance;
    }

    public static void dispose() {
        if (instance == null) return;
        tex.dispose();
    }

    public TextureRegion getTexture(int index) {
        return textures.get(index);
    }
}
