package com.wexalian.common.function;

@FunctionalInterface
public interface FromString<T> {
    T fromString(String string);
}
