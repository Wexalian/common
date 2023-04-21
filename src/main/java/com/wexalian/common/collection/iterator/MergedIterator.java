package com.wexalian.common.collection.iterator;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class MergedIterator<T> implements Iterator<T> {
    private final Iterator<Iterator<T>> iterators;
    
    private Iterator<T> iterator;
    
    private T next;
    private boolean hasNext = true;
    
    public MergedIterator(Iterator<Iterator<T>> iterators) {
        this.iterators = iterators;
        
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
        
        while (iterator == null || !iterator.hasNext()) {
            if (iterators.hasNext()) {
                iterator = iterators.next();
            }
            else {
                hasNext = false;
                return old;
            }
        }
        hasNext = true;
        next = iterator.next();
        return old;
    }
}
