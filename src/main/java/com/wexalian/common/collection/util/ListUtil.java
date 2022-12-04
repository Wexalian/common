package com.wexalian.common.collection.util;

import com.wexalian.nullability.annotations.Nonnull;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class ListUtil {
    @Nonnull
    @Deprecated
    public static Builder newArrayList() {
        return newList(ArrayList::new);
    }
    
    @Nonnull
    @Deprecated
    public static Builder newList(@Nonnull Supplier<List<?>> listSupplier) {
        throw new UnsupportedOperationException("ListUtil is old, please use ListUtilNew!");
    }
    
    @SuppressWarnings("unchecked")
    public static class Builder {
        private final Supplier<List<?>> listSupplier;
        
        private Builder(Supplier<List<?>> listSupplier) {
            this.listSupplier = listSupplier;
        }
        
        @Nonnull
        @Deprecated
        public final <T> List<T> value(@Nonnull T value) {
            return values(List.of(value));
        }
        
        @Nonnull
        @Deprecated
        @SafeVarargs
        public final <T> List<T> values(@Nonnull T... values) {
            return values(List.of(values));
        }
        
        @Nonnull
        @Deprecated
        public final <T> List<T> values(@Nonnull Iterable<T> values) {
            List<T> list = (List<T>) listSupplier.get();
            values.forEach(list::add);
            return list;
        }
        
        @Nonnull
        @Deprecated
        public final <T> List<T> fill(@Nonnull Consumer<List<T>> filler) {
            List<T> list = (List<T>) listSupplier.get();
            filler.accept(list);
            return list;
        }
    }
}