package com.iso.game.Utility;

// Had to make this class because libgdx's Vector3 is based on floats, I only need ints
public class Vector3 {
    public int x, y, z;

    public Vector3(int x, int y, int z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Vector3(Vector3 v) {
        this.x = v.x;
        this.y = v.y;
        this.z = v.z;
    }

    public Vector3 set(int x, int y, int z) {
        this.x = x;
        this.y = y;
        this.z = z;
        return this;
    }

    public Vector3 add(Vector3 v) {
        return this.set(x + v.x, y + v.y, z + v.z);
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Vector3)) return false;
        Vector3 v = (Vector3) obj;
        return v.x == x && v.y == y && v.z == z;
    }
}
