package com.vitungermann.terminalbuffer.helper;

public class MathHelp {
    public static int clamp(int n, int max) {
        return Math.max(0, Math.min(max - 1, n));
    }
}
