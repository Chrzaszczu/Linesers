package Main.com.mygdx.linesers.tiles;

public class TileFactory
{
    public static SquareTile getTile(TileType tileType, int rotation)
    {
        switch(tileType)
        {
            case ONE_LINE: return new TileOne(rotation);

            case TWO_LINES: return new TileTwo(rotation);

            case THREE_LINES: return new TileThree(rotation);

            case FOUR_LINES: return new TileFour(rotation);

            case HALF_LINE: return new TileHalf(rotation);

            case START: return new TileStart(rotation);

            case FINAL: return new TileFinal(rotation);

            default: return new TileZero(rotation);
        }
    }
}
