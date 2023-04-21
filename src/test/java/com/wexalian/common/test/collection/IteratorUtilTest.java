package com.wexalian.common.test.collection;

import com.wexalian.common.collection.util.IteratorUtil;
import com.wexalian.common.collection.util.ListUtil;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import java.util.Iterator;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class IteratorUtilTest {
    private static final String[] TEST_STRINGS = new String[]{"One", "Two", "Three", "Four", "Five", "Six", "Seven", "Eight", "Nine", "Ten"};
    
    private static final Iterator<String> TEST_STRINGS_ITERATOR = IteratorUtil.of(TEST_STRINGS);
    private static final Iterator<Integer> TEST_NUMBERS_ITERATOR = IteratorUtil.of(0, 1, 2, 3, 4, 5, 6, 7, 8, 9);
    private static final Iterator<String> TEST_NUMBER_STRINGS_ITERATOR = ListUtil.map(() -> TEST_NUMBERS_ITERATOR, String::valueOf).iterator();
    
    @Test
    @Order(1)
    void testArrayIterator() {
        Iterator<String> it = IteratorUtil.of(TEST_STRINGS, false);
        printIterator(it, "ArrayIterator");
    }
    
    @Test
    @Order(2)
    void testArrayIteratorReverse() {
        Iterator<String> it = IteratorUtil.of(TEST_STRINGS, true);
        printIterator(it, "ArrayIteratorReverse");
    }
    
    @Test
    @Order(3)
    void testFilteredStringsIterator() {
        Iterator<String> it = IteratorUtil.filter(TEST_STRINGS_ITERATOR, (String s) -> s.length() % 2 == 0);
        printIterator(it, "FilteredNumbersIterator");
    }
    
    @Test
    @Order(4)
    void testFilteredNumbersIterator() {
        Iterator<Integer> it = IteratorUtil.filter(TEST_NUMBERS_ITERATOR, (Integer i) -> i % 2 == 0);
        printIterator(it, "FilteredNumbersIterator");
    }
    
    @Test
    @Order(5)
    void testMergedIterators() {
        Iterator<String> it = IteratorUtil.merge(TEST_STRINGS_ITERATOR, TEST_NUMBER_STRINGS_ITERATOR);
        printIterator(it, "MergedIterator");
    }
    
    private void printIterator(Iterator<?> it, String name) {
        System.out.println(name);
        while (it.hasNext()) {
            System.out.println("\t" + it.next());
        }
        System.out.println("--------------------");
    }
}
