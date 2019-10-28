package com.patryk.main;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class LevelDesign
{
    public final static int NUMBER_OF_LEVELS = 20;

    private List<List<SquareTile>> squareTiles = new ArrayList<List<SquareTile>>();

    private List<List<SquareTile>> levelTmp()
    {
        squareTiles.add(new ArrayList<SquareTile>(Arrays.asList(
                new TileOne(0),
                new TileThree(0),
                new TileThree(0),
                new TileTwo(0),
                new TileFinal(-180)
        )));

        squareTiles.add(new ArrayList<SquareTile>(Arrays.asList(
                new TileHalf(-90),
                new TileOne(-90),
                new TileThree(-90),
                new TileTwo(-90),
                new TileOne(0)
        )));

        squareTiles.add(new ArrayList<SquareTile>(Arrays.asList(
                new TileHalf(0),
                new TileThree(0),
                new TileThree(0),
                new TileThree(0),
                new TileTwo(0)
        )));

        squareTiles.add(new ArrayList<SquareTile>(Arrays.asList(
                new TileStart(-90),
                new TileTwo(-270),
                new TileTwo(-270),
                new TileTwo(-270),
                new TileTwo(-270)
        )));

        return squareTiles;
    }

    public List<List<SquareTile>> setLevel(int levelNumber)
    {
        return levelTmp();
    }
}
