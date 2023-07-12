package com.iso.game.Map;

import com.iso.game.Tiles.AbstractTile;
import com.iso.game.Tiles.TileAir;

public class Chunk {
    private final AbstractTile[][][] tiles;
    private final int xSize, ySize, zSize;

    public Chunk(int xSize, int ySize, int zSize) {
        this.xSize = xSize;
        this.ySize = ySize;
        this.zSize = zSize;
        tiles = new AbstractTile[zSize][ySize][xSize];

        for (int z = 0; z < zSize; z++) {
            for (int y = 0; y < ySize; y++) {
                for (int x = 0; x < xSize; x++) {
                    tiles[z][y][x] = new TileAir();
                }
            }
        }
    }

    public void setTile(int x, int y, int z, AbstractTile value) {
        tiles[z][y][x] = value;
    }

    public AbstractTile getTile(int x, int y, int z) {
        if (x < 0 || x >= xSize || y < 0 || y >= ySize || z < 0 || z >= zSize) return null;
        return tiles[z][y][x];
    }

    public int getWidth() {
        return xSize;
    }

    public int getDepth() {
        return ySize;
    }

    public int getHeight() {
        return zSize;
    }
}
