package Main.com.mygdx.linesers.levels.timetrial;

import Main.com.mygdx.linesers.levels.ConnectionChecker;
import Main.com.mygdx.linesers.levels.Vector;
import Main.com.mygdx.linesers.tiles.*;


import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

class RandomLineArranger
{
    private final int ROTATION_ANGLE = -90;
    private final int NUMBER_OF_ROTATIONS = 4;
    private final double CHANCE_OF_ROLLING_TILE_FOUR = 0.08;
    private final double CHANCE_OF_ROLLING_TILE_THREE = 0.2;

    private ConnectionChecker connectionChecker;
    private List<Vector> finalTiles;

    RandomLineArranger(ConnectionChecker connectionChecker, List<Vector> finalTiles)
    {
        this.connectionChecker = connectionChecker;
        this.finalTiles = finalTiles;
    }

    void arrangeLine(List<Vector> tileLine)
    {
        List<Vector> connectedFinalTiles = new ArrayList<>();
        Vector tempPosition;

        initializeTiles();
        prepareFinalTiles(tileLine);

        connectionChecker.assambleConnections();

        tempPosition = tileLine.get(0);
        for(Vector position: tileLine)
        {
            arrangeNearTiles(position, tempPosition);
            arrangeFinalTiles(position, connectedFinalTiles);
            tempPosition = position;
        }

        if(isGlowing(finalTiles))
        {
            rerollTilesType(tileLine);
            rerollRotations(tileLine);
        }
        else
        {
            connectionChecker.getSquareTiles().clear();
        }
    }

    private void arrangeNearTiles(Vector position, Vector tempPosition)
    {
        connectionChecker.assambleConnections();
        while(!connectionChecker.getSquareTiles().get(position.getY()).get(position.getX()).isGlowing())
        {
            if(!isConnectionPossible(position))
            {
                updateTileType(tempPosition);

                for(int i = 0; i < NUMBER_OF_ROTATIONS; i++)
                {
                    if(!isConnectionPossible(position))
                    {
                        connectionChecker.getSquareTiles().get(tempPosition.getY()).get(tempPosition.getX()).rotateTile(ROTATION_ANGLE);
                    }
                }
            }
        }
    }

    private void arrangeFinalTiles(Vector position, List<Vector> connectedFinalTiles)
    {
        List<Vector> nearestPoints = position.getNearestPoints();

        if(Vector.isSamePosition(finalTiles, nearestPoints) && !Vector.isSamePosition(connectedFinalTiles, nearestPoints))
        {
            connectionChecker.assambleConnections();
            while(!isGlowing(Vector.getSamePositions(finalTiles, nearestPoints)))
            {
                if(!isConnectionPossible(Vector.getSamePositions(finalTiles, nearestPoints), position))
                {
                    updateTileType(position);
                }
            }

            connectedFinalTiles.addAll(Vector.getSamePositions(finalTiles, nearestPoints));
        }
    }


    private boolean isGlowing(List<Vector> tiles)
    {
        for(Vector tile: tiles)
        {
            if(!connectionChecker.getSquareTiles().get(tile.getY()).get(tile.getX()).isGlowing())
            {
                return false;
            }
        }

        return true;
    }

    private void initializeTiles()
    {
        for(List<SquareTile> squareTiles: connectionChecker.getSquareTiles())
        {
            for(SquareTile squareTile: squareTiles)
            {
                squareTile.initializeTile();
            }
        }
    }

    private boolean isConnectionPossible(Vector position)
    {
        for(int i = 0; i < NUMBER_OF_ROTATIONS; i++)
        {
            connectionChecker.assambleConnections();

            if(connectionChecker.getSquareTiles().get(position.getY()).get(position.getX()).isGlowing())
            {
                return true;
            }

            connectionChecker.getSquareTiles().get(position.getY()).get(position.getX()).rotateTile(ROTATION_ANGLE);
        }

        return false;
    }

