package com.wexalian.common.collection.pair;

import com.wexalian.nullability.annotations.Nullable;

public final class ShortPair extends Pair<Short, Short> {
    private final short left;
    private final short right;
    
    ShortPair(short left, short right) {
        this.left = left;
        this.right = right;
    }
    
    @Nullable
    @Override
    public Short getLeft() {
        return getLeftShort();
    }
    
    public short getLeftShort() {
        return left;
    }
    
    @Nullable
    @Override
    public Short getRight() {
        return getRightShort();
    }
    
    public short getRightShort() {
        return right;
    }
}
