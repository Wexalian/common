package com.wexalian.common.collection.iterator;

import com.wexalian.nullability.annotations.Nullable;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class ArrayIterator<T> implements Iterator<T> {
    private final T[] array;
    private final boolean reversed;
    
    private boolean hasNext;
    private int index;
    
    public ArrayIterator(T[] array, boolean reversed) {
        this.array = array;
        this.reversed = reversed;
        this.index = reversed ? array.length - 1 : 0;
    }
    
    @Override
    public boolean hasNext() {
        return hasNext = index >= 0 && index < array.length;
    }
    
    @Override
    @Nullable
    public T next() {
        if (!hasNext) throw new NoSuchElementException();
        return array[reversed ? index-- : index++];
    }
}
