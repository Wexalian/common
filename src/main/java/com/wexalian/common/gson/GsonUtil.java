package com.wexalian.common.gson;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import com.wexalian.common.collection.util.ListUtil;
import com.wexalian.common.util.StringUtil;
import com.wexalian.nullability.annotations.Nonnull;
import com.wexalian.nullability.annotations.Nullable;

import java.io.IOException;
import java.lang.reflect.Array;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.function.Function;
import java.util.logging.Level;
import java.util.logging.Logger;

public class GsonUtil {
    private static final Logger LOGGER = Logger.getLogger("com.wexalian.common.gson");
    
    private static Gson GSON = newBuilder(true).create();
    
    @Nonnull
    public static GsonBuilder newBuilder(boolean addAdapters) {
        GsonBuilder builder = new GsonBuilder().setPrettyPrinting().serializeNulls();
        if (addAdapters) TypeAdapterManager.addTypeAdapters(builder);
        addGsonOpens();
        return builder;
    }
    
    private static void addGsonOpens() {
        Class<?> callerClass = StackWalker.getInstance(StackWalker.Option.RETAIN_CLASS_REFERENCE).getCallerClass();
        callerClass.getModule().addOpens(callerClass.getPackageName(), Gson.class.getModule());
    }
    
    /* object from json */
    
    @Nullable
    public static <T> T fromJson(String json, Class<T> clazz) throws JsonParseException {
        return fromJson(json, (Type) clazz);
    }
    
    @Nullable
    public static <T> T fromJson(String json, TypeToken<T> token) {
        return fromJson(json, token.getType());
    }
    
    @Nullable
    @SuppressWarnings("unchecked")
    public static <T> T fromJson(String json, Type type) {
        try {
            if (type instanceof Class<?> clazz) {
                return GSON.fromJson(json, (Class<T>) clazz);
            }
            return GSON.fromJson(json, type);
        } catch (Exception e) {
            String message = StringUtil.format("Error deserializing json for type '{}'", type.getTypeName());
            LOGGER.log(Level.SEVERE, message, e);
        }
        return null;
    }
    
    /* object to json */
    
    @Nonnull
    public static String toJson(Object object) {
        if (object == null) {
            return fromJsonTree(JsonNull.INSTANCE);
        }
        return toJson(object, object.getClass());
    }
    
    @Nonnull
    public static String toJson(Object object, TypeToken<?> token) {
        return toJson(object, token.getType());
    }
    
    @Nonnull
    public static String toJson(Object object, Type type) {
        try {
            return GSON.toJson(object, type);
        } catch (Exception e) {
            String message = StringUtil.format("Error serializing json for type '{}'", type.getTypeName());
            LOGGER.log(Level.SEVERE, message, e);
        }
        return "";
    }
    
    /* object from json tree */
    
    @Nullable
    public static <T> T fromJsonTree(JsonElement element, Class<T> clazz) {
        return fromJsonTree(element, (Type) clazz);
    }
    
    @Nullable
    public static <T> T fromJsonTree(JsonElement element, TypeToken<T> token) {
        return fromJsonTree(element, token.getType());
    }
    
    @Nullable
    @SuppressWarnings("unchecked")
    public static <T> T fromJsonTree(JsonElement element, Type type) {
        try {
            if (type instanceof Class<?> clazz) {
                return GSON.fromJson(element, (Class<T>) clazz);
            }
            return GSON.fromJson(element, type);
        } catch (Exception e) {
            String message = StringUtil.format("Error deserializing json tree for type '{}'", type.getTypeName());
            LOGGER.log(Level.SEVERE, message, e);
        }
        return null;
    }
    
    @Nonnull
    public static String fromJsonTree(JsonElement element) {
        try {
            return GSON.toJson(element);
        } catch (Exception e) {
            String message = StringUtil.format("Error serializing json for element '{}'", element);
            LOGGER.log(Level.SEVERE, message, e);
        }
        return "";
    }
    
    /* object to json tree */
    
    @Nonnull
    public static JsonElement toJsonTree(Object object) {
        if (object == null) {
            return JsonNull.INSTANCE;
        }
        return toJsonTree(object, object.getClass());
    }
    
    @Nonnull
    public static JsonElement toJsonTree(Object object, TypeToken<?> token) {
        return toJsonTree(object, token.getType());
    }
    
    @Nonnull
    public static JsonElement toJsonTree(Object object, Type type) {
        try {
            return GSON.toJsonTree(object, type);
        } catch (Exception e) {
            String message = StringUtil.format("Error serializing json for type '{}'", type.getTypeName());
            LOGGER.log(Level.SEVERE, message, e);
        }
        return JsonNull.INSTANCE;
    }
    
