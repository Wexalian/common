package com.wexalian.common.property;

import com.wexalian.nullability.annotations.Nullable;

public class StringProperty extends BaseProperty<String> {
    private String value;
    
    public StringProperty(@Nullable String value) {
        this.value = value;
    }
    
    @Override
    @Nullable
    public String get() {
        return value;
    }
    
    @Override
    public void set(String newValue) {
        String oldValue = value;
        value = newValue;
        setDirty();
        runOnChange(oldValue, newValue);
    }
    
    @Override
    public boolean isEmpty() {
        return value == null || value.isBlank();
    }
}
