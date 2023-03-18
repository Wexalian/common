package com.wexalian.common.util.misc;

import com.wexalian.nullability.annotations.Nonnull;
import com.wexalian.nullability.function.NonnullConsumer;
import com.wexalian.nullability.function.NonnullSupplier;

public abstract class Try<T> extends Either<T, Throwable> {
    private Try(T value, Throwable cause) {
        super(value, cause);
    }
    
    @Nonnull
    public abstract T get();
    
    @Nonnull
    public abstract Throwable getCause();
    
    public abstract boolean isSuccess();
    
    public abstract boolean isFailure();
    
    public boolean onSuccess(@Nonnull NonnullConsumer<T> consumer) {
        if (isSuccess()) {
            consumer.accept(get());
            return true;
        }
        return false;
    }
    
    public boolean onFailure(@Nonnull NonnullConsumer<Throwable> consumer) {
        if (isFailure()) {
            consumer.accept(getCause());
            return true;
        }
        return false;
    }
    
    public static <T> Try<T> of(@Nonnull NonnullSupplier<T> supplier) {
        try {
            return new Success<>(supplier.get());
        }
        catch (Throwable cause) {
            return new Failure<>(cause);
        }
    }
    
    public static <T> Try<T> success(@Nonnull T value) {
        return new Success<>(value);
    }
    public static <T> Try<T> failure(@Nonnull Throwable cause) {
        return new Failure<>(cause);
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
        
    }
}
