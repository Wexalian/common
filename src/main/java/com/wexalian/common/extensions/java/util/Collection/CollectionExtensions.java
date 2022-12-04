package com.wexalian.common.extensions.java.util.Collection;

import com.wexalian.common.util.StringUtil;
import com.wexalian.nullability.annotations.Nonnull;
import manifold.ext.rt.api.Extension;
import manifold.ext.rt.api.This;

import java.util.Collection;
import java.util.function.Function;

@Extension
public class CollectionExtensions {
    @Nonnull
    public static <T> String join(@This Collection<T> self, @Nonnull String delimiter) {
        return StringUtil.join(self, delimiter);
    }
    
    @Nonnull
    public static <T> String join(@This Collection<T> self, @Nonnull String delimiter, @Nonnull Function<T, String> formatter) {
        return StringUtil.join(self, delimiter, formatter);
    }
}
