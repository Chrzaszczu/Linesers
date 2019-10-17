package com.patryk.main;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class LevelDesign
{
    private List<List<SquareTile>> squareTiles = new ArrayList<List<SquareTile>>();

    private List<List<SquareTile>> levelTmp()
    {
        squareTiles.add(new ArrayList<SquareTile>(Arrays.asList(
                new SquareTile(TileType.ONE_LINE,0),
                new SquareTile(TileType.THREE_LINES, 0),
                new SquareTile(TileType.THREE_LINES, 0),
                new SquareTile(TileType.THREE_LINES, 0),
                new SquareTile(TileType.FINAL_TILE, -180)
        )));

        squareTiles.add(new ArrayList<SquareTile>(Arrays.asList(
                new SquareTile(TileType.HALF_LINE,-90),
                new SquareTile(TileType.THREE_LINES, -90),
                new SquareTile(TileType.THREE_LINES, -90),
                new SquareTile(TileType.THREE_LINES, -90),
                new SquareTile(TileType.TWO_LINES, -180)
        )));

        squareTiles.add(new ArrayList<SquareTile>(Arrays.asList(
                new SquareTile(TileType.HALF_LINE,-90),
                new SquareTile(TileType.THREE_LINES, -90),
                new SquareTile(TileType.THREE_LINES, -90),
                new SquareTile(TileType.THREE_LINES, -90),
                new SquareTile(TileType.TWO_LINES, -180)
        )));

        squareTiles.add(new ArrayList<SquareTile>(Arrays.asList(
                new SquareTile(TileType.STARTING_TILE,-90),
                new SquareTile(TileType.TWO_LINES, -270),
                new SquareTile(TileType.TWO_LINES, -270),
                new SquareTile(TileType.TWO_LINES, -270),
                new SquareTile(TileType.TWO_LINES, -180)
        )));

        return squareTiles;
    }

    public List<List<SquareTile>> setLevel(int levelNumber)
    {
        return levelTmp();
    }
}
