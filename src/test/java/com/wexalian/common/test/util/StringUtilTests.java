package com.wexalian.common.test.util;

import com.wexalian.common.util.StringUtil;
import com.wexalian.nullability.annotations.Nonnull;
import org.junit.jupiter.api.Test;

import java.util.Collection;
import java.util.EnumSet;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class StringUtilTests {
    private static final Collection<TestEnum> ENUM_SET = EnumSet.allOf(TestEnum.class);
    public static final String TEST_SLUG_1 = "SmöåÉèêk     çèàLà";
    public static final String TEST_SLUG_1_SLUG = "smoaeeek_ceala";
    public static final String TEST_SLUG_1_ESCAPED = "Sm\u00F6\u00E5\u00C9\u00E8\u00EAk     \u00E7\u00E8\u00E0L\u00E0";
    
    public static final String TEST_SLUG_2 = "āăąēîïĩíĝġńñšŝśûůŷ";
    public static final String TEST_SLUG_2_SLUG = "aaaeiiiiggnnsssuuy";
    public static final String TEST_SLUG_2_ESCAPED = "\u0101\u0103\u0105\u0113\u00EE\u00EF\u0129\u00ED\u011D\u0121\u0144\u00F1\u0161\u015D\u015B\u00FB\u016F\u0177";
    
    @Test
    void isBlank() {
        assertTrue(StringUtil.isBlank(""));
        assertFalse(StringUtil.isBlank("Test"));
    }
    
    @Test
    void format() {
        assertEquals("Value: 4", StringUtil.format("Value: {}", 4));
        assertEquals("Value: 1.00", StringUtil.format("Value: {%.2f}", 1F));
        assertEquals("Value: $5", StringUtil.format("Value: {}", "$5"));
    }
    
    @Test
    void join() {
        assertEquals("", StringUtil.join(List.of(), ", "));
        assertEquals("VALUE_1, VALUE_2, VALUE_3", StringUtil.join(ENUM_SET, ", "));
        assertEquals("First, Second, Third", StringUtil.join(ENUM_SET, ", ", TestEnum::getFormatted));
    }
    
    @Test
    void slug() {
        assertEquals(TEST_SLUG_1_SLUG, StringUtil.slug(TEST_SLUG_1));
        assertEquals(TEST_SLUG_1_SLUG, StringUtil.slug(TEST_SLUG_1_ESCAPED));
        
        assertEquals(TEST_SLUG_2_SLUG, StringUtil.slug(TEST_SLUG_2));
        assertEquals(TEST_SLUG_2_SLUG, StringUtil.slug(TEST_SLUG_2_ESCAPED));
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
