package com.mygdx.linesers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import java.util.LinkedList;
import java.util.List;

public class Lattice
{
    private List<List<SquareTile>> squareTiles;
    private List<LaserPosition> laserPositions = new LinkedList<>();
    private Animation<TextureRegion> laserAnimation = MyGame.myAssets.prepareAnimation(Assets.LASER, 6, 1, 0.05f);
    private ConnectionChecker connectionChecker;

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
                    prepareLaserX(start, end), prepareLaserY(start, end), tileSize/2, tileSize/2,
                    tileSize, tileSize,1,1, rotation);
        }
        MyGame.batch.end();
    }

    private int prepareLaserX(Vector start, Vector end)
    {
        return start.getX() + (end.getX() - start.getX())/2;
    }

    private int prepareLaserY(Vector start, Vector end)
    {
        return start.getY() + (end.getY() - start.getY())/2;
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

    public void setLattice(int selectedLevel)
    {
        squareTiles = LevelDesign.setLevel(selectedLevel);
        numberOfFinalTiles = 0;
        numberOfGlowingFinalTiles = 0;

        this.tileSize = setTileSize();

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

        initializeTilesListener();

        connectionChecker = new ConnectionChecker(getSquareTiles(), getStartingTile(), getLaserPositions());
        connectionChecker.assambleConnections();
    }

    private float setTileSize()
    {
        float sizeW = (0.9f * Gdx.graphics.getWidth()) / (float)squareTiles.get(0).size();
        float sizeH = (0.8f * Gdx.graphics.getHeight()) / (float)squareTiles.size();

        return (sizeW < sizeH) ? sizeW : sizeH;
    }

    private Vector preparePosition(Vector vector)
    {
        return new Vector((int)(Gdx.graphics.getWidth()/2 - this.tileSize * squareTiles.get(0).size()/2 + this.tileSize * vector.getX()),
                (int)(Gdx.graphics.getHeight()/2f + this.tileSize * (squareTiles.size()-2f)/2f  - this.tileSize * vector.getY()));
    }

    private void initializeTilesListener()
    {
        for(List<SquareTile> tempSquareTiles: squareTiles)
        {
            for(SquareTile sqrTiles: tempSquareTiles)
            {
                sqrTiles.getImageButton().addListener(new ClickListener()
                {
                    @Override
                    public void clicked(InputEvent event, float x, float y)
                    {
                        sqrTiles.playSound();
                        sqrTiles.getImageButton().setRotation(rotateTile(sqrTiles));
                        turnOff();
                        connectionChecker.assambleConnections();

                        if(connectionChecker.getNumberOfGowingFinalTiles() == numberOfFinalTiles)
                        {
                            MyGame.myAssets.playSound(Assets.WIN_LASER_SOUND);
                        }
                    }
                });
            }
        }
    }

    private float rotateTile(SquareTile sqrTile)
    {
        return sqrTile.rotateTile(SquareTile.ROTATION_ANGLE_STEP);
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

    public boolean isFinished()
    {
        return connectionChecker.getNumberOfGowingFinalTiles() == numberOfFinalTiles;
    }
}
