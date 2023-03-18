package com.wexalian.common.property;

import com.wexalian.common.util.misc.IOptionalLike;
import com.wexalian.nullability.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Predicate;

public abstract class BaseProperty<T> implements IOptionalLike<T> {
    private final List<Listener<T>> listeners = new ArrayList<>();
    
    private boolean dirty;
    
    public abstract void set(@Nullable T value);
    
    public final boolean isDirty() {
        return dirty;
    }
    
    protected final void setDirty() {
        this.dirty = true;
    }
    
    //listeners
    
    public final void addListener(Listener<T> listener) {
        listeners.add(listener);
    }
    
    public final void addListener(Runnable runnable) {
        listeners.add((p, o, n) -> runnable.run());
    }
    
    public final void addListener(Consumer<T> consumer) {
        listeners.add((p, o, n) -> consumer.accept(n));
    }
    
    protected final void runOnChange(T oldValue, T newValue) {
        listeners.forEach(l -> l.onChange(this, oldValue, newValue));
    }
    
    //utility
    public boolean test(Predicate<T> predicate) {
        return predicate.test(get());
    }
    
    public boolean valueEquals(@Nullable T other) {
        return Objects.equals(get(), other);
    }
    
    //base-java
    @Override
    public boolean equals(@Nullable Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BaseProperty<?> that = (BaseProperty<?>) o;
        return get() == that.get();
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(get());
    }
    
    @Override
    public String toString() {
        return map(Objects::toString).orElseGet(super::toString);
    }
    
    @FunctionalInterface
    public interface Listener<T> {
        void onChange(BaseProperty<T> property, T old, T newValue);
    }
}
