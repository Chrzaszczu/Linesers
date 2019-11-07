package com.patryk.main;

public class TileFactory
{
    public static SquareTile getTile(int tileType, int rotation)
    {
        switch(tileType)
        {
            case 1: return new TileOne(rotation);

            case 2: return new TileTwo(rotation);

            case 3: return new TileThree(rotation);

            case 4: return new TileFour(rotation);

            case 5: return new TileHalf(rotation);

            case 6: return new TileStart(rotation);

            case 7: return new TileFinal(rotation);

            default: return new TileOne(0);
        }
    }
}
