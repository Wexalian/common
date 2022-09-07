package com.wexalian.common.stream;

import java.util.*;
import java.util.function.*;
import java.util.stream.*;

@FunctionalInterface
public interface BiStream<K, V> {
    default BiStream<K, V> filter(BiPredicate<? super K, ? super V> mapper) {
        return of(entries().filter(e -> mapper.test(e.getKey(), e.getValue())));
    }
    
    Stream<Map.Entry<K, V>> entries();
    
    default BiStream<K, V> put(K key, V value) {
        Map<K, V> map = entries().collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
        map.put(key, value);
        return of(map);
    }
    
    default BiStream<K, V> filterKey(Predicate<? super K> mapper) {
        return of(entries().filter(e -> mapper.test(e.getKey())));
    }
    
    default BiStream<K, V> filterValue(Predicate<? super V> mapper) {
        return of(entries().filter(e -> mapper.test(e.getValue())));
    }
    
    default <R> Stream<R> map(BiFunction<? super K, ? super V, ? extends R> mapper) {
        return entries().map(e -> mapper.apply(e.getKey(), e.getValue()));
    }
    
    default <R> BiStream<R, V> mapKey(Function<? super K, ? extends R> mapper) {
        return of(entries().map(e -> new AbstractMap.SimpleImmutableEntry<>(mapper.apply(e.getKey()), e.getValue())));
    }
    
    default <R> BiStream<K, R> mapValue(Function<? super V, ? extends R> mapper) {
        return of(entries().map(e -> new AbstractMap.SimpleImmutableEntry<>(e.getKey(), mapper.apply(e.getValue()))));
    }
    
    default IntStream mapToInt(ToIntBiFunction<? super K, ? super V> mapper) {
        return entries().mapToInt(e -> mapper.applyAsInt(e.getKey(), e.getValue()));
    }
    
    default LongStream mapToLong(ToLongBiFunction<? super K, ? super V> mapper) {
        return entries().mapToLong(e -> mapper.applyAsLong(e.getKey(), e.getValue()));
    }
    
    default DoubleStream mapToDouble(ToDoubleBiFunction<? super K, ? super V> mapper) {
        return entries().mapToDouble(e -> mapper.applyAsDouble(e.getKey(), e.getValue()));
    }
    
    default <RK, RV> BiStream<RK, RV> flatMap(BiFunction<? super K, ? super V, ? extends BiStream<RK, RV>> mapper) {
        return of(entries().flatMap(e -> mapper.apply(e.getKey(), e.getValue()).entries()));
    }
    
    default IntStream flatMapToInt(BiFunction<? super K, ? super V, ? extends IntStream> mapper) {
        return entries().flatMapToInt(e -> mapper.apply(e.getKey(), e.getValue()));
    }
    
    default LongStream flatMapToLong(BiFunction<? super K, ? super V, ? extends LongStream> mapper) {
        return entries().flatMapToLong(e -> mapper.apply(e.getKey(), e.getValue()));
    }
    
    default DoubleStream flatMapToDouble(BiFunction<? super K, ? super V, ? extends DoubleStream> mapper) {
        return entries().flatMapToDouble(e -> mapper.apply(e.getKey(), e.getValue()));
    }
    
    default <R> Stream<R> flatMapToObj(BiFunction<? super K, ? super V, ? extends Stream<R>> mapper) {
        return entries().flatMap(e -> mapper.apply(e.getKey(), e.getValue()));
    }
    
    default BiStream<K, V> distinct() {
        return of(entries().distinct());
    }
    
    default BiStream<K, V> sortedByKey(Comparator<? super K> comparator) {
        return of(entries().sorted(Map.Entry.comparingByKey(comparator)));
    }
    
    default BiStream<K, V> sortedByValue(Comparator<? super V> comparator) {
        return of(entries().sorted(Map.Entry.comparingByValue(comparator)));
    }
    
    default BiStream<K, V> peek(BiConsumer<? super K, ? super V> action) {
        return of(entries().peek(e -> action.accept(e.getKey(), e.getValue())));
    }
    
    default BiStream<K, V> limit(long maxSize) {
        return of(entries().limit(maxSize));
    }
    
    default BiStream<K, V> skip(long n) {
        return of(entries().skip(n));
    }
    
    default void forEach(BiConsumer<? super K, ? super V> action) {
        entries().forEach(e -> action.accept(e.getKey(), e.getValue()));
    }
    
    default void forEachOrdered(BiConsumer<? super K, ? super V> action) {
        entries().forEachOrdered(e -> action.accept(e.getKey(), e.getValue()));
    }
    
    default Map<K, V> toMap() {
        return entries().collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }
    
    default Map<K, V> toMap(BinaryOperator<V> valAccum) {
        return entries().collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, valAccum));
    }
    
    default boolean allMatch(BiPredicate<? super K, ? super V> predicate) {
        return entries().allMatch(e -> predicate.test(e.getKey(), e.getValue()));
    }
    
    default boolean anyMatch(BiPredicate<? super K, ? super V> predicate) {
        return entries().anyMatch(e -> predicate.test(e.getKey(), e.getValue()));
    }
    
    default boolean noneMatch(BiPredicate<? super K, ? super V> predicate) {
        return entries().noneMatch(e -> predicate.test(e.getKey(), e.getValue()));
    }
    
    default long count() {
        return entries().count();
    }
    
    default Stream<K> keys() {
        return entries().map(Map.Entry::getKey);
    }
    
    default Stream<V> values() {
        return entries().map(Map.Entry::getValue);
    }
    
    default Optional<Map.Entry<K, V>> maxByKey(Comparator<? super K> comparator) {
        return entries().max(Map.Entry.comparingByKey(comparator));
    }
    
    default Optional<Map.Entry<K, V>> maxByValue(Comparator<? super V> comparator) {
        return entries().max(Map.Entry.comparingByValue(comparator));
    }
    
    default Optional<Map.Entry<K, V>> minByKey(Comparator<? super K> comparator) {
        return entries().min(Map.Entry.comparingByKey(comparator));
    }
    
    default Optional<Map.Entry<K, V>> minByValue(Comparator<? super V> comparator) {
        return entries().min(Map.Entry.comparingByValue(comparator));
    }
    
    static <K, V> BiStream<K, V> of(Map<K, V> map) {
        return of(map.entrySet().stream());
    }
    
    static <K, V> BiStream<K, V> of(List<K> list1, List<V> list2) {
        int size = Math.min(list1.size(), list2.size());
        Map<K, V> map = new HashMap<>();
        for (int i = 0; i < size; i++) {
            map.put(list1.get(i), list2.get(i));
        }
        return of(map);
    }
    
    static <K, V> BiStream<K, V> of(K[] array, List<V> list) {
        return of(List.of(array), list);
    }
    
    static <K, V> BiStream<K, V> of(Stream<Map.Entry<K, V>> stream) {
        return () -> stream;
    }
    
    static <K, V> BiStream<K, V> of(Stream<K> stream, Function<? super K, ? extends V> mapping) {
        return () -> stream.map(k -> new AbstractMap.SimpleImmutableEntry<>(k, mapping.apply(k)));
    }
}