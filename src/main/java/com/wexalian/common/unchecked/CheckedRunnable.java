package com.wexalian.common.unchecked;

@Deprecated()
@FunctionalInterface
public interface CheckedRunnable extends Runnable {
    void runUnchecked() throws Exception;

    @Override
    default void run() {
        Unchecked.run(this);
    }
}