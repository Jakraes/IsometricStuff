package com.iso.game.Map;

import com.iso.game.Tiles.*;
import com.iso.game.Utility.SimplexNoise;

public class WorldMap extends AbstractMap {
    public final int CHUNK_WIDTH = 16, CHUNK_DEPTH = 16, CHUNK_HEIGHT = 32;
    private final int WATER_HEIGHT = CHUNK_HEIGHT / 4;
    private final int OCTAVES = 3;

    public WorldMap(int seed, float step) {
        super(seed, step);
    }

    // TODO the code is a bit ugly and unorganized, I'm sure it can get smaller and more efficient
    @Override
    public Chunk generateChunk(int x, int y) {
        Chunk result = new Chunk(CHUNK_WIDTH, CHUNK_DEPTH, CHUNK_HEIGHT);

        for (int yTemp = 0; yTemp < CHUNK_DEPTH; yTemp++) {
            for (int xTemp = 0; xTemp < CHUNK_WIDTH; xTemp++) {
                double d = (SimplexNoise.noiseWithOctaves(x * CHUNK_WIDTH + xTemp, y * CHUNK_DEPTH + yTemp, getSeed(), OCTAVES, getStep()) + 1) / 2 * (CHUNK_HEIGHT - 2);
                int height = (int) d;
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
                        double d = (SimplexNoise.noiseWithOctaves(x * CHUNK_WIDTH + xTemp, y * CHUNK_DEPTH + yTemp, getSeed() * 2, OCTAVES, getStep()) + 1) / 2;
                        if (d > 0.4) {
                            d = ((SimplexNoise.noiseWithOctaves(x * CHUNK_WIDTH + xTemp, y * CHUNK_DEPTH + yTemp, getSeed() * 2, OCTAVES, 1.0f) + 1) / 2);
                            if (d > 0.7)
                                result.setTile(xTemp, yTemp, zTemp + 1, new TileTree());
                        }
                        break;
                    }
                }
            }
        }

        return result;
    }
}
