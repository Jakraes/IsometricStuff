package com.iso.game.Map;

import com.badlogic.gdx.math.Vector2;
import com.iso.game.Tiles.AbstractTile;

import java.util.HashMap;
import java.util.Map;

public abstract class AbstractMap {
    public final int CHUNK_WIDTH = 16, CHUNK_DEPTH = 16, CHUNK_HEIGHT = 32;
    private final int seed;
    private final float step;
    private final Map<Vector2, Chunk> chunks;

    public AbstractMap(int seed, float step) {
        this.seed = seed;
        this.step = step;
        chunks = new HashMap<>();
    }

    public abstract Chunk generateChunk(int x, int y);

    public Chunk getChunk(int x, int y) {
        Vector2 key = new Vector2(x, y);
        if (!chunks.containsKey(key)) chunks.put(key, generateChunk(x, y));
        return chunks.get(key);
    }

    public AbstractTile getTile(int x, int y, int z) {
        int chunkX = (x < 0.0f) ? (x + 1) / CHUNK_WIDTH - 1 : x / CHUNK_WIDTH;
        int chunkY = (y < 0.0f) ? (y + 1) / CHUNK_DEPTH - 1 : y / CHUNK_DEPTH;

        int tileX = x - chunkX * CHUNK_WIDTH;
        int tileY = y - chunkY * CHUNK_DEPTH;

        return getChunk(chunkX, chunkY).getTile(tileX, tileY, z);
    }

    public void setTile(int x, int y, int z, AbstractTile t) {
        int chunkX = (x < 0.0f) ? (x + 1) / CHUNK_WIDTH - 1 : x / CHUNK_WIDTH;
        int chunkY = (y < 0.0f) ? (y + 1) / CHUNK_DEPTH - 1 : y / CHUNK_DEPTH;

        int tileX = x - chunkX * CHUNK_WIDTH;
        int tileY = y - chunkY * CHUNK_DEPTH;

        getChunk(chunkX, chunkY).setTile(tileX, tileY, z, t);
    }

    public int getSeed() {
        return seed;
    }

    public float getStep() {
        return step;
    }
}
