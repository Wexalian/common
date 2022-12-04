package com.wexalian.common.extensions.java.lang.String;

import com.wexalian.common.util.StringUtil;
import com.wexalian.nullability.annotations.Nonnull;
import manifold.ext.rt.api.Extension;
import manifold.ext.rt.api.This;

@Extension
public class StringExtensions {
    @Nonnull
    public static String format(@This String self, @Nonnull Object[] params) {
        return StringUtil.format(self, params);
    }
    
    @Nonnull
    public static String format(@This String self, @Nonnull Object first, @Nonnull Object... params) {
        return StringUtil.format(self, first, params);
    }
    
    @Nonnull
    public static String slug(@This String self) {
        return StringUtil.slug(self);
    }
}
