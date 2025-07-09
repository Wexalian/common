package com.wexalian.common.gson.adapter;

import com.wexalian.common.gson.TypeAdapterManager;
import com.wexalian.nullability.annotations.Nonnull;

import java.lang.reflect.Type;

public interface IGsonTypeAdapter {
    @Nonnull
    Type getType();
    
    interface Hierarchy extends IGsonTypeAdapter {
        @Nonnull
        Class<?> getType();
    }
    
    interface Registry {
        void register(@Nonnull TypeAdapterManager.Adapters adapters);
    }
}
