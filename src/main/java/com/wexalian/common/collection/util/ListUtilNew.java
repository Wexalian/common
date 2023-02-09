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
    private static final Predicate<Object> ALWAYS = o -> true;
    private static final Predicate<Object> NON_NULL = Objects::nonNull;
    
    @Nonnull
    public static <T> List<T> copyNonnull(T... values) {
        return newArrayList().values(values, NON_NULL).create();
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
        public final <T> Builder value(@Nonnull T value) {
            adders.add(list -> list.add(value));
            return this;
        }
        
        public final <T> Builder values(@Nonnull T[] values) {
            return values(values, ALWAYS);
        }
        
        public final <T> Builder values(@Nonnull T[] values, Predicate<T> filter) {
            return values(List.of(values), filter);
        }
        
        public final <T> Builder values(@Nonnull Iterable<T> iterable) {
            return values(iterable, (Predicate<T>) ALWAYS);
        }
        
        public final <T> Builder values(@Nonnull Iterable<T> iterable, Predicate<T> filter) {
            adders.add(list -> StreamSupport.stream(iterable.spliterator(), true).filter(filter).forEach(list::add));
            return this;
        }
        
        public final <T> Builder fill(@Nonnull Consumer<List<T>> filler) {
            adders.add(list -> filler.accept((List<T>) list));
            return this;
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