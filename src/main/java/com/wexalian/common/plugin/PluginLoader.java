package com.wexalian.common.plugin;

import com.wexalian.common.collection.wrapper.StreamWrapper;
import com.wexalian.nullability.annotations.Nonnull;

import java.nio.file.Path;
import java.util.ServiceLoader;
import java.util.stream.Stream;

@FunctionalInterface
public interface PluginLoader<T extends IAbstractPlugin> extends StreamWrapper.Iterable<T> {
    @Nonnull
    @Override
    Stream<T> get();
    
    static void init(@Nonnull ServiceLoaderLayerFunction serviceLoaderFunc) {
        PluginLoaderImpl.init(serviceLoaderFunc);
    }
    
    static void loadPlugins(@Nonnull Path path) {
        PluginLoaderImpl.loadPlugins(path);
    }
    
    @Nonnull
    static <T extends IAbstractPlugin> PluginLoader<T> load(@Nonnull Class<T> pluginClass) {
        return load(pluginClass, null);
    }
    
    @Nonnull
    static <T extends IAbstractPlugin> PluginLoader<T> load(@Nonnull Class<T> pluginClass, ServiceLoaderFallbackFunction fallbackServiceProvider) {
        return PluginLoaderImpl.load(pluginClass, fallbackServiceProvider);
    }
    
    @FunctionalInterface
    interface ServiceLoaderLayerFunction {
        @Nonnull
        <T> ServiceLoader<T> load(@Nonnull ModuleLayer layer, @Nonnull Class<T> clazz);
        
        @Nonnull
        default <T> Stream<T> stream(@Nonnull ModuleLayer layer, @Nonnull Class<T> clazz) {
            return load(layer, clazz).stream().map(ServiceLoader.Provider::get);
        }
    }
    
    @FunctionalInterface
    interface ServiceLoaderFallbackFunction {
        @Nonnull
        <T> ServiceLoader<T> load(@Nonnull Class<T> clazz);
        
        @Nonnull
        default <T> Stream<T> stream(@Nonnull Class<T> clazz) {
            return load(clazz).stream().map(ServiceLoader.Provider::get);
        }
    }
}
