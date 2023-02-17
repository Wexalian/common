package com.wexalian.common.plugin.test_impl;

import com.wexalian.common.plugin.IAbstractPlugin;
import com.wexalian.nullability.annotations.Nonnull;

public class TestPlugin implements IAbstractPlugin {
    @Nonnull
    @Override
    public String getName() {
        return "Test";
    }
    
    @Override
    public boolean isEnabled() {
        return true;
    }
}
