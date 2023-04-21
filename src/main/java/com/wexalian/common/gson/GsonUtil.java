package com.wexalian.common.gson;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import com.wexalian.common.util.StringUtil;
import com.wexalian.nullability.annotations.Nonnull;
import com.wexalian.nullability.annotations.Nullable;

import java.lang.reflect.Array;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Instant;
import java.util.function.Function;
import java.util.logging.Level;
import java.util.logging.Logger;

public final class GsonUtil {
    private static final Logger LOGGER = Logger.getLogger("com.wexalian.common.gson");
    
    private static Gson GSON = new GsonBuilder().setPrettyPrinting()
                                                .serializeNulls()
                                                .registerTypeAdapter(Instant.class, EasyTypeAdapter.create(Instant::toString, Instant::parse))
                                                .create();
    
    @Nullable
    public static <T> T fromJsonString(@Nonnull String json, @Nonnull TypeToken<T> token) {
        try {
            return GSON.fromJson(json, token.getType());
        }
        catch (Exception e) {
            String message = StringUtil.format("Error parsing json for type '{}', json: '{}'", token.getRawType().getName(), json);
            LOGGER.log(Level.SEVERE, message, e);
        }
        return null;
    }
    
    @Nonnull
    public static <T> String toJsonString(@Nonnull T object, @Nonnull TypeToken<T> token) {
        return GSON.toJson(object, token.getType());
    }
    
    @Nullable
    public static <T> T fromJsonElement(@Nonnull JsonElement json, @Nonnull Type type) {
        try {
            return GSON.fromJson(json, type);
        }
        catch (Exception e) {
            String message = StringUtil.format("Error parsing json for type '{}'", type.getTypeName());
            LOGGER.log(Level.SEVERE, message, e);
        }
        return null;
    }
    
    @Nonnull
    public static <T> JsonElement toJsonElement(@Nonnull T object, @Nonnull Type type) {
        return GSON.toJsonTree(object, type);
    }
    
    @Nonnull
    public static <T> JsonElement toJsonElement(@Nonnull String json) {
        return JsonParser.parseString(json);
    }
    
    @Nullable
    public static <T> T fromJsonFile(@Nonnull Path path, @Nonnull TypeToken<T> token) {
        try {
            if (Files.size(path) == 0) {
                String message = StringUtil.format("File '{}' has a size of 0 bytes, this is probably an error", path.toString());
                LOGGER.log(Level.SEVERE, message, new RuntimeException("File is 0 bytes"));
                return null;
            }
            
            String content = Files.readString(path);
            return GSON.fromJson(content, token.getType());
        }
        catch (Exception e) {
            String message = StringUtil.format("Error parsing json file '{}' for type '{}'", path, token.getRawType().getName());
            LOGGER.log(Level.SEVERE, message, e);
        }
        return null;
    }
    
    public static <T> void toJsonFile(@Nonnull T object, @Nonnull Path path, @Nonnull TypeToken<T> token) {
        try {
            String content = GSON.toJson(object, token.getType());
            Files.writeString(path, content);
        }
        catch (Exception e) {
            String message = StringUtil.format("Error writing json file '{}' for type '{}'", path, token.getRawType().getName());
            LOGGER.log(Level.SEVERE, message, e);
        }
    }
    
    public static void toJsonFile(@Nonnull JsonElement json, @Nonnull Path path) {
        try {
            String content = GSON.toJson(json);
            Files.writeString(path, content);
        }
        catch (Exception e) {
            String message = StringUtil.format("Error writing json file '{}'", path);
            LOGGER.log(Level.SEVERE, message, e);
        }
    }
    
    public static void registerTypeAdapter(Type type, Object adapter) {
        GSON = GSON.newBuilder().registerTypeAdapter(type, adapter).create();
    }
    
    public static String[] toArray(JsonElement jsonElement) {
        return toArray(jsonElement, String.class, JsonElement::getAsString);
    }
    
    @SuppressWarnings("unchecked")
    public static <T> T[] toArray(JsonElement json, Class<T> clazz, Function<JsonElement, T> mapping) {
        if (json.isJsonArray()) {
            return (T[]) json.getAsJsonArray().asList().stream().map(mapping).toArray();
        }
        if (json.isJsonNull()) {
            return (T[]) Array.newInstance(clazz, 0);
        }
        T[] array = (T[]) Array.newInstance(clazz, 1);
        array[0] = mapping.apply(json);
        return array;
    }
}
