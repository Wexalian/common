package com.wexalian.common.util;

public class SystemUtil {
    public static final String OPERATING_SYSTEM = System.getProperty("os.name");
    
    public static final boolean IS_WINDOWS = doesOsStartWith("Windows");
    public static final boolean IS_MAC_OSX = doesOsStartWith("Mac OS X");
    
    public static boolean doesOsStartWith(String prefix) {
        return OPERATING_SYSTEM.startsWith(prefix);
    }
}
