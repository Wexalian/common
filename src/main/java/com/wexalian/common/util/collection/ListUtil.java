package com.wexalian.common.util.collection;

import com.wexalian.nullability.annotations.Nonnull;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class ListUtil {
    @Nonnull
    public static Builder newArrayList() {
        return newList(ArrayList::new);
    }
    
    @Nonnull
    public static Builder newList(@Nonnull Supplier<List<?>> listSupplier) {
        return new Builder(listSupplier);
    }
    
    @SuppressWarnings("unchecked")
    public static class Builder {
        private final Supplier<List<?>> listSupplier;
        
        private Builder(Supplier<List<?>> listSupplier) {
            this.listSupplier = listSupplier;
        }
        
        @Nonnull
        public <T> List<T> values(@Nonnull T... values) {
            return values(List.of(values));
        }
        
        @Nonnull
        public <T> List<T> values(@Nonnull Iterable<T> values) {
            List<T> list = (List<T>) listSupplier.get();
            values.forEach(list::add);
            return list;
        }
        
        @Nonnull
        public <T> List<T> fill(@Nonnull Consumer<List<T>> filler) {
            List<T> list = (List<T>) listSupplier.get();
            filler.accept(list);
            return list;
        }
    }
}
