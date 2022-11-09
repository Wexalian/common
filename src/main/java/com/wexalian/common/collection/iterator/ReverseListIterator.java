package com.wexalian.common.collection.iterator;

import com.wexalian.nullability.annotations.Nonnull;

import java.util.List;
import java.util.ListIterator;

public class ReverseListIterator<T> implements ListIterator<T> {
    public final List<T> backingList;
    public ListIterator<T> iterator;
    public boolean validForUpdate = true;
    public boolean isReset = true;
    
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
        isReset = false;
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
        isReset = false;
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
        isReset = false;
    }
    
    @Override
    public void set(T value) {
        if (!validForUpdate) {
            throw new IllegalStateException("Cannot set to list until next() or previous() called");
        }
        iterator.set(value);
        isReset = false;
    }
    
    @Override
    public void add(T value) {
        if (!validForUpdate) {
            throw new IllegalStateException("Cannot add to list until next() or previous() called");
        }
        iterator.add(value);
        iterator.previous();
        validForUpdate = false;
        isReset = false;
    }
    
    public void reset() {
        if (!isReset) {
            iterator = backingList.listIterator(backingList.size());
            isReset = true;
        }
    }
    
    @Nonnull
    public static <T> ReverseListIterator<T> of(@Nonnull List<T> list) {
        return new ReverseListIterator<>(list);
    }
}
