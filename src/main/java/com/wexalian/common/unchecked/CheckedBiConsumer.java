package com.wexalian.common.unchecked;

import java.util.function.BiConsumer;

@Deprecated()
@FunctionalInterface
public interface CheckedBiConsumer<T, U> extends BiConsumer<T, U> {
    void acceptUnchecked(T t, U u) throws Exception;

    @Override
    default void accept(T t, U u) {
        Unchecked.accept(t, u, this);
    }
}
