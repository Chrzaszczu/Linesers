package Main.com.mygdx.linesers.levels;

import Main.com.mygdx.linesers.assets.Assets;
import Main.com.mygdx.linesers.tiles.SquareTile;
import Main.com.mygdx.linesers.tiles.TileFinal;
import Main.com.mygdx.linesers.tiles.TileStart;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import Main.com.mygdx.linesers.MyGame;

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

    private float tileSize;

    public Lattice()
    {
    }

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

    public void prepareLevelDesign(int selectedLevel)
    {
        squareTiles = LevelDesign.getLevel(selectedLevel);
    }

    public void prepareRandomLevelDesign()
    {
        squareTiles = LevelDesign.getRandomLevel();
    }

    public void prepareLattice()
    {
        numberOfFinalTiles = 0;

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

        connectionChecker = new ConnectionChecker(getSquareTiles(), getStartingTile(), getLaserPositions());
        connectionChecker.assambleConnections();
    }

    private float setTileSize()
    {
        float sizeW = (0.9f * Gdx.graphics.getWidth()) / (float)squareTiles.get(0).size();
        float sizeH = (0.8f * Gdx.graphics.getHeight()) / (float)squareTiles.size();

        return Math.min(sizeW, sizeH);
    }

    private Vector preparePosition(Vector vector)
    {
        return new Vector((int)(Gdx.graphics.getWidth()/2 - this.tileSize * squareTiles.get(0).size()/2 + this.tileSize * vector.getX()),
                (int)(Gdx.graphics.getHeight()/2f + this.tileSize * (squareTiles.size()-2f)/2f  - this.tileSize * vector.getY()));
    }

    public float rotateTile(SquareTile sqrTile)
    {
        return sqrTile.rotateTile(SquareTile.ROTATION_ANGLE_STEP);
    }

    public void assambleConnections()
    {
        connectionChecker.assambleConnections();
    }

    public boolean isFinished()
    {
        return connectionChecker.getNumberOfGlowingFinalTiles() == numberOfFinalTiles;
    }
}