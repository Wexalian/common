package com.wexalian.common.collection.pair;

import com.wexalian.nullability.annotations.Nullable;

public final class FloatPair extends Pair<Float, Float> {
    private final float left;
    private final float right;
    
    FloatPair(float left, float right) {
        this.left = left;
        this.right = right;
    }
    
    @Nullable
    @Override
    public Float getLeft() {
        return getLeftFloat();
    }
    
    public float getLeftFloat() {
        return left;
    }
    
    @Nullable
    @Override
    public Float getRight() {
        return getRightFloat();
    }
    
    public float getRightFloat() {
        return right;
    }
}
