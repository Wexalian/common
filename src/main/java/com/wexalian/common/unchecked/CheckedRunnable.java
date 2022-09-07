package com.wexalian.common.unchecked;

@FunctionalInterface
public interface CheckedRunnable extends Runnable {
    void runUnchecked() throws Exception;
    
    @Override
    default void run() {
        Unchecked.run(this);
    }
}