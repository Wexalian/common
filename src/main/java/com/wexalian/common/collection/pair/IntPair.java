package com.wexalian.common.collection.pair;

import com.wexalian.nullability.annotations.Nullable;

public final class IntPair extends Pair<Integer, Integer> {
    private final int left;
    private final int right;
    
    IntPair(int left, int right) {
        this.left = left;
        this.right = right;
    }
    
    @Nullable
    @Override
    public Integer getLeft() {
        return getLeftInt();
    }
    
    public int getLeftInt() {
        return left;
    }
    
    @Nullable
    @Override
    public Integer getRight() {
        return getRightInt();
    }
    
    public int getRightInt() {
        return right;
    }
}
