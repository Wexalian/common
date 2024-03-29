package com.wexalian.common.gson;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.reflect.TypeToken;
import com.wexalian.common.util.Lazy;
import com.wexalian.common.util.StringUtil;
import com.wexalian.nullability.annotations.Nonnull;
import com.wexalian.nullability.annotations.Nullable;

import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.logging.Level;
import java.util.logging.Logger;

public final class GsonUtil {
    private static final Logger LOGGER = Logger.getLogger("com.wexalian.common.gson");
    
    private static final Lazy<Gson> DEFAULT_GSON = new Lazy<>(() -> new GsonBuilder().setPrettyPrinting().serializeNulls().create());
    
    @Nullable
    private static Gson customGson = null;
    
    public static void setCustomGson(@Nonnull Gson gson) {
        customGson = gson;
    }
    
    @Nullable
    public static <T> T fromJsonString(@Nonnull String json, @Nonnull TypeToken<T> token) {
        try {
            return DEFAULT_GSON.getOrDefault(customGson).fromJson(json, token.getType());
        }
        catch (Exception e) {
            String message = StringUtil.format("Error parsing json for type '{}', json: '{}'", token.getRawType().getName(), json);
            LOGGER.log(Level.SEVERE, message, e);
        }
        return null;
    }
    
    @Nonnull
    public static <T> String toJsonString(@Nonnull T object, @Nonnull TypeToken<T> token) {
        return DEFAULT_GSON.getOrDefault(customGson).toJson(object, token.getType());
    }
    
    @Nullable
    public static <T> T fromJsonElement(@Nonnull JsonElement json, @Nonnull Type type) {
        try {
            return DEFAULT_GSON.getOrDefault(customGson).fromJson(json, type);
        }
        catch (Exception e) {
            String message = StringUtil.format("Error parsing json for type '{}'", type.getTypeName());
            LOGGER.log(Level.SEVERE, message, e);
        }
        return null;
    }
    
    @Nonnull
    public static <T> JsonElement toJsonElement(@Nonnull T object, @Nonnull Type type) {
        return DEFAULT_GSON.getOrDefault(customGson).toJsonTree(object, type);
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
            return DEFAULT_GSON.getOrDefault(customGson).fromJson(content, token.getType());
        }
        catch (Exception e) {
            String message = StringUtil.format("Error parsing json file '{}' for type '{}'", path, token.getRawType().getName());
            LOGGER.log(Level.SEVERE, message, e);
        }
        return null;
    }
    
    public static <T> void toJsonFile(@Nonnull T object, @Nonnull Path path, @Nonnull TypeToken<T> token) {
        try {
            String content = DEFAULT_GSON.getOrDefault(customGson).toJson(object, token.getType());
            Files.writeString(path, content);
        }
        catch (Exception e) {
            String message = StringUtil.format("Error writing json file '{}' for type '{}'", path, token.getRawType().getName());
            LOGGER.log(Level.SEVERE, message, e);
        }
    }
    
    public static void toJsonFile(@Nonnull JsonElement json, @Nonnull Path path) {
        try {
            String content = DEFAULT_GSON.getOrDefault(customGson).toJson(json);
            Files.writeString(path, content);
        }
        catch (Exception e) {
            String message = StringUtil.format("Error writing json file '{}'", path);
            LOGGER.log(Level.SEVERE, message, e);
        }
    }
}
