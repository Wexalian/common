import com.wexalian.common.plugin.IAbstractPlugin;

module com.wexalian.common.test {
    requires com.wexalian.nullability;
    requires com.wexalian.common;

    requires org.junit.jupiter.api;

    opens com.wexalian.common.test.collection to org.junit.platform.commons;
    opens com.wexalian.common.test.collection.util to org.junit.platform.commons;
    opens com.wexalian.common.test.gson to com.google.gson, org.junit.platform.commons;
    opens com.wexalian.common.test.plugin to org.junit.platform.commons;
    opens com.wexalian.common.test.util to org.junit.platform.commons, com.google.gson;

    uses IAbstractPlugin;
}