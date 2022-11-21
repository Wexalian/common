package com.wexalian.common.collection.iterator;

import com.wexalian.nullability.annotations.Nonnull;

import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class ReverseListIterator<T> implements ListIterator<T>, Iterable<T> {
    public final List<T> backingList;
    public ListIterator<T> iterator;
    public boolean validForUpdate = true;
    
    private ReverseListIterator(@Nonnull List<T> backingList) {
        this.backingList = backingList;
        reset();
    }
    
    @Override
    public boolean hasNext() {
        return iterator.hasPrevious();
    }
    
    @Override
    public T next() {
        T previous = iterator.previous();
        validForUpdate = true;
        return previous;
    }
    
    @Override
    public boolean hasPrevious() {
        return iterator.hasNext();
    }
    
    @Override
    public T previous() {
        T next = iterator.next();
        validForUpdate = true;
        return next;
    }
    
    @Override
    public int nextIndex() {
        return iterator.previousIndex();
    }
    
    @Override
    public int previousIndex() {
        return iterator.nextIndex();
    }
    
    @Override
    public void remove() {
        if (!validForUpdate) {
            throw new IllegalStateException("Cannot remove from list until next() or previous() called");
        }
        iterator.remove();
    }
    
    @Override
    public void set(T value) {
        if (!validForUpdate) {
            throw new IllegalStateException("Cannot set to list until next() or previous() called");
        }
        iterator.set(value);
    }
    
    @Override
    public void add(T value) {
        if (!validForUpdate) {
            throw new IllegalStateException("Cannot add to list until next() or previous() called");
        }
        validForUpdate = false;
        iterator.add(value);
        iterator.previous();
    }
    
    public void reset() {
        iterator = backingList.listIterator(backingList.size());
    }
    
    @Override
    public Iterator<T> iterator() {
        return this;
    }
    
    @Nonnull
    public static <T> ReverseListIterator<T> of(@Nonnull List<T> list) {
        return new ReverseListIterator<>(list);
    }
}
