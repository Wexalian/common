package com.wexalian.common.util;

import com.wexalian.nullability.annotations.Nonnull;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public final class FilesUtil {
    @Nonnull
    public static <T> List<T> readLines(@Nonnull Path path, @Nonnull Function<String, T> mapping) throws IOException {
        if (Files.exists(path)) {
            try (Stream<String> lines = Files.lines(path)) {
                return lines.filter(StringUtil::isNotBlank).map(mapping).collect(Collectors.toList());
            }
        }
        return List.of();
    }
    
    @Nonnull
    public static <T> T readString(@Nonnull Path path, @Nonnull Function<String, T> mapping) throws IOException {
        return readString(path, mapping, null);
    }
    
    @Nonnull
    public static <T> T readString(@Nonnull Path path, @Nonnull Function<String, T> mapping, T defaultValue) throws IOException {
        if (Files.exists(path)) {
            return mapping.apply(Files.readString(path));
        }
        return defaultValue;
    }
    
    @Nonnull
    public static <T> Path write(@Nonnull Path path, @Nonnull Collection<T> contents, @Nonnull Function<T, String> mapping) throws IOException {
        return Files.write(path, contents.stream().filter(Objects::nonNull).map(mapping).toList());
    }
}
