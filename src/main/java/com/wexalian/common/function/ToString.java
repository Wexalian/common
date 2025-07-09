package com.wexalian.common.function;

@FunctionalInterface
public interface ToString<T> {
    String toString(T value);
}
