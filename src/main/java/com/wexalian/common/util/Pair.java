package com.wexalian.common.util;

import com.wexalian.nullability.annotations.Nullable;

public final class Pair<L, R> {
    private final L left;
    private final R right;
    
    private Pair(L left, R right) {
        this.left = left;
        this.right = right;
    }
    
    @Nullable
    public L getLeft() {
        return left;
    }
    
    @Nullable
    public R getRight() {
        return right;
    }
    
    public static <L, R> Pair<L, R> of(@Nullable L left, @Nullable R right) {
        return new Pair<>(left, right);
    }
}
