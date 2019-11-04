package com.patryk.main;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;

import java.util.LinkedList;
import java.util.List;

public class Lattice
{
    private LevelDesign levelDesign = new LevelDesign();
    private List<List<SquareTile>> squareTiles;
    private List<LaserPosition> laserPositions = new LinkedList<LaserPosition>();
    private Animation<TextureRegion> laserAnimation = MyGame.myAssets.prepareAnimation(Assets.LASER, 6, 1, 0.05f);

    private Vector startingTile;

    private static int numberOfFinalTiles = 0;
    private static int numberOfGlowingFinalTiles = 0;

    private float tileSize;

    public List<List<SquareTile>> getSquareTiles()
    {
        return squareTiles;
    }

    public Vector getStartingTile()
    {
        return startingTile;
    }

    public List<LaserPosition> getLaserPositions()
    {
        return laserPositions;
    }

    public void drawLasers(float stateTime)
    {
        Vector start;
        Vector end;
        int rotation;

        MyGame.batch.begin();
        for(LaserPosition laserPosition: laserPositions)
        {
            start = preparePosition(laserPosition.getStartingPoint());
            end = preparePosition(laserPosition.getEndingPoint());
            rotation = (end.getX() - start.getX() != 0) ? 90 : 0;

            MyGame.batch.draw(laserAnimation.getKeyFrame(stateTime, true),
                    start.getX() + (end.getX() - start.getX())/2, start.getY() + (end.getY() - start.getY())/2,
                     tileSize/2, tileSize/2, tileSize, tileSize,1,1, rotation);
        }
        MyGame.batch.end();
    }

    public boolean isFinished()
    {
        numberOfGlowingFinalTiles = 0;

        for(List<SquareTile> tempSquareTiles: squareTiles)
        {
            for(SquareTile sqrTiles: tempSquareTiles)
            {
                if(sqrTiles.getClass() == TileFinal.class && sqrTiles.isGlowing())
                {
                    numberOfGlowingFinalTiles += 1;
                }
            }
        }

        if(numberOfGlowingFinalTiles == numberOfFinalTiles)
        {
            MyGame.myAssets.getSound(Assets.WIN_LASER_SOUND).play(MyGame.options.getSoundVolume());
        }

        return numberOfGlowingFinalTiles == numberOfFinalTiles;
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
                    sqrTiles.playSound();
                    sqrTiles.getImageButton().setRotation(sqrTiles.rotateTile(sqrTiles.ROTATION_ANGLE_STEP));
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

        numberOfGlowingFinalTiles = 0;
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

    private Vector preparePosition(Vector vector)
    {
        return new Vector((int)(0.05f * Gdx.graphics.getWidth() + this.tileSize * vector.getX()),
                (int)(Gdx.graphics.getHeight()/2f + this.tileSize * (squareTiles.size()-2f)/2f  - this.tileSize * vector.getY()));
    }

    public void setLattice(int selectedLevel)
    {
        squareTiles = levelDesign.setLevel(selectedLevel);
        numberOfFinalTiles = 0;
        numberOfGlowingFinalTiles = 0;

        this.tileSize = (0.9f * Gdx.graphics.getWidth()) / (float)squareTiles.get(0).size();

        int tileIndexX;
        int tileIndexY = 0;
        for(List<SquareTile> tempSquareTiles: squareTiles)
        {
            tileIndexX = 0;
            for(SquareTile sqrTiles: tempSquareTiles)
            {
                sqrTiles.initializeTile(preparePosition(new Vector(tileIndexX, tileIndexY)), tileSize);

                if(sqrTiles.getClass() == TileStart.class)
                {
                    startingTile = new Vector(tileIndexX, tileIndexY);
                }

                if(sqrTiles.getClass() == TileFinal.class)
                {
                    numberOfFinalTiles += 1;
                }

                tileIndexX += 1;
            }
            tileIndexY += 1;
        }
    }
}
