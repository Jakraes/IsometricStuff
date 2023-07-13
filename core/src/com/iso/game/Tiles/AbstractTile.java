package com.iso.game.Tiles;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.iso.game.TextureManager;

public class AbstractTile {
    private final int index;
    private final boolean solid;

    public AbstractTile(int index, boolean solid) {
        this.index = index;
        this.solid = solid;
    }

    public TextureRegion getTexture() {
        return TextureManager.getInstance().getTexture(index);
    }

    public boolean isSolid() {
        return solid;
    }
}
