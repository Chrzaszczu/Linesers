package com.patryk.main;

import java.util.List;

public class ConnectionChecker
{
    private List<List<SquareTile>> squareTiles;
    private Vector startingTile;

    public ConnectionChecker(List<List<SquareTile>> squareTiles, Vector startingTile)
    {
        this.squareTiles = squareTiles;
        this.startingTile = startingTile;
    }

    public void checkConnections()
    {
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
                    checkConnections(nextTileX, nextTileY, lineDirection);
                }
            }
        }
    }

    private void checkConnections(int nextTileX, int nextTileY, Vector previousLineDirection)
    {
        SquareTile nextTile = squareTiles.get(nextTileY).get(nextTileX);

        for(Vector lineDirection: nextTile.getLinesDirection())
        {
            if(Vector.normOfSum(lineDirection, previousLineDirection) == 0)
            {
                nextTile.setGlowing(true);

                for(Vector lineDirectionB: nextTile.getLinesDirection())
                {
                    if(isThereAnotherLine(nextTileX, nextTileY, lineDirection, lineDirectionB))
                    {
                        checkConnections(nextTileX + lineDirectionB.getX(),
                                nextTileY - lineDirectionB.getY(), lineDirectionB);
                    }
                }
            }
        }
    }

    private boolean isThereAnotherLine(int tileX, int tileY, Vector lineDirectionA, Vector lineDirectionB)
    {
        if(lineDirectionA != lineDirectionB)
        {
            if(lineDirectionB.vectorNorm() > 0)
            {
                if(isWithinLattice(tileX + lineDirectionB.getX(), tileY - lineDirectionB.getY()))
                {
                    if(!squareTiles.get(tileY - lineDirectionB.getY()).get(tileX + lineDirectionB.getX()).isGlowing())
                    {
                        return true;
                    }
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
