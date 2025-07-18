package com.segilmez.okeysimulator.simulator;

import com.segilmez.okeysimulator.tile.Color;
import com.segilmez.okeysimulator.tile.Tile;

import java.util.*;

public class PlayerImpl implements Player {

    private final int number;
    private List<Tile> tiles;

    public PlayerImpl(int number) {
        this.number = number;
        this.tiles = new ArrayList<>();
    }

    @Override
    public List<Tile> getTiles() {
        return tiles;
    }

    @Override
    public int getNumber() {
        return number + 1;
    }

    @Override
    public void setTiles(List<Tile> tiles) {
        this.tiles = tiles;
    }

    @Override
    public void sortTiles() {
        Collections.sort(tiles);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Tile tile : tiles) {
            sb.append(tile.toString());
            sb.append(" ");
        }
        return String.format("%s %d: %s", "Player", number + 1, sb.toString());
    }

    @Override
    public int simulateBestGame() {
        this.sortTiles();

        List<Tile> bestSequences = findBestSequences(tiles, new ArrayList<Tile>());

        System.out.println("The remaining hand, excluding the best series, is then: " + bestSequences);

        return bestSequences.size();
    }

    private List<Tile> findBestSequences(List<Tile> tiles, List<Tile> currentSequence) {
        List<List<Tile>> possibleSeries = new ArrayList<>();

        possibleSeries.addAll(findPossiblePairs(tiles));
        possibleSeries.addAll(findPossibleSet(tiles));

        if (possibleSeries.isEmpty()) {
            return tiles;
        }

        tiles.removeAll(currentSequence);

        List<List<Tile>> remainings = possibleSeries.stream().map(p -> findBestSequences(tiles, p)).toList();

        return remainings.stream()
                .min(Comparator.comparingInt(List::size))
                .orElse(Collections.emptyList());
    }

    private List<List<Tile>> findPossiblePairs(List<Tile> tiles) {
        List<List<Tile>> result = new ArrayList<>();
        if (tiles == null || tiles.isEmpty()) {
            return result;
        }

        List<Tile> nonOkeyTiles = new ArrayList<>();
        List<Tile> okeyTiles = new ArrayList<>();

        for (Tile tile : tiles) {
            if (tile.isOkey()) {
                okeyTiles.add(tile);
            } else {
                nonOkeyTiles.add(tile);
            }
        }

        Map<Integer, List<Tile>> tilesByNumber = new HashMap<>();
        for (Tile tile : nonOkeyTiles) {
            int realNumber = tile.getRealNumber();
            if (!tilesByNumber.containsKey(realNumber)) {
                tilesByNumber.put(realNumber, new ArrayList<>());
            }
            tilesByNumber.get(realNumber).add(tile);
        }

        for (Map.Entry<Integer, List<Tile>> entry : tilesByNumber.entrySet()) {
            List<Tile> tilesWithSameNumber = entry.getValue();

            Map<Color, Tile> tilesByColor = new HashMap<>();
            for (Tile tile : tilesWithSameNumber) {
                tilesByColor.put(tile.getColor(), tile);
            }

            List<Tile> uniqueColorTiles = new ArrayList<>(tilesByColor.values());

            if (uniqueColorTiles.size() >= 3) {
                result.add(new ArrayList<>(uniqueColorTiles));
            } else if (uniqueColorTiles.size() == 2 && !okeyTiles.isEmpty()) {
                List<Tile> pair = new ArrayList<>(uniqueColorTiles);
                pair.add(okeyTiles.get(0));
                result.add(pair);
            }
        }

        return result;
    }

    private List<List<Tile>> findPossibleSet(List<Tile> tiles) {
        List<List<Tile>> result = new ArrayList<>();
        if (tiles == null || tiles.isEmpty()) {
            return result;
        }

        List<Tile> nonOkeyTiles = new ArrayList<>();
        List<Tile> okeyTiles = new ArrayList<>();

        for (Tile tile : tiles) {
            if (tile.isOkey()) {
                okeyTiles.add(tile);
            } else {
                nonOkeyTiles.add(tile);
            }
        }

        Map<Color, List<Tile>> tilesByColor = new HashMap<>();
        for (Tile tile : nonOkeyTiles) {
            Color color = tile.getColor();
            if (color != null) {
                if (!tilesByColor.containsKey(color)) {
                    tilesByColor.put(color, new ArrayList<>());
                }
                tilesByColor.get(color).add(tile);
            }
        }

        for (Map.Entry<Color, List<Tile>> entry : tilesByColor.entrySet()) {
            List<Tile> tilesWithSameColor = entry.getValue();

            tilesWithSameColor.sort(Comparator.comparingInt(Tile::getRealNumber));

            findAllConsecutiveSequences(tilesWithSameColor, okeyTiles, result);
        }

        return result;
    }


    private void findAllConsecutiveSequences(List<Tile> tilesWithSameColor, List<Tile> okeyTiles, List<List<Tile>> result) {
        if (tilesWithSameColor.size() < 2) {
            return;
        }

        List<Tile> uniqueTiles = new ArrayList<>();
        int lastRealNumber = -1;
        for (Tile tile : tilesWithSameColor) {
            int realNumber = tile.getRealNumber();
            if (realNumber != lastRealNumber) {
                uniqueTiles.add(tile);
                lastRealNumber = realNumber;
            }
        }

        for (int start = 0; start < uniqueTiles.size(); start++) {
            List<Tile> currentSequence = new ArrayList<>();
            currentSequence.add(uniqueTiles.get(start));

            int lastNumber = uniqueTiles.get(start).getRealNumber();
            int gapsToFill = 0;

            for (int i = start + 1; i < uniqueTiles.size(); i++) {
                int currentNumber = uniqueTiles.get(i).getRealNumber();
                int gap = currentNumber - lastNumber - 1;

                if (gap == 0) {
                    currentSequence.add(uniqueTiles.get(i));
                    lastNumber = currentNumber;
                } else if (gap > 0 && gap <= okeyTiles.size()) {
                    gapsToFill += gap;
                    currentSequence.add(uniqueTiles.get(i));
                    lastNumber = currentNumber;
                } else {
                    break;
                }

                if (currentSequence.size() + gapsToFill >= 3) {
                    List<Tile> validSequence = new ArrayList<>(currentSequence);

                    for (int j = 0; j < gapsToFill && j < okeyTiles.size(); j++) {
                        validSequence.add(okeyTiles.get(j));
                    }

                    result.add(validSequence);
                }
            }
        }
    }

}
