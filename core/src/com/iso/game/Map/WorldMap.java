package com.iso.game.Map;

import com.iso.game.SimplexNoise;
import com.iso.game.Tiles.*;

import java.util.Random;

public class WorldMap extends AbstractMap {
    public final int CHUNK_WIDTH = 16, CHUNK_DEPTH = 16, CHUNK_HEIGHT = 16;
    private final int WATER_HEIGHT = 5;
    private final Random random;

    public WorldMap(int seed, float step) {
        super(seed, step);
        random = new Random(seed);
    }

    @Override
    public Chunk generateChunk(int x, int y) {
        Chunk result = new Chunk(CHUNK_WIDTH, CHUNK_DEPTH, CHUNK_HEIGHT);

        for (int yTemp = 0; yTemp < CHUNK_DEPTH; yTemp++) {
            for (int xTemp = 0; xTemp < CHUNK_WIDTH; xTemp++) {
                int height = (int) ((SimplexNoise.noise(x * CHUNK_WIDTH * getStep() + xTemp * getStep() + getSeed(), y * CHUNK_DEPTH * getStep() + yTemp * getStep() + getSeed()) + 1) / 2 * CHUNK_HEIGHT);
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
                    if (result.getTile(xTemp, yTemp, zTemp) instanceof TileAir)
                        result.setTile(xTemp, yTemp, zTemp, new TileWater());
                }
            }
        }

        for (int yTemp = 0; yTemp < CHUNK_DEPTH; yTemp++) {
            for (int xTemp = 0; xTemp < CHUNK_WIDTH; xTemp++) {
                for (int zTemp = 0; zTemp < CHUNK_HEIGHT; zTemp++) {
                    if (result.getTile(xTemp, yTemp, zTemp) instanceof TileGrass && zTemp < 15 && zTemp > WATER_HEIGHT + 1) {
                        if (random.nextDouble() > 0.7) result.setTile(xTemp, yTemp, zTemp + 1, new TileTree());
                        break;
                    }
                }
            }
        }


        return result;
    }
}
