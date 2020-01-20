package Main.com.mygdx.linesers.levels;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

class RandomLineGenerator
{
    private int[][] levelDesign;
    private Vector mapSize;
    private Vector startingTilePosition;
    private List<Vector> finalTilesPositions;
    private List<Vector> transitionPoints;
    private Vector tile;

    private List<Vector> tileLine = new LinkedList<>();

    void prepareRandomLineGenerator(RandomLevelGenerator randomLevelGenerator)
    {
        levelDesign = randomLevelGenerator.getLevelDesign();
        mapSize = randomLevelGenerator.getMapSize();
        startingTilePosition = randomLevelGenerator.getStartingTilePosition();
        finalTilesPositions = randomLevelGenerator.getFinalTilesPositions();
        transitionPoints = randomLevelGenerator.getTransitionPoints();
    }

    void rollConnection()
    {
        levelDesign = new int[mapSize.getY()][mapSize.getX()];

        tile = startingTilePosition;

        levelDesign[startingTilePosition.getY()][startingTilePosition.getX()] = 6;
        for (Vector position : finalTilesPositions)
        {
            levelDesign[position.getY()][position.getX()] = 7;
        }

        tileLine.clear();

        connectTransitionPoints();
        connectFinalTiles();
    }

    private void connectTransitionPoints()
    {
        for (Vector transition : transitionPoints)
        {
            do
            {
                if(Vector.isSamePosition(finalTilesPositions, prepareNearestTiles(tile)))
                {
                    break;
                }

                tile = getNextTilePosition(tile, transition);
                if(tile == null)
                {
                    break;
                }

                tileLine.add(new Vector(tile.getX(), tile.getY()));
                if(levelDesign[tile.getY()][tile.getX()] == 0)
                {
                    levelDesign[tile.getY()][tile.getX()] = 1;
                }
            } while(Vector.vectorNorm(tile, transition) != 0);
        }

    }

    private void connectFinalTiles()
    {
        for(Vector finalTile: finalTilesPositions)
        {
            do
            {
                if(Vector.isSamePosition(finalTile, prepareNearestTiles(tile)))
                {
                    break;
                }

                tile = getNextTilePosition(tile, finalTile);
                if(tile == null)
                {
                    break;
                }

                tileLine.add(new Vector(tile.getX(), tile.getY()));
                if(levelDesign[tile.getY()][tile.getX()] == 0)
                {
                    levelDesign[tile.getY()][tile.getX()] = 1;
                }
            } while(Vector.vectorNorm(tile, finalTile) != 0);
        }
    }

    private Vector getNextTilePosition(Vector tile, Vector transitionPoint)
    {
        return rollTile(prepareNearestTiles(tile), transitionPoint);
    }

    private List<Vector> prepareNearestTiles(Vector tile)
    {
        List<Vector> nearestTiles = new ArrayList<>();
        List<int[]> directions = new ArrayList<>(Arrays.asList(new int[]{1, 0}, new int[]{-1, 0}, new int[]{0, 1}, new int[]{0, -1}));

        for(int[] dir: directions)
        {
            if(getTile(tile, dir[0], dir[1]) != null)
            {
                nearestTiles.add(getTile(tile, dir[0], dir[1]));
            }
        }

        return nearestTiles;
    }

    private Vector getTile(Vector tile, int x, int y)
    {
        if(tile.getX() + x < mapSize.getX() && tile.getX() + x >= 0
                && tile.getY() + y < mapSize.getY() && tile.getY() + y >= 0)
        {
            if(levelDesign[tile.getY() + y][tile.getX() + x] != 6)
            {
                return new Vector(tile.getX() + x, tile.getY() + y);
            }
        }

        return null;
    }

    private Vector rollTile(List<Vector> nearestTiles, Vector transitionPoint)
    {//TODO: wez to ogarnij
        List<Double> lengths = new ArrayList<>();
        List<Vector> minList;
        List<Vector> secondMinList;
        double min;
        double secondMin;

        for(Vector nearTile: nearestTiles)
        {
            lengths.add(Vector.vectorNorm(nearTile, transitionPoint));
            if(Vector.isSamePosition(nearTile, transitionPoint))
            {
                return transitionPoint;
            }
        }

        if(lengths.stream().min(Double::compareTo).isPresent())
        {
            min = lengths.stream().min(Double::compareTo).get();
            minList = nearestTiles.stream().filter(nearTile -> Vector.vectorNorm(nearTile, transitionPoint) == min).collect(Collectors.toList());

            if(!minList.isEmpty())
            {
                for (Vector minL: minList)
                {
                    if (!Vector.isSamePosition(minL, finalTilesPositions))
                    {
                        return minL;
                    }
                }
            }

            if(lengths.stream().filter(r -> r != min).min(Double::compareTo).isPresent())
            {
                secondMin = lengths.stream().filter(r -> r != min).min(Double::compareTo).get();
                secondMinList = nearestTiles.stream().filter(nearTile -> Vector.vectorNorm(nearTile, transitionPoint) == secondMin).collect(Collectors.toList());

                if(!secondMinList.isEmpty())
                {
                    for (Vector secondMinL: secondMinList)
                    {
                        if (!Vector.isSamePosition(secondMinL, finalTilesPositions))
                        {
                            return secondMinL;
                        }
                    }
                }
            }
        }

        return null;
    }

    int[][] getLevelDesign()
    {
        return levelDesign;
    }

    List<Vector> getTileLine()
    {
        return tileLine;
    }
}
