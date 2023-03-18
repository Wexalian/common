package com.wexalian.common.util.misc;

import com.wexalian.nullability.annotations.Nonnull;
import com.wexalian.nullability.annotations.Nullable;
import com.wexalian.nullability.function.NonnullSupplier;

public final class Lazy<T> {
    private final NonnullSupplier<T> constructor;
    
    @Nullable
    private T value = null;
    
    public Lazy(@Nonnull NonnullSupplier<T> constructor) {
        this.constructor = constructor;
    }
    
    @Nonnull
    public T get() {
        if (value == null) {
            value = constructor.get();
        }
        return value;
    }
    
    public T getOrDefault(@Nullable T otherValue) {
        if (otherValue == null) {
            return get();
        }
        return otherValue;
    }
}
