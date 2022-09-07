package com.wexalian.common.gson;

import com.google.gson.*;
import com.wexalian.nullability.annotations.Nonnull;

import java.lang.reflect.Type;
import java.util.function.Function;

public class EasyTypeAdapter<T> implements JsonDeserializer<T>, JsonSerializer<T> {
    
    private final Function<T, String> toString;
    private final Function<String, T> fromString;
    
    private EasyTypeAdapter(Function<T, String> toString, Function<String, T> fromString) {
        this.toString = toString;
        this.fromString = fromString;
    }
    
    @Override
    public T deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        return fromString.apply(json.getAsString());
    }
    
    @Override
    public JsonElement serialize(T src, Type typeOfSrc, JsonSerializationContext context) {
        return new JsonPrimitive(toString.apply(src));
    }
    
    public static <T> EasyTypeAdapter<T> create(@Nonnull Function<T, String> toString, @Nonnull Function<String, T> fromString) {
        return new EasyTypeAdapter<>(toString, fromString);
    }
}

