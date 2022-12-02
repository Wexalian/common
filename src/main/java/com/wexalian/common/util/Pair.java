package com.wexalian.common.util;

import com.wexalian.nullability.annotations.Nullable;

public abstract class Pair<L, R> {
    @Nullable
    public abstract L getLeft();
    
    @Nullable
    public abstract R getRight();
    
    public static <L, R> Pair.Objects<L, R> of(@Nullable L left, @Nullable R right) {
        return new Pair.Objects<>(left, right);
    }
    
    public static Pair.Bytes of(@Nullable byte left, @Nullable byte right) {
        return new Pair.Bytes(left, right);
    }
    
    public static Pair.Chars of(@Nullable char left, @Nullable char right) {
        return new Pair.Chars(left, right);
    }
    
    public static Pair.Shorts of(@Nullable short left, @Nullable short right) {
        return new Pair.Shorts(left, right);
    }
    
    public static Pair.Ints of(@Nullable int left, @Nullable int right) {
        return new Pair.Ints(left, right);
    }
    
    public static Pair.Floats of(@Nullable float left, @Nullable float right) {
        return new Pair.Floats(left, right);
    }
    
    public static Pair.Doubles of(@Nullable double left, @Nullable double right) {
        return new Pair.Doubles(left, right);
    }
    
    public static Pair.Booleans of(@Nullable boolean left, @Nullable boolean right) {
        return new Pair.Booleans(left, right);
    }
    
    public static final class Objects<L, R> extends Pair<L, R> {
        private final L left;
        private final R right;
        
        private Objects(L left, R right) {
            this.left = left;
            this.right = right;
        }
        
        @Nullable
        @Override
        public L getLeft() {
            return left;
        }
        
        @Nullable
        @Override
        public R getRight() {
            return right;
        }
    }
    
    public static final class Bytes extends Pair<Byte, Byte> {
        private final byte left;
        private final byte right;
        
        private Bytes(byte left, byte right) {
            this.left = left;
            this.right = right;
        }
        
        @Nullable
        @Override
        public Byte getLeft() {
            return getLeftByte();
        }
        
        public byte getLeftByte() {
            return left;
        }
        
        @Nullable
        @Override
        public Byte getRight() {
            return getRightByte();
        }
        
        public byte getRightByte() {
            return right;
        }
    }
    
    public static final class Chars extends Pair<Character, Character> {
        private final char left;
        private final char right;
        
        private Chars(char left, char right) {
            this.left = left;
            this.right = right;
        }
        
        @Nullable
        @Override
        public Character getLeft() {
            return getLeftChar();
        }
        
        public char getLeftChar() {
            return left;
        }
        
        @Nullable
        @Override
        public Character getRight() {
            return getRightChar();
        }
        
        public char getRightChar() {
            return right;
        }
    }
    
    public static final class Shorts extends Pair<Short, Short> {
        private final short left;
        private final short right;
        
        private Shorts(short left, short right) {
            this.left = left;
            this.right = right;
        }
        
        @Nullable
        @Override
        public Short getLeft() {
            return getLeftShort();
        }
        
        public short getLeftShort() {
            return left;
        }
        
        @Nullable
        @Override
        public Short getRight() {
            return getRightShort();
        }
        
        public short getRightShort() {
            return right;
        }
    }
    
    public static final class Ints extends Pair<Integer, Integer> {
        private final int left;
        private final int right;
        
        private Ints(int left, int right) {
            this.left = left;
            this.right = right;
        }
        
        @Nullable
        @Override
        public Integer getLeft() {
            return getLeftInt();
        }
        
        public int getLeftInt() {
            return left;
        }
        
        @Nullable
        @Override
        public Integer getRight() {
            return getRightInt();
        }
        
        public int getRightInt() {
            return right;
        }
    }
    
    public static final class Floats extends Pair<Float, Float> {
        private final float left;
        private final float right;
        
        private Floats(float left, float right) {
            this.left = left;
            this.right = right;
        }
        
        @Nullable
        @Override
        public Float getLeft() {
            return getLeftFloat();
        }
        
        public float getLeftFloat() {
            return left;
        }
        
        @Nullable
        @Override
        public Float getRight() {
            return getRightFloat();
        }
        
        public float getRightFloat() {
            return right;
        }
    }
    
    public static final class Doubles extends Pair<Double, Double> {
        private final double left;
        private final double right;
        
        private Doubles(double left, double right) {
            this.left = left;
            this.right = right;
        }
        
        @Nullable
        @Override
        public Double getLeft() {
            return getLeftDouble();
        }
        
        public double getLeftDouble() {
            return left;
        }
        
        @Nullable
        @Override
        public Double getRight() {
            return getRightDouble();
        }
        
        public double getRightDouble() {
            return right;
        }
    }
    
    public static final class Booleans extends Pair<Boolean, Boolean> {
        private final boolean left;
        private final boolean right;
        
        private Booleans(boolean left, boolean right) {
            this.left = left;
            this.right = right;
        }
        
        @Nullable
        @Override
        public Boolean getLeft() {
            return getLeftBoolean();
        }
        
        public boolean getLeftBoolean() {
            return left;
        }
        
        @Nullable
        @Override
        public Boolean getRight() {
            return getRightBoolean();
        }
        
        public boolean getRightBoolean() {
            return right;
        }
    }
}
