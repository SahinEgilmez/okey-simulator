package com.segilmez.okeysimulator.tile;

public class FakeOkeyTile extends TileImpl {

    private int realNumber; // just for fake okey

    public FakeOkeyTile(int number, int realNumber) {
        super(number);
        setRealNumber(realNumber);
        super.setColor( determineColor(realNumber) );

    }

    @Override
    public void setRealNumber(int number) {
        this.realNumber = number;
    }

    @Override
    public int getRealNumber() {
        return (realNumber % 13) + 1 ;
    }
}
