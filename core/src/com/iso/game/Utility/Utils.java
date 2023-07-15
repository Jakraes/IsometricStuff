package com.iso.game.Utility;

public class Utils {
    public static int intSignum(int x) {
        return (x == 0) ? x : x / Math.abs(x);
    }
}
