package com.patryk.main;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Stage;
import java.util.List;

public class Lattice
{
    private LevelDesign levelDesign = new LevelDesign();
    private List<List<SquareTile>> squareTiles;

    private Vector startingTile;

    private int numberOfFinalTiles = 0;
    private int numberOfGlowingFinalTiles = 0;

    private float tileSize;

    public List<List<SquareTile>> getSquareTiles()
    {
        return squareTiles;
    }

    public Vector getStartingTile()
    {
        return startingTile;
    }

    public boolean isFinished()
    {
        for(List<SquareTile> tempSquareTiles: squareTiles)
        {
            for(SquareTile sqrTiles: tempSquareTiles)
            {
                if(sqrTiles.getTileType() == TileType.FINAL_TILE)
                {
                    numberOfGlowingFinalTiles += 1;
                }
            }
        }

        return numberOfGlowingFinalTiles == numberOfFinalTiles;
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

        numberOfGlowingFinalTiles = 0;
    }

    public void onLatticeTouch()
    {
        turnOff();

        for(List<SquareTile> tempSquareTiles: squareTiles)
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
        for(List<SquareTile> tempSquareTiles: squareTiles)
        {
            for(SquareTile sqrTiles : tempSquareTiles)
            {
                myStage.addActor(sqrTiles.getImageButton());
            }
        }
    }

    private Vector preparePosition(int tileIndexX, int tileIndexY)
    {
        return new Vector(
                (int)(0.05f * Gdx.graphics.getWidth() + this.tileSize * tileIndexX),
                (int)(Gdx.graphics.getHeight()/2f + this.tileSize * (squareTiles.size()-2)/2f  - this.tileSize * tileIndexY));
    }

    public void setLattice(int selectedLevel)
    {
        squareTiles = levelDesign.setLevel(selectedLevel);

        this.tileSize = (0.9f * Gdx.graphics.getWidth()) / squareTiles.get(0).size();

        int tileIndexX;
        int tileIndexY = 0;
        for(List<SquareTile> tempSquareTiles: squareTiles)
        {
            tileIndexX = 0;
            for(SquareTile sqrTiles: tempSquareTiles)
            {
                sqrTiles.setSquareTile(preparePosition(tileIndexX, tileIndexY), tileSize);

                if(sqrTiles.getTileType() == TileType.STARTING_TILE)
                {
                    startingTile = new Vector(tileIndexX, tileIndexY);
                }

                if(sqrTiles.getTileType() == TileType.FINAL_TILE)
                {
                    numberOfFinalTiles += 1;
                }

                tileIndexX += 1;
            }
            tileIndexY += 1;
        }
    }
}