    @Nonnull
    public static JsonElement toJsonTree(String json) {
        try {
            return JsonParser.parseString(json);
        } catch (Exception e) {
            String message = StringUtil.format("Error parsing json to element '{}': ", json);
            LOGGER.log(Level.SEVERE, message, e);
        }
        return JsonNull.INSTANCE;
    }
    
    /* object from json file */
    
    @Nullable
    public static <T> T fromJsonFile(Path path, Class<T> clazz) {
        return fromJsonFile(path, (Type) clazz);
    }
    
    @Nullable
    public static <T> T fromJsonFile(Path path, TypeToken<T> token) {
        return fromJsonFile(path, token.getType());
    }
    
    @Nullable
    public static <T> T fromJsonFile(Path path, Type type) {
        try {
            String json = Files.readString(path);
            return fromJson(json, type);
        } catch (IOException e) {
            String message = StringUtil.format("Error reading json file '{}' for type '{}'", path, type.getTypeName());
            LOGGER.log(Level.SEVERE, message, e);
        }
        return null;
    }
    
    @Nonnull
    public static JsonElement fromJsonFile(Path path) {
        try {
            String json = Files.readString(path);
            return toJsonTree(json);
        } catch (IOException e) {
            String message = StringUtil.format("Error reading json file '{}'", path);
            LOGGER.log(Level.SEVERE, message, e);
        }
        return JsonNull.INSTANCE;
    }
    
    /* object to json file */
    
    @Nullable
    public static Path toJsonFile(Path path, Object object) {
        return toJsonFile(path, object, object.getClass());
    }
    
    @Nullable
    public static Path toJsonFile(Path path, Object object, TypeToken<?> token) {
        return toJsonFile(path, object, token.getType());
    }
    
    @Nullable
    public static Path toJsonFile(Path path, Object object, Type type) {
        try {
            String content = toJson(object, type);
            return Files.writeString(path, content);
        } catch (IOException e) {
            String message = StringUtil.format("Error writing json file '{}' for type '{}'", path, type.getTypeName());
            LOGGER.log(Level.SEVERE, message, e);
        }
        return null;
    }
    
    @Nullable
    public static Path toJsonFile(Path path, JsonElement element) {
        try {
            String content = fromJsonTree(element);
            return Files.writeString(path, content);
        } catch (IOException e) {
            String message = StringUtil.format("Error writing json file '{}'", path);
            LOGGER.log(Level.SEVERE, message, e);
        }
        return null;
    }
    
    /* json element to array */
    
    @Nonnull
    public static String[] toArray(JsonElement element) {
        return toArray(element, String.class, JsonElement::getAsString);
    }
    
    @Nonnull
    public static <T> T[] toArray(JsonElement element, Class<T> clazz) {
        return toArray(element, clazz, json -> fromJsonTree(json, clazz));
    }
    
    @SuppressWarnings("unchecked")
    public static <T> T[] toArray(JsonElement element, Class<T> clazz, Function<JsonElement, T> mapping) {
        if (element == null || element.isJsonNull()) {
            return (T[]) Array.newInstance(clazz, 0);
        }
        if (element.isJsonPrimitive() || element.isJsonObject()) {
            T[] array = (T[]) Array.newInstance(clazz, 1);
            array[0] = mapping.apply(element);
            return array;
        }
        return (T[]) ListUtil.mapped(mapping, element.getAsJsonArray()).toArray();
    }

    /*

    Object fromJson(String,Class){}
    Object fromJson(String,TypeToken){}
    Object fromJson(String,Type){}

    String toJson(Object){}
    String toJson(Object,TypeToken){}
    String toJson(Object,Type){}

    Object fromJsonTree(JsonElement,Class){}
    Object fromJsonTree(JsonElement,TypeToken){}
    Object fromJsonTree(JsonElement,Type){}
    String fromJsonTree(JsonElement){}

    JsonElement toJsonTree(Object){}
    JsonElement toJsonTree(Object,TypeToken){}
    JsonElement toJsonTree(Object,Type){}
    JsonElement toJsonTree(String){}

    Object fromJsonFile(Path,Class)
    Object fromJsonFile(Path,TypeToken){}
    Object fromJsonFile(Path,Type){}
    JsonElement fromJsonFile(Path)

    Path toJsonFile(Path,Object){}
    Path toJsonFile(Path,Object,TypeToken){}
    Path toJsonFile(Path,Object,Type){}
    Path toJsonFile(Path,JsonElement){}

    String[] toArray(JsonElement){}
    String[] toArray(JsonElement,Class){}
    Object[] toArray(JsonElement,Class,Function){}

    */
}