package com.wexalian.common.util;

import com.wexalian.nullability.annotations.Nonnull;

public enum Ansi {
    BLACK("\u001B[30m"),
    RED("\u001B[31m"),
    GREEN("\u001B[32m"),
    YELLOW("\u001B[33m"),
    BLUE("\u001B[34m"),
    PURPLE("\u001B[35m"),
    CYAN("\u001B[36m"),
    WHITE("\u001B[37m"),
    
    BRIGHT_BLACK("\u001b[30;1m"),
    BRIGHT_RED("\u001b[31;1m"),
    BRIGHT_GREEN("\u001b[32;1m"),
    BRIGHT_YELLOW("\u001b[33;1m"),
    BRIGHT_BLUE("\u001b[34;1m"),
    BRIGHT_MAGENTA("\u001b[35;1m"),
    BRIGHT_CYAN("\u001b[36;1m"),
    BRIGHT_WHITE("\u001b[37;1m"),
    
    BACKGROUND_BLACK("\u001b[40m"),
    BACKGROUND_RED("\u001b[41m"),
    BACKGROUND_GREEN("\u001b[42m"),
    BACKGROUND_YELLOW("\u001b[43m"),
    BACKGROUND_BLUE("\u001b[44m"),
    BACKGROUND_MAGENTA("\u001b[45m"),
    BACKGROUND_CYAN("\u001b[46m"),
    BACKGROUND_WHITE("\u001b[47m"),
    
    BACKGROUND_BRIGHT_BLACK("\u001b[40;1m"),
    BACKGROUND_BRIGHT_RED("\u001b[41;1m"),
    BACKGROUND_BRIGHT_GREEN("\u001b[42;1m"),
    BACKGROUND_BRIGHT_YELLOW("\u001b[43;1m"),
    BACKGROUND_BRIGHT_BLUE("\u001b[44;1m"),
    BACKGROUND_BRIGHT_MAGENTA("\u001b[45;1m"),
    BACKGROUND_BRIGHT_CYAN("\u001b[46;1m"),
    BACKGROUND_BRIGHT_WHITE("\u001b[47;1m"),
    
    BOLD("\u001b[1m"),
    UNDERLINE("\u001b[4m"),
    REVERSED("\u001b[7m"),
    
    UP("\u001b[{n}A"),
    DOWN("\u001b[{n}B"),
    RIGHT("\u001b[{n}C"),
    LEFT("\u001b[{n}D"),
    
    RESET("\u001b[0m");
    
    private final String escapeCode;
    
    Ansi(String escapeCode) {
        this.escapeCode = escapeCode;
    }
    
    @Nonnull
    public String getEscapeCode() {
        return escapeCode;
    }
    
    @Nonnull
    @Override
    public String toString() {
        return getEscapeCode();
    }
}
