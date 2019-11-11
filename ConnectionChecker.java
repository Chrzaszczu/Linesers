package com.mygdx.linesers;

import java.util.List;

public class ConnectionChecker
{
    private List<List<SquareTile>> squareTiles;
    private List<LaserPosition> laserPositions;
    private Vector startingTile;

    public ConnectionChecker(List<List<SquareTile>> squareTiles, Vector startingTile, List<LaserPosition> laserPositions)
    {
        this.squareTiles = squareTiles;
        this.startingTile = startingTile;
        this.laserPositions = laserPositions;
    }

    public void assambleConnections()
    {
        laserPositions.clear();
        int startingTileX = startingTile.getX();
        int startingTileY = startingTile.getY();

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
                nextTile.setGlowing(true);

                for(Vector lineDirectionB: nextTile.getLinesDirection())
                {
                    if(isThereAnotherTile(nextTileX, nextTileY, lineDirection, lineDirectionB))
                    {
                        laserPositions.add(new LaserPosition(
                                new Vector(nextTileX, nextTileY),
                                new Vector(nextTileX + lineDirectionB.getX(), nextTileY - lineDirectionB.getY())));

                        if(!squareTiles.get(nextTileY - lineDirectionB.getY()).get(nextTileX + lineDirectionB.getX()).isGlowing())
                        {
                            assambleConnections(nextTileX + lineDirectionB.getX(),
                                    nextTileY - lineDirectionB.getY(), lineDirectionB);
                        }
                    }
                }
            }
        }
    }

    private boolean isThereAnotherTile(int tileX, int tileY, Vector lineDirectionA, Vector lineDirectionB)
    {
        if(lineDirectionA != lineDirectionB)
        {
            if(lineDirectionB.vectorNorm() > 0)
            {
                if(isWithinLattice(tileX + lineDirectionB.getX(), tileY - lineDirectionB.getY()))
                {
                    return true;
                }
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

        if(x < 0 || x >= squareTiles.get(0).size())
        {
            return false;
        }

        return true;
    }
}
