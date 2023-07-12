package com.iso.game.Map;

import com.badlogic.gdx.math.Vector2;

import java.util.HashMap;
import java.util.Map;

public abstract class AbstractMap {
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

    public int getSeed() {
        return seed;
    }

    public float getStep() {
        return step;
    }
}
