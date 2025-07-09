package com.wexalian.common.unchecked;

import java.util.function.BiFunction;

@Deprecated()
@FunctionalInterface
public interface CheckedBiFunction<T, U, R> extends BiFunction<T, U, R> {
    R applyUnchecked(T t, U u) throws Exception;

    @Override
    default R apply(T t, U u) {
        return Unchecked.apply(t, u, this);
    }
}
