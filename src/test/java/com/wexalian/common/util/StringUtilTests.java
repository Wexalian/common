package com.wexalian.common.util;

import com.wexalian.nullability.annotations.Nonnull;
import org.junit.jupiter.api.Test;

import java.util.Collection;
import java.util.EnumSet;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class StringUtilTests {
    private static final Collection<TestEnum> ENUM_SET = EnumSet.allOf(TestEnum.class);
    
    
    @Test
    @SuppressWarnings("ConstantConditions")
    void isNull() {
        assertTrue(StringUtil.isNull(null));
        assertFalse(StringUtil.isNull(""));
        assertFalse(StringUtil.isNull("Test"));
    }
    
    @Test
    void isBlank() {
        assertTrue(StringUtil.isBlank(""));
        assertFalse(StringUtil.isBlank("Test"));
    }
    
    @Test
    void isNullOrBlank() {
        assertTrue(StringUtil.isNullOrBlank(null));
        assertTrue(StringUtil.isNullOrBlank(""));
        assertFalse(StringUtil.isNullOrBlank("Test"));
    }
    
    @Test
    void format() {
        assertEquals("Value: 4", StringUtil.format("Value: {}", 4));
    }
    
    @Test
    void join() {
        assertEquals("", StringUtil.join(List.of(), ", "));
        assertEquals("VALUE_1, VALUE_2, VALUE_3", StringUtil.join(ENUM_SET, ", "));
        assertEquals("First, Second, Third", StringUtil.join(ENUM_SET, ", ", TestEnum::getFormatted));
    }
    
    @Test
    void slug() {
        assertEquals("smoaeeek_ceala", StringUtil.slug("Sm\u00F6\u00E5\u00C9\u00E8\u00EAk     \u00E7\u00E8\u00E0L\u00E0"));
    }
    
    private enum TestEnum {
        VALUE_1("First"),
        VALUE_2("Second"),
        VALUE_3("Third");
        
        private final String formatted;
        
        TestEnum(String formatted) {
            this.formatted = formatted;
        }
        
        @Nonnull
        public String getFormatted() {
            return formatted;
        }
    }
}
