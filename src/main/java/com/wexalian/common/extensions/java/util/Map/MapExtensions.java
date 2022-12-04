package com.wexalian.common.extensions.java.util.Map;

import manifold.ext.rt.api.Extension;
import manifold.ext.rt.api.This;

import java.util.Map;

@Extension
public class MapExtensions {
    public static <K, V> V set(@This Map<K, V> self, K key, V value) {
        return self.put(key, value);
    }
}
