package com.wexalian.common.collection.util;

import com.wexalian.common.collection.iterator.ArrayIterator;
import com.wexalian.common.collection.iterator.FilteredIterator;
import com.wexalian.common.collection.iterator.MergedIterator;

import java.util.Iterator;
import java.util.function.Predicate;

public final class IteratorUtil {
    
    @SafeVarargs
    public static <T> Iterator<T> of(T... array) {
        return of(array, false);
    }
    
    public static <T> Iterator<T> of(T[] array, boolean reversed) {
        return new ArrayIterator<>(array, reversed);
    }
    
    public static <T> Iterator<T> filter(Iterator<T> iterator, Predicate<T> predicate) {
        return new FilteredIterator<>(iterator, predicate);
    }
    
    @SafeVarargs
    public static <T> Iterator<T> merge(Iterator<T>... iterators) {
        return new MergedIterator<>(of(iterators));
    }
    
    public static <T> Iterator<T> merge(Iterator<Iterator<T>> iterators) {
        return new MergedIterator<>(iterators);
    }
    
    public static <T> Iterator<T> merge(Iterable<Iterator<T>> iterators) {
        return new MergedIterator<>(iterators.iterator());
    }
}
