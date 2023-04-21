package com.wexalian.common.plugin;

import com.wexalian.common.collection.util.IteratorUtil;
import com.wexalian.common.collection.util.ListUtil;
import com.wexalian.nullability.annotations.Nonnull;

import java.io.IOException;
import java.lang.module.Configuration;
import java.lang.module.ModuleFinder;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.Stream;

final class PluginLoaderImpl {
    private static final Set<ModuleLayer> pluginLayerSet = new HashSet<>();
    
    private static final Module DEFAULT_MODULE = PluginLoaderImpl.class.getModule();
    
    private static Module coreModule;
    private static ModuleLayer coreLayer;
    private static ClassLoader coreLoader;
    
    private static boolean init = false;
    
    private PluginLoaderImpl() {}
    
    static void init() {
        if (!init) {
            Class<?> coreClass = StackWalker.getInstance(StackWalker.Option.RETAIN_CLASS_REFERENCE).getCallerClass();
            coreModule = coreClass.getModule();
            coreLayer = coreModule.getLayer();
            coreLoader = coreClass.getClassLoader();
            
            if (coreLayer == null) {
                throw new IllegalStateException("PluginLoaderImpl can only be initialized from a named module!");
            }
            else init = true;
        }
        else throw new IllegalStateException("PluginLoaderImpl can only be initialized once!");
    }
    
    static void loadPlugins(@Nonnull Path path) {
        if (init) {
            if (Files.exists(path)) {
                try (Stream<Path> paths = Files.list(path)) {
                    ModuleFinder moduleFinder = ModuleFinder.of(paths.toArray(Path[]::new));
                    List<String> moduleNames = moduleFinder.findAll().stream().map(ref -> ref.descriptor().name()).toList();
                    Configuration configuration = coreLayer.configuration().resolveAndBind(moduleFinder, ModuleFinder.of(), moduleNames);
                    ModuleLayer pluginLayer = coreLayer.defineModulesWithOneLoader(configuration, coreLoader);
                    pluginLayerSet.add(pluginLayer);
                }
                catch (IOException e) {
                    throw new IllegalStateException("Error loading plugins from path " + path, e);
                }
            }
        }
        else throw new IllegalStateException("PluginLoaderImpl has to be initialized before you can load plugins!");
    }
    
    static <T> PluginLoader<T> load(Class<T> clazz) {
        Iterator<T> plugins = loadInternal(clazz);
        if (IAbstractPlugin.class.isAssignableFrom(clazz)) {
            return () -> IteratorUtil.filter(plugins, plugin -> ((IAbstractPlugin) plugin).isEnabled());
        }
        return () -> plugins;
    }
    
    static <T> List<T> loadAll(Class<T> clazz) {
        Iterator<T> plugins = loadInternal(clazz);
        if (IAbstractPlugin.class.isAssignableFrom(clazz)) {
            return ListUtil.filter(() -> plugins, plugin -> ((IAbstractPlugin) plugin).isEnabled());
        }
        return ListUtil.all(() -> plugins);
    }
    
    private static <T> Iterator<T> loadInternal(Class<T> clazz) {
        if (init) {
            if (!pluginLayerSet.isEmpty()) {
                List<Iterator<T>> list = pluginLayerSet.stream().map(layer -> loadFromLayer(layer, clazz).iterator()).toList();
                return IteratorUtil.merge(list);
            }
            else return loadFromLayer(coreLayer, clazz).iterator();
        }
        else return loadFromBaseModule(clazz).iterator();
    }
    
    private static <T> ServiceLoader<T> loadFromLayer(ModuleLayer layer, Class<T> clazz) {
        for (Module module : layer.modules()) {
            module.addUses(clazz);
        }
        return ServiceLoader.load(layer, clazz);
    }
    
    private static <T> ServiceLoader<T> loadFromBaseModule(Class<T> clazz) {
        DEFAULT_MODULE.addUses(clazz);
        return ServiceLoader.load(DEFAULT_MODULE.getLayer(), clazz);
    }
}
