package com.wexalian.common.property;

import com.wexalian.common.collection.wrapper.ListWrapper;
import com.wexalian.common.util.StringUtil;
import com.wexalian.nullability.annotations.Nonnull;
import com.wexalian.nullability.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class ListProperty<T> extends BaseProperty<List<T>> implements ListWrapper<T> {
    private final List<T> values;
    
    public ListProperty(@Nullable List<T> values) {
        if (values == null) {
            values = new ArrayList<>();
        }
        this.values = values;
    }
    
    //list-methods
    @Nullable
    @Override
    public List<T> get() {
        return values;
    }
    
    @Override
    public boolean isEmpty() {
        return super.isEmpty();
    }
    
    @Override
    public void set(@Nonnull List<T> newValues) {
        List<T> oldValues = List.copyOf(values);
        values.clear();
        if (newValues != null) {
            values.addAll(newValues);
        }
        setDirty();
        runOnChange(oldValues, newValues);
    }
    
    @Nullable
    @Override
    public T set(int index, @Nonnull T value) {
        T previous = values.set(index, value);
        setDirty();
        return previous;
    }
    
    @Override
    public boolean add(@Nonnull T value) {
        values.add(value);
        setDirty();
        return true;
    }
    
    @Override
    public void add(int index, @Nonnull T value) {
        values.add(index, value);
        setDirty();
    }
    
    @Override
    public boolean remove(@Nonnull Object value) {
        boolean success = values.remove(value);
        if (success) {
            setDirty();
        }
        return success;
    }
    
    @Nullable
    @Override
    public T remove(int index) {
        T value = values.remove(index);
        if (value != null) {
            setDirty();
        }
        return value;
    }
    
    //utility methods
    @Nonnull
    public <R> ListProperty<R> mapValues(@Nonnull Function<T, R> mapper) {
        return new ListProperty<>(values.stream().map(mapper).toList());
    }
    
    @Nonnull
    public String join(@Nonnull String delimiter) {
        return join(delimiter, Object::toString);
    }
    
    @Nonnull
    public String join(@Nonnull String delimiter, Function<T, String> toStringFunc) {
        return StringUtil.join(this, delimiter, toStringFunc);
    }
}
