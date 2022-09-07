package com.wexalian.common.unchecked;

import java.util.function.Function;

@FunctionalInterface
public interface CheckedFunction<T, R> extends Function<T, R> {
    R applyUnchecked(T t) throws Exception;
    
    @Override
    default R apply(T t) {
        return Unchecked.apply(t, this);
    }
}
