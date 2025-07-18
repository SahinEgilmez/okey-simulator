package com.segilmez.okeysimulator.simulator;

import com.segilmez.okeysimulator.tile.FakeOkeyTile;
import com.segilmez.okeysimulator.tile.OkeyTile;
import com.segilmez.okeysimulator.tile.Tile;
import com.segilmez.okeysimulator.tile.TileImpl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class Game {

    private final int TILE_SIZE = 52;
    private final int PLAYER_SIZE = 4;

    List<Player> players = new ArrayList<>();

    public Game() {
    }

    public void run() {
        System.out.println("Welcome to the Okey Game Simulation!");

        Random random = new Random();
        int okeyNum = random.nextInt(TILE_SIZE);
        System.out.println("\nOkey number is: " + new TileImpl(okeyNum) + "\n");

        List<Tile> tiles = new ArrayList<>();
        for (int i = 0; i < TILE_SIZE; i++) {
            if (i == okeyNum) {
                tiles.add(new OkeyTile(i));
                tiles.add(new OkeyTile(i));
            } else {
                tiles.add(new TileImpl(i));
                tiles.add(new TileImpl(i));
            }
        }

        tiles.add(new FakeOkeyTile(TILE_SIZE, okeyNum));
        tiles.add(new FakeOkeyTile(TILE_SIZE, okeyNum));

        Collections.shuffle(tiles);

        int firstPlayer = random.nextInt(PLAYER_SIZE);
        int index = 0;

        for (int i = 0; i < PLAYER_SIZE; i++) {
            Player player = new PlayerImpl(i);

            if (i == firstPlayer) {
                player.setTiles(new ArrayList<>(tiles.subList(index, index + 15)));
                index = index + 15;
            } else {
                player.setTiles(new ArrayList<>(tiles.subList(index, index + 14)));
                index = index + 14;
            }

            players.add(player);
        }

        for (Player player : players) {
            player.sortTiles();
            System.out.println(player);
        }

        System.out.println("\nThe best series in each player's hand is calculated...\n");

        List<Player> possibleWinners = new ArrayList<>();
        int minimum = 15;
        for (int i = 0; i < players.size(); i++) {
            System.out.println("Player " + (players.get(i).getNumber()));
            int min = players.get(i).simulateBestGame();

            if (i == 0) { // for 15 tiles hand
                min--;
            }

            if (min == minimum) {
                possibleWinners.add(players.get(i));
            }

            if (min < minimum) {
                minimum = min;
                possibleWinners.clear();
                possibleWinners.add(players.get(i));
            }
        }

        System.out.println("\nThe possible winner player is:\n");

        for (Player player : possibleWinners) {
            System.out.println("Player " + (player.getNumber()));
        }

    }

}
