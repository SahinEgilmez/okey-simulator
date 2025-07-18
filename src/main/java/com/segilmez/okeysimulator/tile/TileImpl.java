package com.segilmez.okeysimulator.tile;

public class TileImpl implements Tile {

    private Color color;
    private final int number; // 0-12 yellow, 13-25 blue, 26-38 black, 39-52 red
    private boolean isOkey = false;

    public TileImpl(int number) {
        this.number = number;
        this.color = determineColor(number);
    }

    public Color getColor() {
        return color;
    }

    public int getNumber() {
        return number;
    }

    public int getRealNumber() {
        return (number % 13) + 1;
    }

    public void setRealNumber(int number) {
        return;
    }

    public boolean isOkey() {
        return isOkey;
    }

    public void setOkey(boolean isOkey) {
        this.isOkey = isOkey;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    @Override
    public String toString() {
        String underlineCode = isOkey() ? "\033[4m" : "";
        String resetUnderlineCode = isOkey() ? "\033[24m" : "";
        return String.format("%d%s%s(%d)%s\033[0m", number, color.getAnsiCode(), underlineCode, getRealNumber(), resetUnderlineCode);
    }
}
