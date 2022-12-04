package com.wexalian.common.util;

import com.wexalian.nullability.annotations.Nonnull;
import org.junit.jupiter.api.Test;

import java.util.Collection;
import java.util.EnumSet;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ManifoldExtensionTests {
    private static final Collection<TestEnum> ENUM_SET = EnumSet.allOf(TestEnum.class);
    
    @Test
    void format() {
        assertEquals("Value: 4", "Value: {}".formatting(4));
        assertEquals("Value: 1.00", "Value: {%.2f}".formatting(1F));
    }
    
    @Test
    void join() {
        assertEquals("", List.of().join(", "));
        assertEquals("VALUE_1, VALUE_2, VALUE_3", ENUM_SET.join(", "));
        assertEquals("First, Second, Third", ENUM_SET.join(", ", TestEnum::getFormatted));
    }
    
    @Test
    void slug() {
        assertEquals("smoaeeek_ceala", "Sm\u00F6\u00E5\u00C9\u00E8\u00EAk     \u00E7\u00E8\u00E0L\u00E0".slug());
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
