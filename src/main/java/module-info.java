module com.wexalian.common {
    requires com.google.gson;
    requires com.wexalian.nullability;
    
    requires java.logging;
    
    exports com.wexalian.common.collection.iterator;
    exports com.wexalian.common.collection.pair;
    exports com.wexalian.common.collection.util;
    exports com.wexalian.common.collection.wrapper;
    exports com.wexalian.common.gson;
    exports com.wexalian.common.plugin;
    exports com.wexalian.common.property;
    exports com.wexalian.common.stream;
    exports com.wexalian.common.unchecked;
    exports com.wexalian.common.util;
    exports com.wexalian.common.util.misc;
    
    opens com.wexalian.common.gson to com.google.gson;
}