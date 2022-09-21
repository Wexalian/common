package com.wexalian.common.util;

import com.wexalian.nullability.annotations.Nonnull;
import com.wexalian.nullability.annotations.Nullable;

import java.text.Normalizer;
import java.util.Collection;
import java.util.function.Function;
import java.util.stream.Collectors;

public class StringUtil {
    public static boolean isNull(@Nullable String string) {
        return string == null;
    }
    
    public static boolean isBlank(@Nonnull String string) {
        return string.isBlank();
    }
    
    public static boolean isNullOrBlank(@Nullable String string) {
        return isNull(string) || isBlank(string);
    }
    
    @Nonnull
    public static String format(@Nonnull String text, @Nonnull Object... params) {
        String formatted = text;
        for (Object param : params) {
            formatted = formatted.replaceFirst("\\{}", param.toString());
        }
        return formatted;
    }
    
    @Nonnull
    public static <T> String join(@Nullable Collection<T> items, @Nonnull String delimiter) {
        return join(items, delimiter, Object::toString);
    }
    
    @Nonnull
    public static <T> String join(@Nullable Collection<T> items, @Nonnull String delimiter, @Nonnull Function<T, String> formatter) {
        if (items == null || items.isEmpty()) return "";
        return items.stream().map(formatter).collect(Collectors.joining(delimiter));
    }
    
    @Nonnull
    public static String slug(@Nonnull String title) {
        String normalized = Normalizer.normalize(title.toLowerCase(), Normalizer.Form.NFD);
        return normalized.toLowerCase()
                         .replaceAll("[\\p{InCombiningDiacriticalMarks}\\p{IsLm}\\p{IsSk}]+", "")
                         .replace(' ', '_')
                         .replace('-', '_')
                         .replaceAll("[^a-zA-Z_\\d]+", "")
                         .replaceAll("_{2,}", "_");
    }
}
