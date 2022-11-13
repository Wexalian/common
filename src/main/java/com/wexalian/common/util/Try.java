package com.wexalian.common.util;

import com.wexalian.nullability.annotations.Nonnull;
import com.wexalian.nullability.function.NonnullSupplier;

public abstract class Try<T> {
    
    private Try() {}
    
    @Nonnull
    public abstract T get();
    
    @Nonnull
    public abstract Throwable getCause();
    
    public abstract boolean isSuccess();
    
    public abstract boolean isFailure();
    
    public static <T> Try<T> of(@Nonnull NonnullSupplier<T> supplier) {
        try {
            return new Success<>(supplier.get());
        }
        catch (Throwable throwable) {
            return new Failure<>(throwable);
        }
    }
    
    private static class Success<T> extends Try<T> {
        
        private final T result;
        
        public Success(T result) {
            this.result = result;
        }
        
        @Nonnull
        @Override
        public T get() {
            return result;
        }
        
        @Nonnull
        @Override
        public Throwable getCause() {
            throw new IllegalStateException("Can't call getCause on a success");
        }
        
        @Override
        public boolean isSuccess() {
            return true;
        }
        
        @Override
        public boolean isFailure() {
            return false;
        }
    }
    
    private static class Failure<T> extends Try<T> {
        private final Throwable cause;
        
        public Failure(Throwable cause) {
            this.cause = cause;
        }
        
        @Nonnull
        @Override
        public T get() {
            throw new IllegalStateException("Can't call get on a failure");
        }
        
        @Nonnull
        @Override
        public Throwable getCause() {
            return cause;
        }
        
        @Override
        public boolean isSuccess() {
            return false;
        }
        
        @Override
        public boolean isFailure() {
            return true;
        }
    }
}
