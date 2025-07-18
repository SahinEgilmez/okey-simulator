package com.segilmez.okeysimulator.tile;

public enum Color {

    YELLOW("\033[0;33m"),
    BLUE("\033[0;34m"),
    BLACK("\033[0;90m"), // bright black (gray) for dark mode compatibility
    RED("\033[0;31m");

    private final String ansiCode;

    Color(String ansiCode) {
        this.ansiCode = ansiCode;
    }

    public String getAnsiCode() {
        return ansiCode;
    }
}