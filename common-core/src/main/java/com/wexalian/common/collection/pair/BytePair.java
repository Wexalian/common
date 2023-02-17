package com.wexalian.common.collection.pair;

import com.wexalian.nullability.annotations.Nullable;

public final class BytePair extends Pair<Byte, Byte> {
    private final byte left;
    private final byte right;
    
    BytePair(byte left, byte right) {
        this.left = left;
        this.right = right;
    }
    
    @Nullable
    @Override
    public Byte getLeft() {
        return getLeftByte();
    }
    
    public byte getLeftByte() {
        return left;
    }
    
    @Nullable
    @Override
    public Byte getRight() {
        return getRightByte();
    }
    
    public byte getRightByte() {
        return right;
    }
}
