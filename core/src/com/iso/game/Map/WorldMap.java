package com.iso.game.Map;

import com.badlogic.gdx.math.MathUtils;
import com.iso.game.SimplexNoise;
import com.iso.game.Tiles.*;

public class WorldMap extends AbstractMap {
    public final int CHUNK_WIDTH = 16, CHUNK_DEPTH = 16, CHUNK_HEIGHT = 32;
    private final int WATER_HEIGHT = CHUNK_HEIGHT / 4;

    public WorldMap(int seed, float step) {
        super(seed, step);
    }

    @Override
    public Chunk generateChunk(int x, int y) {
        Chunk result = new Chunk(CHUNK_WIDTH, CHUNK_DEPTH, CHUNK_HEIGHT);

        for (int yTemp = 0; yTemp < CHUNK_DEPTH; yTemp++) {
            for (int xTemp = 0; xTemp < CHUNK_WIDTH; xTemp++) {
                int height = MathUtils.clamp((int) ((SimplexNoise.noise(x * CHUNK_WIDTH * getStep() + xTemp * getStep() + getSeed(), y * CHUNK_DEPTH * getStep() + yTemp * getStep() + getSeed()) + 1) / 2 * CHUNK_HEIGHT) - 1, 0, CHUNK_HEIGHT - 2);
                result.setTile(xTemp, yTemp, height, new TileGrass());
                for (int z = 0; z < height; z++) {
                    result.setTile(xTemp, yTemp, z, new TileDirt());
                }
            }
        }

        for (int yTemp = 0; yTemp < CHUNK_DEPTH; yTemp++) {
            for (int xTemp = 0; xTemp < CHUNK_WIDTH; xTemp++) {
                if (result.getTile(xTemp, yTemp, WATER_HEIGHT) instanceof TileGrass)
                    result.setTile(xTemp, yTemp, WATER_HEIGHT, new TileSand());
                if (result.getTile(xTemp, yTemp, WATER_HEIGHT + 1) instanceof TileGrass)
                    result.setTile(xTemp, yTemp, WATER_HEIGHT + 1, new TileSand());

                for (int zTemp = WATER_HEIGHT; zTemp >= 0; zTemp--) {
                    if (result.getTile(xTemp, yTemp, zTemp) instanceof TileAir || result.getTile(xTemp, yTemp, zTemp) instanceof TileSand)
                        result.setTile(xTemp, yTemp, zTemp, new TileWater());
                }
            }
        }

        for (int yTemp = 0; yTemp < CHUNK_DEPTH; yTemp++) {
            for (int xTemp = 0; xTemp < CHUNK_WIDTH; xTemp++) {
                for (int zTemp = 0; zTemp < CHUNK_HEIGHT; zTemp++) {
                    if (result.getTile(xTemp, yTemp, zTemp) instanceof TileGrass && zTemp < CHUNK_HEIGHT - 1 && zTemp > WATER_HEIGHT + 1) {
                        int treeX = (getSeed() + x * 33 + y + xTemp);
                        int treeY = (getSeed() + y * 33 + x + yTemp);
                        if ((SimplexNoise.noise(treeX, treeY) + 1) / 2 > 0.7) result.setTile(xTemp, yTemp, zTemp + 1, new TileTree());
                        break;
                    }
                }
            }
        }

        return result;
    }
}
