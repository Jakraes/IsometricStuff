package com.iso.game.Tiles;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.iso.game.TextureManager;

public class AbstractTile {
    private final int index;

    public AbstractTile(int index) {
        this.index = index;
    }

    public TextureRegion getTexture() {
        return TextureManager.getInstance().getTexture(index);
    }
}
