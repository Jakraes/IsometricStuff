package com.iso.game;

import com.badlogic.gdx.math.Vector3;

public enum Direction {
    UP(new Vector3(0, 0, 1)), DOWN(new Vector3(0, 0, -1)),
    LEFT(new Vector3(-1, 0, 0)), RIGHT(new Vector3(1, 0, 0)),
    BACK(new Vector3(0, -1, 0)), FRONT(new Vector3(0, 1, 0));

    private final Vector3 vector;

    Direction(Vector3 vector) {
        this.vector = vector;
    }

    public Vector3 asVector() {
        return vector;
    }
}
