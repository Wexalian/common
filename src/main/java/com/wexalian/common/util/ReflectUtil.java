package com.wexalian.common.util;

import com.wexalian.nullability.annotations.Nonnull;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

public final class ReflectUtil {
    private static final Map<Class<?>, Map<String, Field>> classFieldCache = new HashMap<>();

    @Nonnull
    public static <T> T getValueFromField(Object instance, String fieldName) {
        return getValueFromField(instance, fieldName, false);
    }

    @SuppressWarnings("unchecked")
    public static <T> T getValueFromField(Object instance, String fieldName, boolean skipThis) {
        try {
            Class<?> clazz = instance.getClass();
            Field field = getField(skipThis ? clazz.getSuperclass() : clazz, fieldName);
            if (field != null) {
                field.setAccessible(true);
                Object obj = field.get(instance);
                if (obj != null) return (T) obj;
            }
        } catch (IllegalAccessException ignored) {
        }
        throw new IllegalStateException(StringUtil.format("Unable to load field  '{}' from object '{}'", fieldName, instance));
    }

    private static Field getField(Class<?> clazz, String fieldName) {
        try {
            return clazz.getDeclaredField(fieldName);
        } catch (NoSuchFieldException e) {
            Class<?> superClass = clazz.getSuperclass();
            if (superClass != null) {
                return getField(superClass, fieldName);
            }
        }
        return null;
    }
}
