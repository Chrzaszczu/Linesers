package com.patryk.main;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Stage;

import java.util.ArrayList;

public class Lattice
{
    private LevelDesign levelDesign = new LevelDesign();
    private ArrayList<ArrayList<SquareTile>> squareTiles;

    private Vector startingTile;

    private int numberOfFinalTiles = 0;
    private int numberOfGlowingFinalTiles = 0;

    private float tileSize;

    private boolean isFinished()
    {
        if(numberOfGlowingFinalTiles == numberOfFinalTiles)
        {
            return true;
        }
        else
        {
            return false;
        }
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

    private void turnOff()
    {
        for(ArrayList<SquareTile> tempSquareTiles: squareTiles)
        {
            for(SquareTile sqrTiles: tempSquareTiles)
            {
                sqrTiles.setGlowing(false);
            }
        }

        numberOfGlowingFinalTiles = 0;
    }

    private boolean isAnotherLine(int tileX, int tileY, Vector lineDirectionA, Vector lineDirectionB)
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

    private void checkConnections(int nextTileX, int nextTileY, Vector previousLineDirection)
    {
        for(Vector lineDirection: squareTiles.get(nextTileY).get(nextTileX).getLinesDirection())
        {
            if(Vector.normOfSum(lineDirection, previousLineDirection) == 0)
            {
                squareTiles.get(nextTileY).get(nextTileX).setGlowing(true);

                if(squareTiles.get(nextTileY).get(nextTileX).getTileType() == TileType.FINAL_TILE)
                {
                    numberOfGlowingFinalTiles += 1;
                }

                for(Vector lineDirectionB: squareTiles.get(nextTileY).get(nextTileX).getLinesDirection())
                {
                    if(isAnotherLine(nextTileX, nextTileY, lineDirection, lineDirectionB))
                    {
                        checkConnections(nextTileX + lineDirectionB.getX(),
                                nextTileY - lineDirectionB.getY(), lineDirectionB);
                    }
                }
            }
        }
    }

    public boolean checkConnections()
    {
        int startingTileX = startingTile.getX();
        int startingTileY = startingTile.getY();

        for(Vector lineDirection: squareTiles.get(startingTileY).get(startingTileX).getLinesDirection())
        {
            if(lineDirection.vectorNorm() > 0)
            {
                if(isWithinLattice(startingTileX + lineDirection.getX(), startingTileY - lineDirection.getY()))
                {
                    checkConnections(startingTileX + lineDirection.getX(),
                            startingTileY - lineDirection.getY(), lineDirection);
                }
            }
        }

        return isFinished();
    }

    public void onLatticeTouch()
    {
        turnOff();

        for(ArrayList<SquareTile> tempSquareTiles: squareTiles)
        {
            for(SquareTile sqrTiles: tempSquareTiles)
            {
                if(sqrTiles.getImageButton().isPressed())
                {
                    sqrTiles.getImageButton().setRotation(sqrTiles.rotateTile(sqrTiles.ROTATION_ANGLE_STEP));
                }
            }
        }
    }

    public void addActors(Stage myStage)
    {
        for(ArrayList<SquareTile> tempSquareTiles: squareTiles)
        {
            for(SquareTile sqrTiles : tempSquareTiles)
            {
                myStage.addActor(sqrTiles.getImageButton());
            }
        }
    }

    public void setLattice(int selectedLevel)
    {
        squareTiles = levelDesign.setLevel(selectedLevel);

        this.tileSize = (0.8f * Gdx.graphics.getWidth()) / squareTiles.get(0).size();

        int tilePositionX;
        int tilePositionY = 0;
        for(ArrayList<SquareTile> tempSquareTiles: squareTiles)
        {
            tilePositionX = 0;
            for(SquareTile sqrTiles: tempSquareTiles)
            {
                sqrTiles.setSquareTile(new Vector(
                        (int)(0.1f * Gdx.graphics.getWidth() + tileSize * tilePositionX),
                        (int)(Gdx.graphics.getHeight()/2 + tileSize * (squareTiles.size()-2)/2  - tileSize * tilePositionY)),
                        tileSize);

                if(sqrTiles.getTileType() == TileType.STARTING_TILE)
                {
                    startingTile = new Vector(tilePositionX, tilePositionY);
                }

                if(sqrTiles.getTileType() == TileType.FINAL_TILE)
                {
                    numberOfFinalTiles += 1;
                }

                tilePositionX += 1;
            }
            tilePositionY += 1;
        }
    }
}
