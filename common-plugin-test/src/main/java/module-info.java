import com.wexalian.common.plugin.IAbstractPlugin;
import com.wexalian.common.plugin.test_impl.TestPlugin;

module com.wexalian.plugin.test {
    requires com.wexalian.nullability;
    requires com.wexalian.common;
    
    provides IAbstractPlugin with TestPlugin;
}