package com.wexalian.common.util;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collection;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public final class FilesUtil {
    public static <T> List<T> read(Path path, Function<String, T> mapping) throws IOException {
        try (Stream<String> lines = Files.lines(path)) {
            return lines.map(mapping).collect(Collectors.toList());
        }
    }
    
    public static <T> Path write(Path path, Collection<T> contents, Function<T, String> mapping) throws IOException {
        return Files.write(path, contents.stream().map(mapping).toList());
    }
}
