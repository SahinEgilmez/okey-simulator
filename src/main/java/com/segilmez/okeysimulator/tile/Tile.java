package com.segilmez.okeysimulator.tile;

public interface Tile extends Comparable<Tile> {

    default Color determineColor(int num) {
        if (num >= 0 && num <= 12) return Color.YELLOW;
        else if (num <= 25) return Color.BLUE;
        else if (num <= 38) return Color.BLACK;
        else if (num <= 51) return Color.RED;
        else if (num == 52) return null; // fake okey
        else throw new IllegalArgumentException("Number out of range!");
    }

    Color getColor();

    void setColor(Color color);

    int getNumber();

    int getRealNumber();

    void setRealNumber(int number);

    boolean isOkey();

    void setOkey(boolean isOkey);

    @Override
    default int compareTo(Tile other) {
        return Integer.compare(this.getRealNumber(), other.getRealNumber());
    }

}
