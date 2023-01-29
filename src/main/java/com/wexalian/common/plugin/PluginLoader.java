package com.wexalian.common.plugin;

import java.io.IOException;
import java.lang.module.Configuration;
import java.lang.module.ModuleFinder;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.ServiceLoader;
import java.util.function.BiFunction;
import java.util.stream.Stream;

public class PluginLoader {
    public static <T extends IAbstractPlugin> List<T> loadPlugins(Path pluginFolder, Class<T> pluginClazz, BiFunction<ModuleLayer, Class<T>, ServiceLoader<T>> pluginLoader) {
        List<T> providers = new ArrayList<>();
        
        try (Stream<Path> paths = Files.list(pluginFolder)) {
            ModuleLayer coreLayer = pluginClazz.getModule().getLayer();
            ClassLoader coreLoader = pluginClazz.getClassLoader();
            
            ModuleFinder moduleFinder = ModuleFinder.of(paths.toArray(Path[]::new));
            List<String> moduleNames = moduleFinder.findAll().stream().map(ref -> ref.descriptor().name()).toList();
            Configuration configuration = coreLayer.configuration().resolveAndBind(moduleFinder, ModuleFinder.of(), moduleNames);
            ModuleLayer moduleLayer = coreLayer.defineModulesWithOneLoader(configuration, coreLoader);
            
            ServiceLoader<T> serviceLoader = pluginLoader.apply(moduleLayer, pluginClazz);
            serviceLoader.stream().map(ServiceLoader.Provider::get).filter(IAbstractPlugin::isEnabled).forEach(providers::add);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return providers;
    }
}
