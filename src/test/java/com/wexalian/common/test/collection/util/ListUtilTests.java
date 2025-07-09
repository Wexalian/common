package com.wexalian.common.test.collection.util;

import com.wexalian.common.collection.util.ListUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class ListUtilTests {
    private static final String ZERO = "Zero";
    private static final String ONE = "One";
    private static final String TWO = "Two";
    private static final String THREE = "Three";
    private static final String FOUR = "Four";
    private static final String FIVE = "Five";
    private static final String SIX = "Six";
    private static final String SEVEN = "Seven";
    private static final String EIGHT = "Eight";
    private static final String NINE = "Nine";


    private static final String[] ARRAY_01234 = {ZERO, ONE, TWO, THREE, FOUR};
    private static final String[] ARRAY_56789 = {FIVE, SIX, SEVEN, EIGHT, NINE};


    private ListUtil.Builder<String> builder;

    @BeforeEach
    void before() {
        builder = ListUtil.builder();
    }

    @Test
    void testBuilderAdd() {
        builder.add(ZERO);

        assertListEquals(builder.build(), ZERO);
    }

    @Test
    void testBuilderAllArray() {
        builder.all(ARRAY_01234);

        assertListEquals(builder.build(), ARRAY_01234);
    }

    @Test
    void testBuilderAllIterable() {
        builder.all(List.of(ARRAY_01234));

        assertListEquals(builder.build(), ARRAY_01234);
    }

    @Test
    void testBuilderAllStream() {
        builder.all(Stream.of(ARRAY_01234));

        assertListEquals(builder.build(), ARRAY_01234);
    }

    @Test
    void testBuilderFill() {
        builder.fill(consumer -> {
            for (String number : ARRAY_01234) {
                consumer.accept(number);
            }
        });

        assertListEquals(builder.build(), ARRAY_01234);
    }

    @Test
    void testBuilderFiltered() {
        builder.filtered(s -> s.length() % 2 == 0, ARRAY_56789);

        assertListEquals(builder.build(), FIVE,NINE);
    }

    @Test
    void testBuilderFilter() {
        builder.all(ARRAY_56789).filter(s -> s.length() % 2 != 0);

        assertListEquals(builder.build(), SIX,SEVEN,EIGHT);
    }

    @Test
    void testBuilderMapped() {
        builder.mapped(String::toLowerCase, ARRAY_01234);

        assertListEquals(builder.build(), "zero", "one", "two", "three", "four");
    }

    @Test
    void testBuilderMap() {
        ListUtil.Builder<Integer> newBuilder = builder.all(ARRAY_56789).map(String::length);

        assertListEquals(newBuilder.build(), 4, 3, 5, 5, 4);
    }

    @SafeVarargs
    private static <T> void assertListEquals(List<T> list, T... expected) {
        assertListEquals(list, List.of(expected));
    }

    private static <T> void assertListEquals(List<T> list, List<T> collection) {
        assertNotNull(list, "list is null");
        assertEquals(collection.size(), list.size(), "list does not match expected size");

        int index = -1;
        for (T t : list) {
            assertEquals(t, collection.get(++index), "list value at index " + index + " does not match expected value '" + collection.get(index) + "'");
        }
    }

}
