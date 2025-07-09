package com.wexalian.common.gson.adapter;

import com.google.gson.*;
import com.wexalian.nullability.annotations.Nullable;

import java.lang.reflect.Type;

public abstract class AbstractTypeAdapter<T> implements IGsonTypeAdapter, JsonDeserializer<T>, JsonSerializer<T> {
    
    @Nullable
    protected abstract T fromJson(JsonElement json) throws JsonParseException;
    
    protected abstract JsonElement toJson(T src);
    
    @Override
    public final T deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        return fromJson(json);
    }
    
    @Override
    public final JsonElement serialize(T src, Type typeOfSrc, JsonSerializationContext context) {
        return toJson(src);
    }
}

