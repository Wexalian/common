package com.wexalian.common.util.collection;

import com.wexalian.nullability.annotations.Nonnull;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

public class MapUtil {
    
    @Nonnull
    public static Builder newHashMap() {
        return newMap(HashMap::new);
    }
    
    @Nonnull
    public static Builder newMap(@Nonnull Supplier<Map<?, ?>> mapSupplier) {
        return new Builder(mapSupplier);
    }
    
    @SuppressWarnings("unchecked")
    public static class Builder {
        private final Supplier<Map<?, ?>> mapSupplier;
        
        private Builder(Supplier<Map<?, ?>> mapSupplier) {
            this.mapSupplier = mapSupplier;
        }
        
        @Nonnull
        public <K, V> Map<K, V> keys(@Nonnull K[] keys, @Nonnull Function<K, V> mapping) {
            return keys(List.of(keys), mapping);
        }
        
        @Nonnull
        public <K, V> Map<K, V> keys(@Nonnull Iterable<K> keys, @Nonnull Function<K, V> mapping) {
            Map<K, V> map = (Map<K, V>) mapSupplier.get();
            keys.forEach(key -> map.put(key, mapping.apply(key)));
            return map;
        }
        
        @Nonnull
        public <K, V> Map<K, V> values(@Nonnull V[] values, @Nonnull Function<V, K> mapping) {
            return values(List.of(values), mapping);
        }
        
        @Nonnull
        public <K, V> Map<K, V> values(@Nonnull Iterable<V> values, @Nonnull Function<V, K> mapping) {
            Map<K, V> map = (Map<K, V>) mapSupplier.get();
            values.forEach(value -> map.put(mapping.apply(value), value));
            return map;
        }
        
        @Nonnull
        public <K, V> Map<K, V> fill(@Nonnull Consumer<Map<K, V>> filler) {
            Map<K, V> map = (Map<K, V>) mapSupplier.get();
            filler.accept(map);
            return map;
        }
    }
}
