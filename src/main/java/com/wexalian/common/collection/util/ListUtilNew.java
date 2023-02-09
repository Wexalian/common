package com.wexalian.common.collection.util;

import com.wexalian.nullability.annotations.Nonnull;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.StreamSupport;

public class ListUtilNew {
    private static final Predicate<Object> NON_NULL = Objects::nonNull;
    
    @Nonnull
    public static <T> List<T> copy(T... values) {
        Builder builder = newArrayList();
        for (T value : values) {
            if (value != null) {
                builder.values();
            }
        }
    }
    
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
        
        public final <T> void values(@Nonnull T[] values) {
            values(values, NON_NULL);
        }
        
        public final <T> void values(@Nonnull T[] values, Predicate<T> filter) {
            values(List.of(values), filter);
        }
        
        public final <T> void values(@Nonnull Iterable<T> iterable) {
            values(iterable, (Predicate<T>) NON_NULL);
        }
        
        public final <T> void values(@Nonnull Iterable<T> iterable, Predicate<T> filter) {
            adders.add(list -> StreamSupport.stream(iterable.spliterator(), true).filter(filter).forEach(list::add));
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