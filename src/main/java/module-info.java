module com.wexalian.common {
    requires java.logging;
    
    requires com.wexalian.nullability;
    
    requires com.google.gson;
    
    exports com.wexalian.common.gson;
    exports com.wexalian.common.lazy;
    exports com.wexalian.common.stream;
    exports com.wexalian.common.unchecked;
    exports com.wexalian.common.util;
    
    opens com.wexalian.common.gson to com.google.gson;
}