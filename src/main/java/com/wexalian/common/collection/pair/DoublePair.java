package com.wexalian.common.collection.pair;

import com.wexalian.nullability.annotations.Nullable;

public final class DoublePair extends Pair<Double, Double> {
    private final double left;
    private final double right;
    
    DoublePair(double left, double right) {
        this.left = left;
        this.right = right;
    }
    
    @Nullable
    @Override
    public Double getLeft() {
        return getLeftDouble();
    }
    
    public double getLeftDouble() {
        return left;
    }
    
    @Nullable
    @Override
    public Double getRight() {
        return getRightDouble();
    }
    
    public double getRightDouble() {
        return right;
    }
}
