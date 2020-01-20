package Main.com.mygdx.linesers.levels;

import Main.com.mygdx.linesers.tiles.SquareTile;
import Main.com.mygdx.linesers.tiles.TileFinal;

import java.util.LinkedList;
import java.util.List;

public class ConnectionChecker
{
    private List<List<SquareTile>> squareTiles;
    private List<LaserPosition> laserPositions = new LinkedList<>();
    private Vector startingTile;

    private int numberOfGlowingFinalTiles = 0;

    public ConnectionChecker()
    {
    }

    public ConnectionChecker(List<List<SquareTile>> squareTiles, Vector startingTile, List<LaserPosition> laserPositions)
    {
        this.squareTiles = squareTiles;
        this.startingTile = startingTile;
        this.laserPositions = laserPositions;
    }

    public void assambleConnections()
    {
        int startingTileX = startingTile.getX();
        int startingTileY = startingTile.getY();

        turnOff();
        laserPositions.clear();
        numberOfGlowingFinalTiles = 0;

        for(Vector lineDirection: squareTiles.get(startingTileY).get(startingTileX).getLinesDirection())
        {
            if(lineDirection.vectorNorm() > 0)
            {
                int nextTileX = startingTileX + lineDirection.getX();
                int nextTileY = startingTileY - lineDirection.getY();

                if(isWithinLattice(nextTileX, nextTileY))
                {
                    laserPositions.add(new LaserPosition(
                            new Vector(startingTileX, startingTileY),
                            new Vector(nextTileX, nextTileY)));

                    assambleConnections(nextTileX, nextTileY, lineDirection);
                }
            }
        }
    }

    private void assambleConnections(int nextTileX, int nextTileY, Vector previousLineDirection)
    {
        SquareTile nextTile = squareTiles.get(nextTileY).get(nextTileX);

        for(Vector lineDirection: nextTile.getLinesDirection())
        {
            if(Vector.normOfSum(lineDirection, previousLineDirection) == 0)
            {
                lightUp(nextTile);

                for(Vector nextTileLineDirection: nextTile.getLinesDirection())
                {
                    if(isThereAnotherTile(nextTileX, nextTileY, lineDirection, nextTileLineDirection))
                    {
                        laserPositions.add(new LaserPosition(
                                new Vector(nextTileX, nextTileY),
                                new Vector(nextTileX + nextTileLineDirection.getX(), nextTileY - nextTileLineDirection.getY())));

                        if(!squareTiles.get(nextTileY - nextTileLineDirection.getY()).get(nextTileX + nextTileLineDirection.getX()).isGlowing())
                        {
                            assambleConnections(nextTileX + nextTileLineDirection.getX(),
                                    nextTileY - nextTileLineDirection.getY(), nextTileLineDirection);
                        }
                    }
                }
            }
        }
    }

    private void turnOff()
    {
        for(List<SquareTile> tempSquareTiles: squareTiles)
        {
            for(SquareTile sqrTiles: tempSquareTiles)
            {
                sqrTiles.setGlowing(false);
            }
        }
    }

    private void lightUp(SquareTile tile)
    {
        tile.setGlowing(true);

        if(tile.getClass() == TileFinal.class)
        {
            numberOfGlowingFinalTiles += 1;
        }
    }

    private boolean isThereAnotherTile(int tileX, int tileY, Vector lineDirectionA, Vector lineDirectionB)
    {
        if(lineDirectionA != lineDirectionB)
        {
            if(lineDirectionB.vectorNorm() > 0)
            {
                return isWithinLattice(tileX + lineDirectionB.getX(), tileY - lineDirectionB.getY());
            }
        }

        return false;
    }

    private boolean isWithinLattice(int x, int y)
    {
        if(y < 0 || y >= squareTiles.size())
        {
            return false;
        }

        return x >= 0 && x < squareTiles.get(0).size();
    }

    public int getNumberOfGlowingFinalTiles()
    {
        return numberOfGlowingFinalTiles;
    }

    public void setSquareTiles(List<List<SquareTile>> squareTiles)
    {
        this.squareTiles = squareTiles;
    }

    public void setStartingTile(Vector startingTile)
    {
        this.startingTile = startingTile;
    }

    public List<List<SquareTile>> getSquareTiles()
    {
        return squareTiles;
    }
}
