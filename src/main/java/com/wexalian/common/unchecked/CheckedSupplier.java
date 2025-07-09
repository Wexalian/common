package com.wexalian.common.unchecked;

import java.util.function.Supplier;

@Deprecated()
@FunctionalInterface
public interface CheckedSupplier<T> extends Supplier<T> {
    @Override
    default T get() {
        return Unchecked.get(this);
    }

    T getUnchecked() throws Exception;
}
