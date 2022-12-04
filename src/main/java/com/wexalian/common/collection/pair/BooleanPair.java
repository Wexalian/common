package com.wexalian.common.collection.pair;

import com.wexalian.nullability.annotations.Nullable;

public final class BooleanPair extends Pair<Boolean, Boolean> {
    private final boolean left;
    private final boolean right;
    
    BooleanPair(boolean left, boolean right) {
        this.left = left;
        this.right = right;
    }
    
    @Nullable
    @Override
    public Boolean getLeft() {
        return getLeftBoolean();
    }
    
    public boolean getLeftBoolean() {
        return left;
    }
    
    @Nullable
    @Override
    public Boolean getRight() {
        return getRightBoolean();
    }
    
    public boolean getRightBoolean() {
        return right;
    }
}
