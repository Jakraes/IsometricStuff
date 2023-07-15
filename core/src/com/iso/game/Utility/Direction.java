package com.iso.game.Utility;

public enum Direction {
    UP(new Vector3(-1, 1, 0)), DOWN(new Vector3(1, -1, 0)),
    LEFT(new Vector3(-1, -1, 0)), RIGHT(new Vector3(1, 1, 0));

    private final Vector3 vector;

    Direction(Vector3 vector) {
        this.vector = vector;
    }

    public Vector3 asVector() {
        return vector;
    }
}
