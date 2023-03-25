package com.wexalian.common.collection.iterator;

import com.wexalian.nullability.annotations.Nonnull;
import com.wexalian.nullability.annotations.Nullable;

import java.util.Iterator;

public class ArrayIterator<T> implements ResettableIterator<T>, Iterable<T> {
    protected final T[] array;
    protected int index;
    
    private ArrayIterator(T[] array) {
        this.array = array;
        reset();
    }
    
    @Override
    public boolean hasNext() {
        return index >= 0 && index < array.length;
    }
    
    @Override
    @Nullable
    public T next() {
        return array[index++];
    }
    
    @Override
    @Nonnull
    public Iterator<T> iterator() {
        return this;
    }
    
    @Override
    public void reset() {
        index = 0;
    }
    
    public static <T> ArrayIterator<T> of(T[] array) {
        return new ArrayIterator<>(array);
    }
    
    public static <T> ArrayIterator<T> reverse(T[] array) {
        return new Reversed<>(array);
    }
    
    private static final class Reversed<T> extends ArrayIterator<T> {
        private Reversed(T[] array) {
            super(array);
        }
        
        @Override
        public T next() {
            return array[index--];
        }
        
        @Override
        public void reset() {
            index = array.length - 1;
        }
    }
}