    private boolean isConnectionPossible(List<Vector> finalPositions, Vector position)
    {
        for(int i = 0; i < NUMBER_OF_ROTATIONS; i++)
        {
            connectionChecker.assambleConnections();

            boolean allFinalTilesGlowing = finalPositions.stream()
                    .filter(finalPosition -> connectionChecker.getSquareTiles().get(finalPosition.getY()).get(finalPosition.getX()).isGlowing())
                    .collect(Collectors.toList())
                    .size() == finalPositions.size();

            if(allFinalTilesGlowing)
            {
                return true;
            }
            else
            {
                connectionChecker.getSquareTiles().get(position.getY()).get(position.getX()).rotateTile(ROTATION_ANGLE);
            }
        }

        return false;
    }

    private void updateTileType(Vector position)
    {
        SquareTile tempTile = connectionChecker.getSquareTiles().get(position.getY()).get(position.getX());

        if(tempTile.getClass() != TileFour.class)
        {
            if (tempTile.getClass() == TileOne.class)
            {
                tempTile = TileFactory.getTile(TileType.TWO_LINES, 0);
            }
            else if (tempTile.getClass() == TileTwo.class)
            {
                tempTile = TileFactory.getTile(TileType.THREE_LINES, 0);
            }
            else if (tempTile.getClass() == TileThree.class)
            {
                tempTile = TileFactory.getTile(TileType.FOUR_LINES, 0);
            }

            tempTile.initializeTile();
            connectionChecker.getSquareTiles().get(position.getY()).set(position.getX(), tempTile);
        }
    }

    private void prepareFinalTiles(List<Vector> tileLine)
    {
        SquareTile tempTile;

        for(Vector finalTile: finalTiles)
        {
            for (Vector tile : tileLine)
            {
                if(Vector.vectorNorm(finalTile, tile) == 1)
                {
                    tempTile = TileFactory.getTile(TileType.FINAL, finalTileRotation(finalTile, tile));
                    tempTile.initializeTile();
                    connectionChecker.getSquareTiles().get(finalTile.getY()).set(finalTile.getX(), tempTile);
                    break;
                }
            }
        }
    }

    private int finalTileRotation(Vector finalTile, Vector nearTile)
    {
        Vector direction = new Vector(nearTile.getX() - finalTile.getX(), nearTile.getY() - finalTile.getY());

        if(direction.getX() == 1)
        {
            return ROTATION_ANGLE;
        }

        if(direction.getY() == 1)
        {
            return 2 * ROTATION_ANGLE;
        }

        if(direction.getX() == -1)
        {
            return 3 * ROTATION_ANGLE;
        }

        return 0;
    }

    private void changeTileType(Vector tile, int tileType, int rotation)
    {
        if(connectionChecker.getSquareTiles().get(tile.getY()).get(tile.getX()).getClass() != TileFour.class)
        {
            connectionChecker.getSquareTiles()
                    .get(tile.getY())
                    .set(tile.getX(), TileFactory.getTile(TileType.getTileTypeOfValue(tileType), rotation));
        }
    }

    private void rerollTilesType(List<Vector> tileLine)
    {
        double randomNumber;

        for(Vector tile: tileLine)
        {
            randomNumber = Math.random();

            if(randomNumber < CHANCE_OF_ROLLING_TILE_FOUR)
            {
                changeTileType(tile, 4, RandomLevelGenerator.rollRotation());
            }
            else if(randomNumber < CHANCE_OF_ROLLING_TILE_THREE)
            {
                changeTileType(tile, 3, RandomLevelGenerator.rollRotation());
            }
        }
    }

    private void rerollRotations(List<Vector> tileLine)
    {
        for(Vector tile: tileLine)
        {
            getSquareTiles().get(tile.getY()).get(tile.getX()).rotateTile(RandomLevelGenerator.rollRotation());
        }
    }

    void setFinalTiles(List<Vector> finalTiles)
    {
        this.finalTiles = finalTiles;
    }

    void clearTiles()
    {
        for(List<SquareTile> squareTiles: connectionChecker.getSquareTiles())
        {
            for(SquareTile squareTile: squareTiles)
            {
                squareTile.clear();
            }
        }
    }

    public List<List<SquareTile>> getSquareTiles()
    {
        return connectionChecker.getSquareTiles();
    }
}
