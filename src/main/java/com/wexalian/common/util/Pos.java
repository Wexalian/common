package com.wexalian.common.util;

public record Pos(float x, float y, float z) {
    public Pos(float x, float y) {
        this(x, y, 0);
    }
}
