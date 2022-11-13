package com.wexalian.common.util;

import com.wexalian.nullability.annotations.Nonnull;
import com.wexalian.nullability.annotations.Nullable;

import java.util.function.Consumer;

public class Either<LEFT, RIGHT> {
    private final LEFT left;
    private final RIGHT right;
    
    protected Either(LEFT left, RIGHT right) {
        this.left = left;
        this.right = right;
    }
    
    public static <LEFT, RIGHT> Either<LEFT, RIGHT> left(@Nonnull LEFT left) {
        return new Either<>(left, null);
    }
    
    public static <LEFT, RIGHT> Either<LEFT, RIGHT> right(@Nonnull RIGHT right) {
        return new Either<>(null, right);
    }
    
    public boolean isLeft() {
        return left != null;
    }
    
    @Nullable
    public LEFT getLeft() {
        return left;
    }
    
    public void onLeft(@Nonnull Consumer<LEFT> leftConsumer) {
        leftConsumer.accept(left);
    }
    
    public boolean isRight() {
        return right != null;
    }
    
    @Nullable
    public RIGHT getRight() {
        return right;
    }
    
    public void onRight(@Nonnull Consumer<RIGHT> rightConsumer) {
        rightConsumer.accept(right);
    }
}
