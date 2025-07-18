package com.segilmez.okeysimulator.simulator;

import com.segilmez.okeysimulator.tile.Tile;

import java.util.List;

public interface Player {

    List<Tile> getTiles();

    int getNumber();

    void setTiles(List<Tile> tiles);

    void sortTiles();

    int simulateBestGame();

}
