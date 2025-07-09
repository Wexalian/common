import com.wexalian.common.gson.adapter.IGsonTypeAdapter;
import com.wexalian.common.gson.adapter.impl.BuiltinTypeAdapterRegistry;

module com.wexalian.common {
    requires java.logging;

    requires com.google.gson;
    requires com.wexalian.nullability;

    exports com.wexalian.common.collection.iterator;
    exports com.wexalian.common.collection.pair;
    exports com.wexalian.common.collection.util;
    exports com.wexalian.common.collection.wrapper;
    exports com.wexalian.common.function;
    exports com.wexalian.common.gson;
    exports com.wexalian.common.gson.adapter;
    exports com.wexalian.common.http;
    exports com.wexalian.common.plugin;
    exports com.wexalian.common.property;
    exports com.wexalian.common.stream;
    exports com.wexalian.common.unchecked;
    exports com.wexalian.common.util;
    exports com.wexalian.common.util.misc;

    opens com.wexalian.common.gson to com.google.gson;
    opens com.wexalian.common.gson.adapter to com.google.gson;
    opens com.wexalian.common.gson.adapter.impl to com.google.gson;

    uses IGsonTypeAdapter.Registry;

    provides IGsonTypeAdapter.Registry with BuiltinTypeAdapterRegistry;
}