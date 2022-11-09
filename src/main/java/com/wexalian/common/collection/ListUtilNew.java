package com.wexalian.common.collection;

import com.wexalian.nullability.annotations.Nonnull;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class ListUtilNew {
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
        private final List<ListAdder<?>> adders = new ArrayList<>();
        
        private Builder(Supplier<List<?>> listSupplier) {
            this.listSupplier = listSupplier;
        }
        
        @Nonnull
        public final <T> void value(@Nonnull T value) {
            adders.add(list -> list.add(value));
        }
        
        @SafeVarargs
        public final <T> void values(@Nonnull T... values) {
            values(List.of(values));
        }
        
        public final <T> void values(@Nonnull Iterable<T> values) {
            adders.add(list -> values.forEach(list::add));
        }
        
        public final <T> void fill(@Nonnull Consumer<List<T>> filler) {
            adders.add(list -> filler.accept((List<T>) list));
        }
        
        @Nonnull
        public final <T> List<T> create() {
            List<T> list = (List<T>) listSupplier.get();
            adders.forEach(adder -> ((ListAdder<T>) adder).addToList(list));
            return list;
        }
        
        @FunctionalInterface
        public interface ListAdder<T> {
            void addToList(@Nonnull List<T> list);
        }
    }
}