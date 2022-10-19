package com.wexalian.common.util;

import com.wexalian.nullability.annotations.Nonnull;
import com.wexalian.nullability.annotations.Nullable;

import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

@FunctionalInterface
public interface IOptionalLike<T> {
    @Nullable
    T get();
    
    default boolean isEmpty() {
        return get() == null;
    }
    
    default boolean isPresent() {
        return !isEmpty();
    }
    
    @Nonnull
    default T orElse(@Nonnull T other) {
        T value = get();
        if (value != null && isPresent()) {
            return value;
        }
        return other;
    }
    
    @Nonnull
    default T orElseGet(@Nonnull Supplier<T> supplier) {
        T value = get();
        if (value == null || isEmpty()) {
            return supplier.get();
        }
        return value;
    }
    
    default void ifPresent(@Nonnull Consumer<T> consumer) {
        T value = get();
        if (value == null || isEmpty()) {
            return;
        }
        consumer.accept(value);
    }
    
    default void ifPresentOrElse(@Nonnull Consumer<T> consumer, @Nonnull Runnable runnable) {
        T value = get();
        if (value == null || isEmpty()) {
            runnable.run();
        }
        else consumer.accept(value);
    }
    
    @Nonnull
    default IOptionalLike<T> or(@Nonnull Supplier<? extends IOptionalLike<T>> supplier) {
        T value = get();
        if (value == null || isEmpty()) {
            return supplier.get();
        }
        return () -> value;
    }
    
    @Nonnull
    default IOptionalLike<T> filter(@Nonnull Predicate<T> predicate) {
        T value = get();
        if (value == null || isEmpty() || !predicate.test(value)) {
            return () -> null;
        }
        return () -> value;
    }
    
    @Nonnull
    default <U> IOptionalLike<U> map(@Nonnull Function<T, U> mapper) {
        T value = get();
        if (value == null || isEmpty()) {
            return () -> null;
        }
        return () -> mapper.apply(value);
    }
    
    @Nonnull
    default <U> IOptionalLike<U> flatMap(@Nonnull Function<T, IOptionalLike<U>> mapper) {
        T value = get();
        if (value == null || isEmpty()) {
            return () -> null;
        }
        return mapper.apply(value);
    }
    
    @Nonnull
    default Optional<T> asOptional() {
        return Optional.ofNullable(get());
    }
}
