package com.wexalian.common.unchecked;

import java.util.function.Consumer;

@Deprecated()
@FunctionalInterface
public interface CheckedConsumer<T> extends Consumer<T> {
    void acceptUnchecked(T t) throws Exception;

    @Override
    default void accept(T t) {
        Unchecked.accept(t, this);
    }
}
