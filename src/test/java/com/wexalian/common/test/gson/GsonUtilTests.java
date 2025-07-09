package com.wexalian.common.test.gson;

import com.wexalian.common.gson.GsonUtil;
import com.wexalian.nullability.annotations.Nonnull;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

public class GsonUtilTests {
    private static final String TEST_ENUM_JSON = """
                                                 [
                                                   "FIRST",
                                                   "SECOND",
                                                   "THIRD"
                                                 ]
                                                 """;
    
    @Test
    void testFromJson() {
        TestEnum[] test = GsonUtil.fromJson(TEST_ENUM_JSON, TestEnum[].class);
        System.out.println(Arrays.toString(test));
    }
    
    @Test
    void testToJson() {
        String json = GsonUtil.toJson(TestEnum.values());
        System.out.println(json);
    }
    
    private enum TestEnum {
        FIRST("First"),
        SECOND("Second"),
        THIRD("Third");
        
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
