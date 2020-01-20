package Main.com.mygdx.linesers.levels;

import Main.com.mygdx.linesers.tiles.*;

import java.util.ArrayList;
import java.util.List;

public class RandomLevelGenerator
{
    private static final int MEDIUM_MAP_SIDE_LENGTH = 10;
    private static final int BIG_MAP_SIDE_LENGTH = 15;
    private static final int CLOSE_RANGE = 2;
    private static final int MEDIUM_RANGE = 3;
    private static  final int ROTATION_ANGLE = -90;

    private List<List<SquareTile>> squareTiles = new ArrayList<List<SquareTile>>();
    private int[][] levelDesign;
    private Vector mapSize = new Vector(rollSideLength(), rollSideLength());
    private Vector startingTilePosition = rollTilePosition();
    private List<Vector> finalTilesPositions = rollFinalTilesPositions();
    private List<Vector> transitionPoints = rollTransitionPoints();
    private RandomLineGenerator randomLineGenerator = new RandomLineGenerator();
    private ConnectionChecker connectionChecker = new ConnectionChecker();
    private RandomLineArranger randomLineArranger = new RandomLineArranger(connectionChecker, finalTilesPositions);

    public List<List<SquareTile>> generateRandomLevel()
    {
        prepareRandomLine();

        while(squareTiles.isEmpty())
        {
            clear();
            prepareRandomLine();
        }

        fillLevel();

        return squareTiles;
    }

    private void prepareRandomLine()
    {
        randomLineGenerator.prepareRandomLineGenerator(this);
        randomLineGenerator.rollConnection();

        levelDesign = randomLineGenerator.getLevelDesign();
        prepareLevel();

        connectionChecker.setSquareTiles(squareTiles);
        connectionChecker.setStartingTile(startingTilePosition);

        randomLineArranger.arrangeLine(randomLineGenerator.getTileLine());
        randomLineArranger.clearTiles();

        squareTiles = randomLineArranger.getSquareTiles();
    }

    void clear()
    {
        mapSize = new Vector(rollSideLength(), rollSideLength());
        startingTilePosition = rollTilePosition();
        finalTilesPositions = rollFinalTilesPositions();
        transitionPoints = rollTransitionPoints();
        randomLineArranger.setFinalTiles(finalTilesPositions);
    }

    private int rollSideLength()
    {
        return (int)Math.floor(20 * Math.exp(-1.1 * Math.pow(Math.random(), 0.6)));
    }

    private Vector rollTilePosition()
    {
        return new Vector((int)Math.floor((mapSize.getX() - 1) * Math.random()), (int)Math.floor((mapSize.getY() - 1) * Math.random()));
    }

    private List<Vector> rollFinalTilesPositions()
    {
        List<Vector> finalTilesPositions = new ArrayList<>();
        Vector tempTilePosition = rollTilePosition();
        int numberOfFinalTiles = 1;

        if((mapSize.getX() > MEDIUM_MAP_SIDE_LENGTH || mapSize.getY() > MEDIUM_MAP_SIDE_LENGTH) && Math.random() < 0.4)
        {
            numberOfFinalTiles = 2;
        }
        else if((mapSize.getX() > BIG_MAP_SIDE_LENGTH || mapSize.getY() > BIG_MAP_SIDE_LENGTH) && Math.random() < 0.1)
        {
            numberOfFinalTiles = 3;
        }

        for(int i = 0; i < numberOfFinalTiles; i++)
        {
            while (Vector.vectorNorm(startingTilePosition, tempTilePosition) < CLOSE_RANGE)
            {
                tempTilePosition = rollTilePosition();
            }

            finalTilesPositions.add(tempTilePosition);
            tempTilePosition = rollTilePosition();
        }

        return finalTilesPositions;
    }

