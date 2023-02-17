package com.wexalian.common.test.collection.iterator;

import com.wexalian.common.collection.iterator.ReverseListIterator;
import org.junit.jupiter.api.Test;

import java.util.List;

public class ReverseListIteratorTest {
    
    private static final List<String> TEST_LIST = List.of("One", "Two", "Three", "Four", "Five");
    
    @Test
    public void testReverseListIterator() {
        for (String value : TEST_LIST) {
            System.out.println(value);
        }
        for (String value : ReverseListIterator.of(TEST_LIST)) {
            System.out.println(value);
        }
    }
}
