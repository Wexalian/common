package com.wexalian.common.collection.util;

import com.wexalian.common.stream.BiStream;
import com.wexalian.nullability.annotations.Nonnull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.function.*;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class MapUtil {
    @Nonnull
    public static <K, V> Builder<K, V> builder() {
        return builder(HashMap::new);
    }

    @Nonnull
    public static <K, V> Builder<K, V> builder(Supplier<Map<?, ?>> mapSupplier) {
        return new Builder<>(mapSupplier);
    }

    @Nonnull
    @SafeVarargs
    public static <K, V> Map<K, V> all(@Nonnull Entry<K, V>... values) {
        return MapUtil.<K, V>builder().all(values).build();
    }

    @Nonnull
    public static <K, V> Map<K, V> all(@Nonnull Iterable<Entry<K, V>> values) {
        return MapUtil.<K, V>builder().all(values).build();
    }

    @Nonnull
    public static <K, V> Map<K, V> all(@Nonnull Stream<Entry<K, V>> stream) {
        return MapUtil.<K, V>builder().all(stream).build();
    }

    @Nonnull
    public static <K, V> Map<K, V> all(@Nonnull BiStream<K, V> stream) {
        return MapUtil.<K, V>builder().all(stream).build();
    }

    @Nonnull
    @SafeVarargs
    public static <K, V> Map<K, V> keys(@Nonnull Function<K, V> mapping, @Nonnull K... keys) {
        return MapUtil.<K, V>builder().keys(mapping, keys).build();
    }

    @Nonnull
    public static <K, V> Map<K, V> keys(@Nonnull Function<K, V> mapping, @Nonnull Iterable<K> keys) {
        return MapUtil.<K, V>builder().keys(mapping, keys).build();
    }

    @Nonnull
    @SafeVarargs
    public static <K, V> Map<K, V> values(@Nonnull Function<V, K> mapping, @Nonnull V... values) {
        return MapUtil.<K, V>builder().values(mapping, values).build();
    }

    @Nonnull
    public static <K, V> Map<K, V> values(@Nonnull Function<V, K> mapping, @Nonnull Iterable<V> values) {
        return MapUtil.<K, V>builder().values(mapping, values).build();
    }

    @Nonnull
    @SafeVarargs
    public static <K, V> Map<K, V> filtered(@Nonnull BiPredicate<K, V> filter, @Nonnull Entry<K, V>... values) {
        return MapUtil.<K, V>builder().filtered(filter, values).build();
    }

    @Nonnull
    public static <K, V> Map<K, V> filtered(@Nonnull BiPredicate<K, V> filter, @Nonnull Iterable<Entry<K, V>> values) {
        return MapUtil.<K, V>builder().filtered(filter, values).build();
    }

    @Nonnull
    @SafeVarargs
    public static <K, V, RK, RV> Map<K, V> mapped(@Nonnull BiFunction<RK, RV, Entry<K, V>> mapping, @Nonnull Entry<RK, RV>... values) {
        return MapUtil.<K, V>builder().mapped(mapping, values).build();
    }

    @Nonnull
    public static <K, V, RK, RV> Map<K, V> mapped(@Nonnull BiFunction<RK, RV, Entry<K, V>> mapping, @Nonnull Iterable<Entry<RK, RV>> values) {
        return MapUtil.<K, V>builder().mapped(mapping, values).build();
    }

    public static class Builder<K, V> {
        private final Supplier<Map<?, ?>> mapSupplier;
        private final List<MapContents<K, V>> mapContents;

        private Builder(Supplier<Map<?, ?>> mapSupplier) {
            this.mapSupplier = mapSupplier;
            this.mapContents = new ArrayList<>();
        }

        @Nonnull
        public final Builder<K, V> add(@Nonnull K key, V value) {
            mapContents.add(map -> map.accept(key, value));
            return this;
        }

        @Nonnull
        @SafeVarargs
        public final Builder<K, V> all(@Nonnull Entry<K, V>... values) {
            return all(List.of(values));
        }

        @Nonnull
        public final Builder<K, V> all(@Nonnull Iterable<Entry<K, V>> values) {
            mapContents.add(map -> values.forEach(entry -> map.accept(entry.getKey(), entry.getValue())));
            return this;
        }

        @Nonnull
        public final Builder<K, V> all(@Nonnull Stream<Entry<K, V>> stream) {
            return all(() -> stream);
        }

        @Nonnull
        public final Builder<K, V> all(@Nonnull BiStream<K, V> stream) {
            mapContents.add(stream::forEach);
            return this;
        }

        @Nonnull
        public final Builder<K, V> fill(@Nonnull Consumer<MapBiConsumer<K, V>> filler) {
            mapContents.add(filler::accept);
            return this;
        }

//        @Nonnull
//        @SafeVarargs
//        public final Builder<K,V> nonnull(@Nonnull Entry<K, V>... values) {
//            return filtered(Objects::nonNull, values);
//        }

        @Nonnull
        @SafeVarargs
        public final Builder<K, V> keys(@Nonnull Function<K, V> mapping, @Nonnull K... keys) {
            return keys(mapping, List.of(keys));
        }

        @Nonnull
        public final Builder<K, V> keys(@Nonnull Function<K, V> mapping, @Nonnull Iterable<K> keys) {
            mapContents.add(map -> keys.forEach(key -> map.accept(key, mapping.apply(key))));
            return this;
        }

        @Nonnull
        @SafeVarargs
        public final Builder<K, V> values(@Nonnull Function<V, K> mapping, @Nonnull V... values) {
            return values(mapping, List.of(values));
        }

        @Nonnull
        public final Builder<K, V> values(@Nonnull Function<V, K> mapping, @Nonnull Iterable<V> values) {
            mapContents.add(map -> values.forEach(value -> map.accept(mapping.apply(value), value)));
            return this;
        }

        @Nonnull
        @SafeVarargs
        public final Builder<K, V> filtered(@Nonnull BiPredicate<K, V> filter, @Nonnull Entry<K, V>... values) {
            return all(stream(values).filter(filter));
        }

        @Nonnull
        public final Builder<K, V> filtered(@Nonnull BiPredicate<K, V> filter, @Nonnull Iterable<Entry<K, V>> values) {
            return all(stream(values).filter(filter));
        }

        @Nonnull
        public final Builder<K, V> filter(@Nonnull BiPredicate<K, V> filter) {
            return new Builder<K, V>(mapSupplier).fill(consumer -> {
                mapContents.forEach(content -> {
                    content.add((k, v) -> {
                        if (filter.test(k, v)) consumer.accept(k, v);
                    });
                });
            });
        }

        @Nonnull
        @SafeVarargs
        public final <RK, RV> Builder<K, V> mapped(@Nonnull BiFunction<RK, RV, Entry<K, V>> mapping, @Nonnull Entry<RK, RV>... values) {
            return all(stream(values).map(mapping));
        }

        @Nonnull
        public final <RK, RV> Builder<K, V> mapped(@Nonnull BiFunction<RK, RV, Entry<K, V>> mapping, @Nonnull Iterable<Entry<RK, RV>> values) {
            return all(stream(values).map(mapping));
        }

        @Nonnull
        public final <RK, RV> Builder<RK, RV> map(BiFunction<K, V, Entry<RK, RV>> mapping) {
            return new Builder<RK, RV>(mapSupplier).fill(consumer -> {
                mapContents.forEach(content -> {
                    content.add((k, v) -> consumer.accept(mapping.apply(k, v)));
                });
            });
        }

        @Nonnull
        @SuppressWarnings("unchecked")
        public final Map<K, V> build() {
            Map<K, V> map = (Map<K, V>) mapSupplier.get();
            mapContents.forEach(content -> content.add(map::put));
            return map;
        }

        private <K2, V2> BiStream<K2, V2> stream(Entry<K2, V2>[] values) {
            return () -> Stream.of(values);
        }

        private <K2, V2> BiStream<K2, V2> stream(Iterable<Entry<K2, V2>> iterable) {
            return () -> StreamSupport.stream(iterable.spliterator(), true);
        }

        @FunctionalInterface
        public interface MapContents<K, V> {
            void add(@Nonnull MapBiConsumer<K, V> map);
        }

        @FunctionalInterface
        public interface MapBiConsumer<K, V> extends BiConsumer<K, V> {
            void accept(@Nonnull K k, V v);

            default void accept(@Nonnull Entry<K, V> entry) {
                accept(entry.getKey(), entry.getValue());
            }
        }
    }
}
