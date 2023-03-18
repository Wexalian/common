package com.wexalian.common.collection.wrapper;

import com.wexalian.nullability.annotations.Nonnull;
import com.wexalian.nullability.annotations.Nullable;

import java.util.*;
import java.util.function.Function;

@FunctionalInterface
public interface ListWrapper<T> extends List<T> {
    
    @Nonnull
    List<T> get();
    
    @Override
    default int size() {
        return get().size();
    }
    
    @Override
    default boolean isEmpty() {
        return get().isEmpty();
    }
    
    @Override
    default boolean contains(@Nonnull Object object) {
        return get().contains(object);
    }
    
    @Override
    @Nonnull
    default Iterator<T> iterator() {
        return get().iterator();
    }
    
    @Override
    @Nonnull
    default Object[] toArray() {
        return get().toArray();
    }
    
    @Override
    @Nonnull
    @SuppressWarnings("SuspiciousToArrayCall")
    default <T1> T1[] toArray(T1[] a) {
        return get().toArray(a);
    }
    
    @Override
    default boolean add(@Nonnull T t) {
        return get().add(t);
    }
    
    @Override
    default boolean remove(@Nonnull Object object) {
        return get().remove(object);
    }
    
    @Override
    default boolean containsAll(@Nonnull Collection<?> collection) {
        return new HashSet<>(get()).containsAll(collection);
    }
    
    @Override
    default boolean addAll(@Nonnull Collection<? extends T> collection) {
        return get().addAll(collection);
    }
    
    @Override
    default boolean addAll(int index, @Nonnull Collection<? extends T> collection) {
        return get().addAll(index, collection);
    }
    
    @Override
    default boolean removeAll(@Nonnull Collection<?> collection) {
        return get().removeAll(collection);
    }
    
    @Override
    default boolean retainAll(@Nonnull Collection<?> collection) {
        return get().retainAll(collection);
    }
    
    @Override
    default void clear() {
        get().clear();
    }
    
    @Override
    @Nullable
    default T get(int index) {
        return get().get(index);
    }
    
    @Override
    @Nullable
    default T set(int index, @Nonnull T element) {
        return get().set(index, element);
    }
    
    @Override
    default void add(int index, @Nonnull T element) {
        get().add(index, element);
    }
    
    @Override
    @Nullable
    default T remove(int index) {
        return get().remove(index);
    }
    
    @Override
    default int indexOf(@Nonnull Object object) {
        return get().indexOf(object);
    }
    
    @Override
    default int lastIndexOf(@Nonnull Object object) {
        return get().lastIndexOf(object);
    }
    
    @Override
    @Nonnull
    default ListIterator<T> listIterator() {
        return get().listIterator();
    }
    
    @Override
    @Nonnull
    default ListIterator<T> listIterator(int index) {
        return get().listIterator(index);
    }
    
    @Override
    @Nonnull
    default List<T> subList(int fromIndex, int toIndex) {
        return get().subList(fromIndex, toIndex);
    }
    
    default <R> List<R> as(Function<T, R> mapping) {
        return stream().map(mapping).toList();
    }
    
    static <T> ListWrapper<T> wrap(List<T> list) {
        if (list instanceof ListWrapper<T> wrapper) return wrapper;
        return () -> list;
    }
}
