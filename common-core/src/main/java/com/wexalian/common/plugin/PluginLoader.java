package com.wexalian.common.plugin;

import java.io.IOException;
import java.lang.module.Configuration;
import java.lang.module.ModuleFinder;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.Stream;

public final class PluginLoader<T extends IAbstractPlugin> implements Iterable<T> {
    private static final Map<Path, ModuleLayer> pluginLayerMap = new HashMap<>();
    
    private static ServiceLoaderFunction serviceLoader;
    private static ModuleLayer coreLayer;
    private static ClassLoader coreLoader;
    
    private static boolean init = false;
    
    private final Iterator<T> iterator;
    
    private PluginLoader(Iterator<T> iterator) {this.iterator = iterator;}
    
    public Iterator<T> iterator() {
        return iterator;
    }
    
    public static void init(ServiceLoaderFunction serviceLoaderFunc) {
        if (!init) {
            serviceLoader = serviceLoaderFunc;
            
            Class<?> coreClass = StackWalker.getInstance(StackWalker.Option.RETAIN_CLASS_REFERENCE).getCallerClass();
            coreLayer = coreClass.getModule().getLayer();
            coreLoader = coreClass.getClassLoader();
            
            init = true;
        }
        else throw new IllegalStateException("PluginLoader can only be initialized once!");
    }
    
    public static void loadPlugins(Path path) {
        if (init) {
            try (Stream<Path> paths = Files.list(path)) {
                ModuleFinder moduleFinder = ModuleFinder.of(paths.toArray(Path[]::new));
                List<String> moduleNames = moduleFinder.findAll().stream().map(ref -> ref.descriptor().name()).toList();
                Configuration configuration = coreLayer.configuration().resolveAndBind(moduleFinder, ModuleFinder.of(), moduleNames);
                ModuleLayer pluginLayer = coreLayer.defineModulesWithOneLoader(configuration, coreLoader);
                pluginLayerMap.put(path, pluginLayer);
            }
            catch (IOException e) {
                throw new IllegalStateException("Error loading plugins from path " + path, e);
            }
        }
        else throw new IllegalStateException("PluginLoader has to be initialized before you can load plugins!");
    }
    
    public static <T extends IAbstractPlugin> PluginLoader<T> load(Class<T> clazz, boolean useDefault) {
        if (init) {
            Stream<T> pluginStream = pluginLayerMap.values()
                                                   .stream()
                                                   .flatMap(layer -> serviceLoader.load(layer, clazz)
                                                                                  .stream()
                                                                                  .map(ServiceLoader.Provider::get)
                                                                                  .filter(IAbstractPlugin::isEnabled));
            return new PluginLoader<>(pluginStream.iterator());
        }
        else if (useDefault) {
            Stream<T> pluginStream = ServiceLoader.load(clazz).stream().map(ServiceLoader.Provider::get);
            return new PluginLoader<>(pluginStream.iterator());
        }
        else throw new IllegalStateException("PluginLoader has to be initialized before you can load services from plugins!");
    }
    
    public static <T extends IAbstractPlugin> PluginLoader<T> load(Class<T> pluginClass) {
        return PluginLoader.load(pluginClass, true);
    }
    
    @FunctionalInterface
    public interface ServiceLoaderFunction {
        <T> ServiceLoader<T> load(ModuleLayer layer, Class<T> clazz);
    }
}
