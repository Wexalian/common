package com.wexalian.common.collection.util;

import com.wexalian.nullability.annotations.Nonnull;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

@SuppressWarnings("unchecked")
public class ListUtilNew {
    private static final Predicate<Object> ALWAYS = o -> true;
    private static final Predicate<Object> NON_NULL = Objects::nonNull;
    
    @Nonnull
    public static <T> List<T> nonnull(T[] values) {
        return builder().filtered(values, NON_NULL).create();
    }
    
    @Nonnull
    public static <T> List<T> nonnull(Iterable<T> values) {
        return builder().filtered(values, (Predicate<T>) NON_NULL).create();
    }
    
    @Nonnull
    public static <T> List<T> filter(T[] values, Predicate<T> filter) {
        return builder().filtered(values, filter).create();
    }
    
    @Nonnull
    public static <T> List<T> filter(Iterable<T> values, Predicate<T> filter) {
        return builder().filtered(values, filter).create();
    }
    
    @Nonnull
    public static <T, R> List<R> map(T[] values, Function<T, R> mapping) {
        return builder().mapped(values, mapping).create();
    }
    
    @Nonnull
    public static <T, R> List<R> map(Iterable<T> values, Function<T, R> mapping) {
        return builder().mapped(values, mapping).create();
    }
    
    @Nonnull
    public static Builder builder() {
        return builder(ArrayList::new);
    }
    
    @Nonnull
    public static Builder builder(@Nonnull Supplier<List<?>> listSupplier) {
        return new Builder(listSupplier);
    }
    
    public static class Builder {
        private final Supplier<List<?>> listSupplier;
        private final List<ListAdder<?>> adders = new ArrayList<>();
        
        private Builder(Supplier<List<?>> listSupplier) {
            this.listSupplier = listSupplier;
        }
        
        @Nonnull
        public final <T> Builder add(@Nonnull T value) {
            adders.add(list -> list.add(value));
            return this;
        }
        
        @Nonnull
        public final <T> Builder all(@Nonnull T[] values) {
            return all(List.of(values));
        }
        
        @Nonnull
        public final <T> Builder all(@Nonnull Iterable<T> values) {
            adders.add(list -> values.forEach(list::add));
            return this;
        }
        
        @Nonnull
        public final <T> Builder all(@Nonnull Stream<T> stream) {
            adders.add(list -> stream.forEach(list::add));
            return this;
        }
        
        @Nonnull
        public final <T> Builder filtered(@Nonnull T[] values, @Nonnull Predicate<T> filter) {
            return all(stream(values).filter(filter));
        }
        
        @Nonnull
        public final <T> Builder filtered(@Nonnull Iterable<T> values, @Nonnull Predicate<T> filter) {
            return all(stream(values).filter(filter));
        }
        
        @Nonnull
        public final <T, R> Builder mapped(@Nonnull T[] values, @Nonnull Function<T, R> mapping) {
            return all(stream(values).map(mapping));
        }
        
        @Nonnull
        public final <T, R> Builder mapped(@Nonnull Iterable<T> values, @Nonnull Function<T, R> mapping) {
            return all(stream(values).map(mapping));
        }
        
        @Nonnull
        public final <T> Builder fill(@Nonnull Consumer<List<T>> filler) {
            adders.add(list -> filler.accept((List<T>) list));
            return this;
        }
        
        @Nonnull
        public final <T> List<T> create() {
            List<T> list = (List<T>) listSupplier.get();
            adders.forEach(adder -> ((ListAdder<T>) adder).add(list));
            return list;
        }
        
        private <T> Stream<T> stream(T[] values) {
            return Stream.of(values);
        }
        
        private <T> Stream<T> stream(Iterable<T> iterable) {
            return StreamSupport.stream(iterable.spliterator(), true);
        }
        
        @FunctionalInterface
        public interface ListAdder<T> {
            void add(@Nonnull List<T> list);
        }
    }
}