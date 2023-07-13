package com.iso.game.Map;

import com.badlogic.gdx.math.MathUtils;
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
        int chunkX = (x < 0.0f) ? x / CHUNK_WIDTH - 1 : x / CHUNK_WIDTH;
        int chunkY = (y < 0.0f) ? y / CHUNK_DEPTH - 1 : y / CHUNK_DEPTH;
        //int chunkX = x / CHUNK_WIDTH;
        //int chunkY = y / CHUNK_DEPTH;
        System.out.println(chunkX + " " + chunkY + " " + (x - chunkX * CHUNK_WIDTH) + " " + (y - chunkY * CHUNK_DEPTH));
        return getChunk(chunkX, chunkY).getTile(MathUtils.clamp(x - chunkX * CHUNK_WIDTH, 0, CHUNK_WIDTH - 1), MathUtils.clamp(y - chunkY * CHUNK_DEPTH - 1, 0, CHUNK_DEPTH), z);
        //return getChunk(chunkX, chunkY).getTile(x - chunkX * CHUNK_WIDTH, y - chunkY * CHUNK_DEPTH, z);
    }

    public void setTile(int x, int y, int z, AbstractTile t) {
        int chunkX = (x < 0.0f) ? x / CHUNK_WIDTH - 1 : x / CHUNK_WIDTH;
        int chunkY = (y < 0.0f) ? y / CHUNK_DEPTH - 1 : y / CHUNK_DEPTH;
        //int chunkX = x / CHUNK_WIDTH;
        //int chunkY = y / CHUNK_DEPTH;
        System.out.println(chunkX + " " + chunkY + " " + (x - chunkX * CHUNK_WIDTH) + " " + (y - chunkY * CHUNK_DEPTH));
        getChunk(chunkX, chunkY).setTile(MathUtils.clamp(x - chunkX * CHUNK_WIDTH, 0, CHUNK_WIDTH - 1), MathUtils.clamp(y - chunkY * CHUNK_DEPTH, 0, CHUNK_DEPTH), z, t);
        //getChunk(chunkX, chunkY).setTile(x - chunkX * CHUNK_WIDTH, y - chunkY * CHUNK_DEPTH, z, t);
    }

    public int getSeed() {
        return seed;
    }

    public float getStep() {
        return step;
    }
}
