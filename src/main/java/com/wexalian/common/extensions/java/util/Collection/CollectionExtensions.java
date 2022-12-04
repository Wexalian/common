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
    public static <E> String join(@This Collection<E> self, @Nonnull String delimiter) {
        return StringUtil.join(self, delimiter);
    }
    
    @Nonnull
    public static <E> String join(@This Collection<E> self, @Nonnull String delimiter, @Nonnull Function<E, String> formatter) {
        return StringUtil.join(self, delimiter, formatter);
    }
}
