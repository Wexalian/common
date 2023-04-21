package com.wexalian.common.collection.iterator;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.function.Predicate;

public class FilteredIterator<T> implements Iterator<T> {
    private final Iterator<T> iterator;
    private final Predicate<T> predicate;
    
    private T next;
    private boolean hasNext = true;
    
    public FilteredIterator(Iterator<T> iterator, Predicate<T> predicate) {
        this.iterator = iterator;
        this.predicate = predicate;
        
        next();
    }
    
    @Override
    public boolean hasNext() {
        return hasNext;
    }
    
    @Override
    public T next() {
        if (!hasNext) {
            throw new NoSuchElementException();
        }
        T old = next;
        while (iterator.hasNext()) {
            T o = iterator.next();
            if (predicate.test(o)) {
                hasNext = true;
                next = o;
                return old;
            }
        }
        hasNext = false;
        return old;
    }
}
