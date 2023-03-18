package com.wexalian.common.test.plugin;

import com.wexalian.common.plugin.IAbstractPlugin;
import com.wexalian.common.plugin.PluginLoader;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.nio.file.Path;
import java.util.ServiceLoader;

public class PluginLoaderTest {
    public static final Path PATH = Path.of(System.getProperty("user.dir"), "..", "common-plugin-test/build/libs");
    
    @BeforeAll
    static void setup() {
        System.out.println("-------------------- !!! MAKE SURE TO BUILD PLUGINS TEST PROJECT AS WELL !!! --------------------");
        
        PluginLoader.init(ServiceLoader::load);
        PluginLoader.loadPlugins(PATH);
    }
    
    @Test
    void testLoader() {
        PluginLoader<IAbstractPlugin> loader = PluginLoader.load(IAbstractPlugin.class, ServiceLoader::load);
        for (IAbstractPlugin plugin : loader) {
            System.out.println(plugin.getName());
        }
    }
}
