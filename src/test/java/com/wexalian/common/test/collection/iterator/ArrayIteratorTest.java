package com.wexalian.common.test.collection.iterator;

import com.wexalian.common.collection.iterator.ArrayIterator;
import org.junit.jupiter.api.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ArrayIteratorTest {
    
    private static final String[] TEST_ARRAY = new String[]{"One", "Two", "Three", "Four", "Five"};
    
    @Test
    @Order(1)
    void testArrayIterator() {
        System.out.println("ArrayIterator");
        ArrayIterator<String> it = ArrayIterator.of(TEST_ARRAY);
        while (it.hasNext()) {
            System.out.println("\t" + it.next());
        }
    }
    
    @Test
    @Order(2)
    void testArrayIterable() {
        System.out.println("ArrayIterator Iterable");
        for (String value : ArrayIterator.of(TEST_ARRAY)) {
            System.out.println("\t" + value);
        }
    }
    
    @Test
    @Order(3)
    void testArrayIteratorReverse() {
        System.out.println("ArrayIterator.Reverse");
        ArrayIterator<String> it = ArrayIterator.reverse(TEST_ARRAY);
        while (it.hasNext()) {
            System.out.println("\t" + it.next());
        }
    }
    
    @Test
    @Order(4)
    void testArrayIterableReverse() {
        System.out.println("ArrayIterator.Reverse Iterable");
        for (String value : ArrayIterator.reverse(TEST_ARRAY)) {
            System.out.println("\t" + value);
        }
    }
    
    @AfterEach
    void after() {
        System.out.println("--------------------");
    }
}