    private List<Vector> rollTransitionPoints()
    {
        List<Vector> transitionPoints = new ArrayList<>();
        Vector previousPoint = startingTilePosition;
        int numberOfTransitionPoints = 2;

        if((mapSize.getX() > MEDIUM_MAP_SIDE_LENGTH || mapSize.getY() > MEDIUM_MAP_SIDE_LENGTH) && Math.random() < 0.33)
        {
            numberOfTransitionPoints += 1;

            if((mapSize.getX() > BIG_MAP_SIDE_LENGTH || mapSize.getY() > MEDIUM_MAP_SIDE_LENGTH) && Math.random() < 0.25)
            {
                numberOfTransitionPoints += 1;
            }
        }

        for(int i = 0; i < numberOfTransitionPoints; i++)
        {
            Vector point = rollTilePosition();

            while(isInvalidTransitionPoint(point, previousPoint))
            {
                point = rollTilePosition();
            }

            transitionPoints.add(point);
            previousPoint = point;
        }

        return transitionPoints;
    }

    private boolean isInvalidTransitionPoint(Vector point, Vector previousPoint)
    {
        return (Vector.isSamePosition(point, startingTilePosition)
                || Vector.isSamePosition(point, finalTilesPositions)
                || Vector.isSamePosition(point, previousPoint)
                || Vector.vectorNorm(point, previousPoint) < MEDIUM_RANGE
                || point.getX() == previousPoint.getX()
                || point.getY() == previousPoint.getY());
    }

    private void fillLevel()
    {
        List<List<SquareTile>> tempSuareTiles = new ArrayList<List<SquareTile>>();
        double randomNumber;

        for(List<SquareTile> tiles: squareTiles)
        {
            List<SquareTile> tempTiles = new ArrayList<>();

            for(SquareTile tile: tiles)
            {
                if(tile.getClass() == TileZero.class)
                {
                    randomNumber = Math.random();

                    if(randomNumber < 0.05)
                    {
                        if(Math.random() < 0.33)
                        {
                            tile = TileFactory.getTile(TileType.FOUR_LINES, rollRotation());
                        }
                        else
                        {
                            tile = TileFactory.getTile(TileType.ZERO_LINES, rollRotation());
                        }
                    }
                    else if(randomNumber < 0.13)
                    {
                        if(Math.random() < 0.5)
                        {
                            tile = TileFactory.getTile(TileType.THREE_LINES, rollRotation());
                        }
                        else
                        {
                            tile = TileFactory.getTile(TileType.HALF_LINE, rollRotation());
                        }
                    }
                    else if(randomNumber < 0.5)
                    {
                        tile = TileFactory.getTile(TileType.TWO_LINES, rollRotation());
                    }
                    else
                    {
                        tile = TileFactory.getTile(TileType.ONE_LINE, rollRotation());
                    }
                }

                tempTiles.add(tile);
            }

            tempSuareTiles.add(tempTiles);
        }

        squareTiles = tempSuareTiles;
    }

    private void prepareLevel()
    {
        squareTiles.clear();

        for(int[] tiles: levelDesign)
        {
            List<SquareTile> temporary = new ArrayList<>();
            for(int tile: tiles)
            {
                int rotation = 0;

                if(tile == TileType.START.getTypeValue())
                {
                    rotation = startingTileRotation();
                }

                temporary.add(TileFactory.getTile(TileType.getTileTypeOfValue(tile), rotation));
            }
            squareTiles.add(temporary);
        }
    }

    private int startingTileRotation()
    {
        Vector firstTile = randomLineGenerator.getTileLine().get(0);
        Vector direction = new Vector(firstTile.getX() - startingTilePosition.getX(), firstTile.getY() - startingTilePosition.getY());

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

    static int rollRotation()
    {
        double randomNumber = Math.random();

        if(randomNumber < 0.25)
        {
            return ROTATION_ANGLE;
        }
        else if(randomNumber < 0.5)
        {
            return 2 * ROTATION_ANGLE;
        }
        else if(randomNumber < 0.75)
        {
            return 3 * ROTATION_ANGLE;
        }

        return 0;
    }

    int[][] getLevelDesign()
    {
        return levelDesign;
    }

    Vector getMapSize()
    {
        return mapSize;
    }

    Vector getStartingTilePosition()
    {
        return startingTilePosition;
    }

    List<Vector> getFinalTilesPositions()
    {
        return finalTilesPositions;
    }

    List<Vector> getTransitionPoints()
    {
        return transitionPoints;
    }
}