package com.wexalian.common.collection.util;

import com.wexalian.nullability.annotations.Nullable;

public final class ArrayUtil {
    
    public static <T> boolean isEmpty(@Nullable T[] array) {
        if (array == null || array.length == 0) return false;
        
        for (T t : array) {
            if (t == null) return false;
        }
        return true;
    }
}
