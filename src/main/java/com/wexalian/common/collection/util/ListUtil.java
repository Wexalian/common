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

public class ListUtil {
    @Nonnull
    public static <T> Builder<T> builder() {
        return builder(ArrayList::new);
    }

    @Nonnull
    public static <T> Builder<T> builder(@Nonnull Supplier<List<T>> listSupplier) {
        return new Builder<>(listSupplier);
    }

    @Nonnull
    @SafeVarargs
    public static <T> List<T> all(@Nonnull T... values) {
        return ListUtil.<T>builder().all(values).build();
    }

    @Nonnull
    public static <T> List<T> all(@Nonnull Iterable<T> values) {
        return ListUtil.<T>builder().all(values).build();
    }

    @Nonnull
    @SafeVarargs
    public static <T> List<T> nonnull(@Nonnull T... values) {
        return ListUtil.<T>builder().nonnull(values).build();
    }

    @Nonnull
    public static <T> List<T> nonnull(@Nonnull Iterable<T> values) {
        return ListUtil.<T>builder().nonnull(values).build();
    }

    @Nonnull
    @SafeVarargs
    public static <T> List<T> filtered(@Nonnull Predicate<T> filter, @Nonnull T... values) {
        return ListUtil.<T>builder().filtered(filter, values).build();
    }

    @Nonnull
    public static <T> List<T> filtered(@Nonnull Predicate<T> filter, @Nonnull Iterable<T> values) {
        return ListUtil.<T>builder().filtered(filter, values).build();
    }

    @Nonnull
    @SafeVarargs
    public static <R, T> List<T> mapped(@Nonnull Function<R, T> mapping, @Nonnull R... values) {
        return ListUtil.<T>builder().mapped(mapping, values).build();
    }

    @Nonnull
    public static <R, T> List<T> mapped(@Nonnull Function<R, T> mapping, @Nonnull Iterable<R> values) {
        return ListUtil.<T>builder().mapped(mapping, values).build();
    }

    @SuppressWarnings("UnusedReturnValue")
    public static class Builder<T> {
        private final Supplier<List<T>> listSupplier;
        private final List<ListContents<T>> listContents;

        private Builder(Supplier<List<T>> listSupplier) {
            this.listSupplier = listSupplier;
            this.listContents = new ArrayList<>();
        }

        @Nonnull
        public final Builder<T> add(@Nonnull T value) {
            listContents.add(list -> list.accept(value));
            return this;
        }

        @Nonnull
        @SafeVarargs
        public final Builder<T> all(@Nonnull T... values) {
            return all(List.of(values));
        }

        @Nonnull
        public final Builder<T> all(@Nonnull Iterable<T> values) {
            listContents.add(values::forEach);
            return this;
        }

        @Nonnull
        public final Builder<T> all(@Nonnull Stream<T> stream) {
            listContents.add(stream::forEach);
            return this;
        }

        @Nonnull
        public final Builder<T> fill(@Nonnull Consumer<Consumer<T>> filler) {
            listContents.add(filler::accept);
            return this;
        }

        @Nonnull
        @SafeVarargs
        public final Builder<T> nonnull(@Nonnull T... values) {
            return filtered(Objects::nonNull, values);
        }

        @Nonnull
        public final Builder<T> nonnull(@Nonnull Iterable<T> values) {
            return filtered(Objects::nonNull, values);
        }

        @Nonnull
        @SafeVarargs
        public final Builder<T> filtered(@Nonnull Predicate<T> filter, @Nonnull T... values) {
            return all(stream(values).filter(filter));
        }

        @Nonnull
        public final Builder<T> filtered(@Nonnull Predicate<T> filter, @Nonnull Iterable<T> values) {
            return all(stream(values).filter(filter));
        }

        @Nonnull
        public final Builder<T> filter(@Nonnull Predicate<T> filter) {
            List<ListContents<T>> listContents = List.copyOf(this.listContents);
            this.listContents.clear();

            fill(consumer -> {
                listContents.forEach(content -> {
                    content.add(t -> {
                        if (filter.test(t)) consumer.accept(t);
                    });
                });
            });

            return this;
        }

        @Nonnull
        @SafeVarargs
        public final <R> Builder<T> mapped(@Nonnull Function<R, T> mapping, @Nonnull R... values) {
            return all(stream(values).map(mapping));
        }

        @Nonnull
        public final <R> Builder<T> mapped(@Nonnull Function<R, T> mapping, @Nonnull Iterable<R> values) {
            return all(stream(values).map(mapping));
        }

        @Nonnull
        @SuppressWarnings("unchecked")
        public final <R> Builder<R> map(Function<T, R> mapping) {
            List<ListContents<T>> listContents = List.copyOf(this.listContents);
            this.listContents.clear();

            Builder<R> builder = (Builder<R>) this;
            builder.fill(consumer -> {
                listContents.forEach(content -> {
                    content.add(value -> consumer.accept(mapping.apply(value)));
                });
            });

            return builder;
        }

        @Nonnull
        @SuppressWarnings("unchecked")
        public final <R> List<R> build() {
            List<T> list = listSupplier.get();
            listContents.forEach(content -> content.add(list::add));
            return (List<R>) list;
        }

        private <V> Stream<V> stream(V[] values) {
            return Stream.of(values);
        }

        private <V> Stream<V> stream(Iterable<V> iterable) {
            return StreamSupport.stream(iterable.spliterator(), true);
        }

        @FunctionalInterface
        public interface ListContents<T> {
            void add(@Nonnull Consumer<T> list);
        }
    }
}
