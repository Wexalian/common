package com.wexalian.common.unchecked;

import com.wexalian.common.util.StringUtil;

import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.logging.Level;
import java.util.logging.Logger;

public final class Unchecked {
    private static final Logger LOGGER = Logger.getLogger("com.wexalian.common.unchecked");
    
    private static Consumer<Exception> defaultExceptionHandler = e -> {
        String error = StringUtil.format("Swallowed unchecked exception of type '{}'", e.getClass().getName());
        LOGGER.log(Level.SEVERE, error, e);
    };
    
    public static void setDefaultExceptionHandler(Consumer<Exception> exceptionHandler) {
        Unchecked.defaultExceptionHandler = exceptionHandler;
    }
    
    public static void run(CheckedRunnable runnable) {
        try {
            runnable.runUnchecked();
        }
        catch (Exception e) {
            defaultExceptionHandler.accept(e);
        }
    }
    
    public static <I> void accept(I input, CheckedConsumer<I> consumer) {
        try {
            consumer.acceptUnchecked(input);
        }
        catch (Exception e) {
            defaultExceptionHandler.accept(e);
        }
    }
    
    public static <I1, I2> void accept(I1 input1, I2 input2, CheckedBiConsumer<I1, I2> biConsumer) {
        try {
            biConsumer.acceptUnchecked(input1, input2);
        }
        catch (Exception e) {
            defaultExceptionHandler.accept(e);
        }
    }
    
    public static <O> O get(CheckedSupplier<O> supplier) {
        return get(supplier, null);
    }
    
    public static <O> O get(CheckedSupplier<O> supplier, O defaultValue) {
        try {
            return supplier.getUnchecked();
        }
        catch (Exception e) {
            defaultExceptionHandler.accept(e);
        }
        return defaultValue;
    }
    
    public static <I, O> O apply(I input, CheckedFunction<I, O> function) {
        return apply(input, function, (O) null);
    }
    
    public static <I, O> O apply(I input, CheckedFunction<I, O> function, O defaultValue) {
        try {
            return function.applyUnchecked(input);
        }
        catch (Exception e) {
            defaultExceptionHandler.accept(e);
        }
        return defaultValue;
    }
    
    public static <I1, I2, O> O apply(I1 input1, I2 input2, CheckedBiFunction<I1, I2, O> function) {
        return apply(input1, input2, function, null);
    }
    
    public static <I1, I2, O> O apply(I1 input1, I2 input2, CheckedBiFunction<I1, I2, O> function, O defaultValue) {
        try {
            return function.applyUnchecked(input1, input2);
        }
        catch (Exception e) {
            defaultExceptionHandler.accept(e);
        }
        return defaultValue;
    }
    
    public static Runnable wrap(CheckedRunnable runnable) {
        return runnable;
    }
    
    public static <I> Consumer<I> wrap(CheckedConsumer<I> consumer) {
        return consumer;
    }
    
    public static <O> Supplier<O> wrap(CheckedSupplier<O> supplier) {
        return supplier;
    }
    
    public static <I, O> Function<I, O> wrap(CheckedFunction<I, O> function) {
        return function;
    }
    
    public static <I1, I2, O> BiFunction<I1, I2, O> wrap(CheckedBiFunction<I1, I2, O> function) {
        return function;
    }
}
