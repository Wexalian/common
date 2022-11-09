package com.wexalian.common.collection.iterator;

import com.wexalian.nullability.annotations.Nonnull;

import java.util.List;
import java.util.ListIterator;

public class ReverseListIterator<T> implements ListIterator<T> {
    public final List<T> backingList;
    public ListIterator<T> iterator;
    public boolean validForUpdate = true;
    public boolean changed = true;
    
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
        changed = true;
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
        changed = true;
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
        changed = true;
    }
    
    @Override
    public void set(T value) {
        if (!validForUpdate) {
            throw new IllegalStateException("Cannot set to list until next() or previous() called");
        }
        iterator.set(value);
        changed = true;
    }
    
    @Override
    public void add(T value) {
        if (!validForUpdate) {
            throw new IllegalStateException("Cannot add to list until next() or previous() called");
        }
        iterator.add(value);
        iterator.previous();
        validForUpdate = false;
        changed = true;
    }
    
    public void reset() {
        if (changed) {
            iterator = backingList.listIterator(backingList.size());
            changed = false;
        }
    }
}
