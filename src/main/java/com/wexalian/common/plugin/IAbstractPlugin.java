package com.wexalian.common.plugin;

import com.wexalian.nullability.annotations.Nonnull;

public interface IAbstractPlugin {
    @Nonnull
    String getName();
    
    boolean isEnabled();
}
