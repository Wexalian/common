package com.wexalian.common.collection.pair;

import com.wexalian.nullability.annotations.Nullable;

public abstract class Pair<L, R> {
    @Nullable
    public abstract L getLeft();
    
    @Nullable
    public abstract R getRight();
    
    public static <L, R> ObjectPair<L, R> of(@Nullable L left, @Nullable R right) {
        return new ObjectPair<>(left, right);
    }
    
    public static BytePair of(@Nullable byte left, @Nullable byte right) {
        return new BytePair(left, right);
    }
    
    public static CharPair of(@Nullable char left, @Nullable char right) {
        return new CharPair(left, right);
    }
    
    public static ShortPair of(@Nullable short left, @Nullable short right) {
        return new ShortPair(left, right);
    }
    
    public static IntPair of(@Nullable int left, @Nullable int right) {
        return new IntPair(left, right);
    }
    
    public static FloatPair of(@Nullable float left, @Nullable float right) {
        return new FloatPair(left, right);
    }
    
    public static DoublePair of(@Nullable double left, @Nullable double right) {
        return new DoublePair(left, right);
    }
    
    public static BooleanPair of(@Nullable boolean left, @Nullable boolean right) {
        return new BooleanPair(left, right);
    }
}
