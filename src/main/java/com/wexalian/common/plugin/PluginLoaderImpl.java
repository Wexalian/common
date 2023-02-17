package com.wexalian.common.plugin;

import com.wexalian.nullability.annotations.Nonnull;

import java.io.IOException;
import java.lang.module.Configuration;
import java.lang.module.ModuleFinder;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.List;
import java.util.ServiceLoader;
import java.util.Set;
import java.util.stream.Stream;

final class PluginLoaderImpl {
    private static final Set<ModuleLayer> pluginLayerMap = new HashSet<>();
    
    private static PluginLoader.ServiceLoaderFunction serviceLoader;
    private static ModuleLayer coreLayer;
    private static ClassLoader coreLoader;
    
    private static boolean init = false;
    
    private PluginLoaderImpl() {}
    
    static void init(@Nonnull PluginLoader.ServiceLoaderFunction serviceLoaderFunc) {
        if (!init) {
            serviceLoader = serviceLoaderFunc;
            
            Class<?> coreClass = StackWalker.getInstance(StackWalker.Option.RETAIN_CLASS_REFERENCE).getCallerClass();
            coreLayer = coreClass.getModule().getLayer();
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
                    pluginLayerMap.add(pluginLayer);
                }
                catch (IOException e) {
                    throw new IllegalStateException("Error loading plugins from path " + path, e);
                }
            }
        }
        else throw new IllegalStateException("PluginLoaderImpl has to be initialized before you can load plugins!");
    }
    
    static <T extends IAbstractPlugin> PluginLoader<T> load(Class<T> clazz, boolean useDefault) {
        if (init) {
            return () -> pluginLayerMap.stream().flatMap(layer -> serviceLoader.stream(layer, clazz)).filter(IAbstractPlugin::isEnabled);
        }
        else if (useDefault) {
            return () -> ServiceLoader.load(clazz).stream().map(ServiceLoader.Provider::get);
        }
        else throw new IllegalStateException("PluginLoaderImpl has to be initialized before you can load services from plugins!");
    }
}
