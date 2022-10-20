package com.wexalian.common.property;

import com.wexalian.nullability.annotations.Nullable;

public class ObjectProperty<T> extends BaseProperty<T> {
    private T value;
    
    public ObjectProperty(@Nullable T value) {
        this.value = value;
    }
    
    @Override
    @Nullable
    public T get() {
        return value;
    }
    
    @Override
    public void set(@Nullable T newValue) {
        T oldValue = value;
        value = newValue;
        setDirty();
        runOnChange(oldValue, newValue);
    }
}
