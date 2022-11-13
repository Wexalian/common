package com.wexalian.common.util;

import com.wexalian.nullability.annotations.Nonnull;
import com.wexalian.nullability.function.NonnullConsumer;
import com.wexalian.nullability.function.NonnullSupplier;

public abstract class Try<T> extends Either<T, Throwable>{
    
    private Try(T value, Throwable cause) {
        super(value, cause);
    }
    
    @Nonnull
    public abstract T get();
    
    @Nonnull
    public abstract Throwable getCause();
    
    public abstract boolean isSuccess();
    
    public abstract boolean isFailure();
    
    public abstract void onSuccess(@Nonnull NonnullConsumer<T> consumer);
    
    public abstract void onFailure(@Nonnull NonnullConsumer<Throwable> consumer);
    
    
    public static <T> Try<T> of(@Nonnull NonnullSupplier<T> supplier) {
        try {
            return new Success<>(supplier.get());
        }
        catch (Throwable cause) {
            return new Failure<>(cause);
        }
    }
    
    private static class Success<T> extends Try<T> {
        
        private final T result;
        
        public Success(T result) {
            super(result, null);
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
    
        @Override
        public void onSuccess(@Nonnull NonnullConsumer<T> consumer) {
        
        }
    
        @Override
        public void onFailure(@Nonnull NonnullConsumer<Throwable> consumer) {
        
        }
    }
    
    private static class Failure<T> extends Try<T> {
        private final Throwable cause;
        
        public Failure(Throwable cause) {
            super(null, cause);
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
    
        @Override
        public void onSuccess(@Nonnull NonnullConsumer<T> consumer) {
        
        }
    
        @Override
        public void onFailure(@Nonnull NonnullConsumer<Throwable> consumer) {
        
        }
    }
}
