package com.wexalian.common.collection.wrapper;

import com.wexalian.nullability.annotations.Nonnull;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

@FunctionalInterface
public interface MapWrapper<K, V> extends Map<K, V> {
    @Nonnull
    Map<K, V> get();
    
    @Override
    default int size() {
        return get().size();
    }
    
    @Override
    default boolean isEmpty() {
        return get().isEmpty();
    }
    
    @Override
    default boolean containsKey(Object key) {
        return get().containsKey(key);
    }
    
    @Override
    default boolean containsValue(Object value) {
        return get().containsValue(value);
    }
    
    @Override
    default V get(Object key) {
        return get().get(key);
    }
    
    @Override
    default V put(K key, V value) {
        return get().put(key, value);
    }
    
    @Override
    default V remove(Object key) {
        return get().remove(key);
    }
    
    @Override
    default void putAll(Map<? extends K, ? extends V> m) {
        get().putAll(m);
    }
    
    @Override
    default void clear() {
        get().clear();
    }
    
    @Override
    default Set<K> keySet() {
        return get().keySet();
    }
    
    @Override
    default Collection<V> values() {
        return get().values();
    }
    
    @Override
    default Set<Entry<K, V>> entrySet() {
        return get().entrySet();
    }
}
