package com.wexalian.common.test.util;

import com.wexalian.common.util.SystemUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class SystemUtilTests {
    
    @Test
    public void testOs() {
        assertTrue(SystemUtil.IS_WINDOWS, "os not windows");
        assertFalse(SystemUtil.IS_MAC, "os not mac osx");
    }
}
