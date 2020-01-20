package Main.com.mygdx.linesers.tiles;

public enum TileType
{
    ONE_LINE(1), TWO_LINES(2), THREE_LINES(3), FOUR_LINES(4), HALF_LINE(5), ZERO_LINES(0), START(6), FINAL(7);

    private final int typeValue;

    TileType(int typeValue)
    {
        this.typeValue = typeValue;
    }

    public int getTypeValue()
    {
        return typeValue;
    }

    public static TileType getTileTypeOfValue(int type)
    {
        for(TileType tileType: values())
        {
            if(tileType.typeValue == type)
            {
                return tileType;
            }
        }

        return ZERO_LINES;
    }
}