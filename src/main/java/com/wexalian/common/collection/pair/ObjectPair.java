package com.wexalian.common.collection.pair;

import com.wexalian.nullability.annotations.Nullable;

public final class ObjectPair<L, R> extends Pair<L, R> {
    private final L left;
    private final R right;
    
    ObjectPair(L left, R right) {
        this.left = left;
        this.right = right;
    }
    
    @Nullable
    @Override
    public L getLeft() {
        return left;
    }
    
    @Nullable
    @Override
    public R getRight() {
        return right;
    }
}
