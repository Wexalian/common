package com.wexalian.common.plugin;

import com.wexalian.nullability.annotations.Nonnull;

import java.nio.file.Path;
import java.util.Iterator;
import java.util.ServiceLoader;

@FunctionalInterface
public interface PluginLoader<T extends IAbstractPlugin> extends Iterable<T> {
    @Override
    Iterator<T> iterator();
    
    static void init(@Nonnull ServiceLoaderFunction serviceLoaderFunc) {
        PluginLoaderImpl.init(serviceLoaderFunc);
    }
    
    static void loadPlugins(@Nonnull Path path) {
        PluginLoaderImpl.loadPlugins(path);
    }
    
    @Nonnull
    static <T extends IAbstractPlugin> PluginLoader<T> load(@Nonnull Class<T> pluginClass) {
        return load(pluginClass, true);
    }
    
    @Nonnull
    static <T extends IAbstractPlugin> PluginLoader<T> load(@Nonnull Class<T> pluginClass, boolean useDefaultServiceProvider) {
        return PluginLoaderImpl.load(pluginClass, useDefaultServiceProvider);
    }
    
    @FunctionalInterface
    interface ServiceLoaderFunction {
        @Nonnull
        <T> ServiceLoader<T> load(@Nonnull ModuleLayer layer, @Nonnull Class<T> clazz);
    }
}
