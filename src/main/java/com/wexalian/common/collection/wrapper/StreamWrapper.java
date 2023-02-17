package com.wexalian.common.collection.wrapper;

import com.wexalian.nullability.annotations.Nonnull;

import java.util.Comparator;
import java.util.Iterator;
import java.util.Optional;
import java.util.Spliterator;
import java.util.function.*;
import java.util.stream.*;

@FunctionalInterface
public interface StreamWrapper<T> extends Stream<T> {
    @Nonnull
    Stream<T> get();
    
    @Override
    @Nonnull
    default Stream<T> filter(@Nonnull Predicate<? super T> predicate) {
        return get().filter(predicate);
    }
    
    @Override
    @Nonnull
    default <R> Stream<R> map(@Nonnull Function<? super T, ? extends R> mapper) {
        return get().map(mapper);
    }
    
    @Override
    @Nonnull
    default IntStream mapToInt(@Nonnull ToIntFunction<? super T> mapper) {
        return get().mapToInt(mapper);
    }
    
    @Override
    @Nonnull
    default LongStream mapToLong(@Nonnull ToLongFunction<? super T> mapper) {
        return get().mapToLong(mapper);
    }
    
    @Override
    @Nonnull
    default DoubleStream mapToDouble(@Nonnull ToDoubleFunction<? super T> mapper) {
        return get().mapToDouble(mapper);
    }
    
    @Override
    @Nonnull
    default <R> Stream<R> flatMap(@Nonnull Function<? super T, ? extends Stream<? extends R>> mapper) {
        return get().flatMap(mapper);
    }
    
    @Override
    @Nonnull
    default IntStream flatMapToInt(@Nonnull Function<? super T, ? extends IntStream> mapper) {
        return get().flatMapToInt(mapper);
    }
    
    @Override
    @Nonnull
    default LongStream flatMapToLong(@Nonnull Function<? super T, ? extends LongStream> mapper) {
        return get().flatMapToLong(mapper);
    }
    
    @Override
    @Nonnull
    default DoubleStream flatMapToDouble(@Nonnull Function<? super T, ? extends DoubleStream> mapper) {
        return get().flatMapToDouble(mapper);
    }
    
    @Override
    @Nonnull
    default Stream<T> distinct() {
        return get().distinct();
    }
    
    @Override
    @Nonnull
    default Stream<T> sorted() {
        return get().sorted();
    }
    
    @Override
    @Nonnull
    default Stream<T> sorted(@Nonnull Comparator<? super T> comparator) {
        return get().sorted(comparator);
    }
    
    @Override
    @Nonnull
    default Stream<T> peek(@Nonnull Consumer<? super T> action) {
        return get().peek(action);
    }
    
    @Override
    @Nonnull
    default Stream<T> limit(long maxSize) {
        return get().limit(maxSize);
    }
    
    @Override
    @Nonnull
    default Stream<T> skip(long n) {
        return get().skip(n);
    }
    
    @Override
    @Nonnull
    default void forEach(@Nonnull Consumer<? super T> action) {
        get().forEach(action);
    }
    
    @Override
    @Nonnull
    default void forEachOrdered(@Nonnull Consumer<? super T> action) {
        get().forEachOrdered(action);
    }
    
    @Override
    @Nonnull
    default Object[] toArray() {
        return get().toArray();
    }
    
    @Override
    @Nonnull
    default <A> A[] toArray(@Nonnull IntFunction<A[]> generator) {
        return get().toArray(generator);
    }
    
    @Override
    @Nonnull
    default T reduce(@Nonnull T identity, @Nonnull BinaryOperator<T> accumulator) {
        return get().reduce(identity, accumulator);
    }
    
    @Override
    @Nonnull
    default Optional<T> reduce(@Nonnull BinaryOperator<T> accumulator) {
        return get().reduce(accumulator);
    }
    
    @Override
    @Nonnull
    default <U> U reduce(@Nonnull U identity, @Nonnull BiFunction<U, ? super T, U> accumulator, @Nonnull BinaryOperator<U> combiner) {
        return get().reduce(identity, accumulator, combiner);
    }
    
    @Override
    @Nonnull
    default <R> R collect(@Nonnull Supplier<R> supplier, @Nonnull BiConsumer<R, ? super T> accumulator, @Nonnull BiConsumer<R, R> combiner) {
        return get().collect(supplier, accumulator, combiner);
    }
    
    @Override
    @Nonnull
    default <R, A> R collect(@Nonnull Collector<? super T, A, R> collector) {
        return get().collect(collector);
    }
    
    @Override
    @Nonnull
    default Optional<T> min(@Nonnull Comparator<? super T> comparator) {
        return get().min(comparator);
    }
    
    @Override
    @Nonnull
    default Optional<T> max(@Nonnull Comparator<? super T> comparator) {
        return get().max(comparator);
    }
    
    @Override
    @Nonnull
    default long count() {
        return get().count();
    }
    
    @Override
    @Nonnull
    default boolean anyMatch(@Nonnull Predicate<? super T> predicate) {
        return get().anyMatch(predicate);
    }
    
    @Override
    @Nonnull
    default boolean allMatch(@Nonnull Predicate<? super T> predicate) {
        return get().allMatch(predicate);
    }
    
    @Override
    @Nonnull
    default boolean noneMatch(@Nonnull Predicate<? super T> predicate) {
        return get().noneMatch(predicate);
    }
    
    @Override
    @Nonnull
    default Optional<T> findFirst() {
        return get().findFirst();
    }
    
    @Override
    @Nonnull
    default Optional<T> findAny() {
        return get().findAny();
    }
    
    @Override
    @Nonnull
    default Iterator<T> iterator() {
        return get().iterator();
    }
    
    @Override
    @Nonnull
    default Spliterator<T> spliterator() {
        return get().spliterator();
    }
    
    @Override
    @Nonnull
    default boolean isParallel() {
        return get().isParallel();
    }
    
    @Override
    @Nonnull
    default Stream<T> sequential() {
        return get().sequential();
    }
    
    @Override
    @Nonnull
    default Stream<T> parallel() {
        return get().parallel();
    }
    
    @Override
    @Nonnull
    default Stream<T> unordered() {
        return get().unordered();
    }
    
    @Override
    @Nonnull
    default Stream<T> onClose(@Nonnull Runnable closeHandler) {
        return get().onClose(closeHandler);
    }
    
    @Override
    @Nonnull
    default void close() {
        get().close();
    }
    
    @FunctionalInterface
    interface Iterable<T> extends StreamWrapper<T>, java.lang.Iterable<T> {
        @Nonnull
        @Override
        default void forEach(@Nonnull Consumer<? super T> action) {
            get().forEach(action);
        }
    
        @Nonnull
        @Override
        default Iterator<T> iterator() {
            return get().iterator();
        }
        
        @Nonnull
        @Override
        default Spliterator<T> spliterator() {
            return get().spliterator();
        }
    }
}
