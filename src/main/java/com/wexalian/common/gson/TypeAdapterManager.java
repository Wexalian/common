package com.wexalian.common.gson;

import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.wexalian.common.function.FromString;
import com.wexalian.common.function.ToString;
import com.wexalian.common.gson.adapter.AbstractTypeAdapter;
import com.wexalian.common.gson.adapter.IGsonTypeAdapter;
import com.wexalian.common.plugin.PluginLoader;
import com.wexalian.common.util.StringUtil;
import com.wexalian.nullability.annotations.Nonnull;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public final class TypeAdapterManager {
    private static final PluginLoader<IGsonTypeAdapter.Registry> REGISTRY_LOADER = PluginLoader.load(IGsonTypeAdapter.Registry.class);
    
    public static void addTypeAdapters(@Nonnull GsonBuilder builder) {
        Adapters adapters = new Adapters();
        REGISTRY_LOADER.forEach(registry -> registry.register(adapters));
        adapters.typeAdapters.forEach(adapter -> builder.registerTypeAdapter(adapter.getType(), adapter));
        adapters.typeHierarchyAdapters.forEach(adapter -> builder.registerTypeHierarchyAdapter(adapter.getType(), adapter));
    }
    
    public static class Adapters {
        private final List<IGsonTypeAdapter> typeAdapters = new ArrayList<>();
        private final List<IGsonTypeAdapter.Hierarchy> typeHierarchyAdapters = new ArrayList<>();
        
        public void register(@Nonnull IGsonTypeAdapter adapter) {
            if (adapter instanceof IGsonTypeAdapter.Hierarchy hierarchyAdapter) {
                typeHierarchyAdapters.add(hierarchyAdapter);
            } else typeAdapters.add(adapter);
        }
        
        public <T> void register(@Nonnull Class<T> clazz, @Nonnull FromString<T> fromJsonFunc, @Nonnull ToString<T> toJsonFunc) {
            register(new Impl<>(clazz, fromJsonFunc, toJsonFunc));
        }
    }
    
    private static class Impl<T> extends AbstractTypeAdapter<T> {
        private final Class<T> clazz;
        private final FromString<T> fromJsonFunc;
        private final ToString<T> toJsonFunc;
        
        public Impl(Class<T> clazz, FromString<T> fromJsonFunc, ToString<T> toJsonFunc) {
            this.clazz = clazz;
            this.fromJsonFunc = fromJsonFunc;
            this.toJsonFunc = toJsonFunc;
        }
        
        @Override
        public Type getType() {
            return clazz;
        }
        
        @Override
        protected final T fromJson(JsonElement json) {
            String string = json.getAsString();
            if (!StringUtil.isBlank(string)) {
                return fromJsonFunc.fromString(string);
            }
            return null;
        }
        
        @Override
        protected final JsonElement toJson(T src) {
            return new JsonPrimitive(toJsonFunc.toString(src));
        }
    }
}
