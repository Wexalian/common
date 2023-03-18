package com.wexalian.common.util.misc;

public record Pos(float x, float y, float z) {
    public Pos(float x, float y) {
        this(x, y, 0);
    }
}
