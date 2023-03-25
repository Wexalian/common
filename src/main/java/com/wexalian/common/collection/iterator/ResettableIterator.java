package com.wexalian.common.collection.iterator;

import java.util.Iterator;

public interface ResettableIterator<T> extends Iterator<T> {
    void reset();
}
