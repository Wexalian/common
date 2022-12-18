package com.wexalian.common.extensions.java.util.List;

import manifold.ext.rt.api.Extension;
import manifold.ext.rt.api.This;

import java.util.List;

@Extension
public class ListExtensions {
    public static <E> E first(@This List<E> self) {
        return self.get(0);
    }
    
    public static <E> E last(@This List<E> self) {
        if (self.size() == 0) return null;
        return self.get(self.size() - 1);
    }
}
