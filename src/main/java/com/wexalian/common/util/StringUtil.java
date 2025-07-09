package com.wexalian.common.util;

import com.wexalian.common.collection.util.ListUtil;
import com.wexalian.nullability.annotations.Nonnull;
import com.wexalian.nullability.annotations.Nullable;

import java.text.Normalizer;
import java.util.Collection;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class StringUtil {
    private static final Pattern FORMATTING_PATTERN = Pattern.compile("\\{([^}]*)}");

    public static boolean isBlank(@Nullable String string) {
        return string == null || string.isBlank();
    }

    public static boolean isNotBlank(@Nullable String string) {
        return !isBlank(string);
    }

    @Nonnull
    public static String format(@Nonnull String text, @Nonnull Object[] params) {
        String formatted = text;
        for (Object param : params) {
            formatted = formatParam(formatted, param);
        }
        return formatted;
    }

    @Nonnull
    public static String format(@Nonnull String text, @Nonnull Object first, @Nonnull Object... params) {
        return format(formatParam(text, first), params);
    }

    private static String formatParam(String text, Object param) {
        Matcher matcher = FORMATTING_PATTERN.matcher(text);
        if (matcher.find()) {
            return matcher.replaceFirst(r -> !r.group(1)
                                               .isBlank() ? String.format(r.group(1), param) : Matcher.quoteReplacement(param.toString()));
        }
        return text;
    }

    @Nonnull
    public static <T> String join(@Nullable T[] items, @Nonnull String delimiter) {
        return join(items, delimiter, Object::toString);
    }

    @Nonnull
    public static <T> String join(@Nullable Collection<T> items, @Nonnull String delimiter) {
        return join(items, delimiter, Object::toString);
    }

    @Nonnull
    public static <T> String join(@Nullable T[] items, @Nonnull String delimiter, @Nonnull Function<T, String> formatter) {
        return join(ListUtil.nonnull(items), delimiter, formatter);
    }

    @Nonnull
    public static <T> String join(@Nullable Collection<T> items, @Nonnull String delimiter, @Nonnull Function<T, String> formatter) {
        if (items == null || items.isEmpty()) return "";
        return items.stream().map(formatter).collect(Collectors.joining(delimiter));
    }

    @Nonnull
    public static String slug(@Nonnull String text) {
        return Normalizer.normalize(text, Normalizer.Form.NFKD)
                         .replaceAll("\\p{M}", "")
                         .toLowerCase()
                         .replace(' ', '_')
                         .replace('-', '_')
                         .replaceAll("[^a-zA-Z_\\d]+", "")
                         .replaceAll("_{2,}", "_");
    }
}
