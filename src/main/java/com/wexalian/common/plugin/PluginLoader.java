package com.wexalian.common.plugin;

import com.wexalian.nullability.annotations.Nonnull;

import java.nio.file.Path;

@FunctionalInterface
public interface PluginLoader<T> extends Iterable<T> {
    static void init() {
        PluginLoaderImpl.init();
    }
    
    static void loadPlugins(@Nonnull Path path) {
        PluginLoaderImpl.loadPlugins(path);
    }
    
    @Nonnull
    static <T> PluginLoader<T> load(@Nonnull Class<T> pluginClass) {
        return PluginLoaderImpl.load(pluginClass);
    }
}
