package com.wexalian.common.util;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class SystemUtilTests {
    
    @Test
    public void testOs() {
        assertTrue(SystemUtil.IS_WINDOWS, "os not windows");
        assertFalse(SystemUtil.IS_MAC_OSX, "os not mac osx");
    }
}
