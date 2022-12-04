package com.wexalian.common.collection.pair;

import com.wexalian.nullability.annotations.Nullable;

public final class CharPair extends Pair<Character, Character> {
    private final char left;
    private final char right;
    
    CharPair(char left, char right) {
        this.left = left;
        this.right = right;
    }
    
    @Nullable
    @Override
    public Character getLeft() {
        return getLeftChar();
    }
    
    public char getLeftChar() {
        return left;
    }
    
    @Nullable
    @Override
    public Character getRight() {
        return getRightChar();
    }
    
    public char getRightChar() {
        return right;
    }
}
