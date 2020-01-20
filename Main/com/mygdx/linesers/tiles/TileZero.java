package Main.com.mygdx.linesers.tiles;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.mygdx.linesers.MyGame;
import Main.com.mygdx.linesers.levels.Vector;

public class TileZero extends SquareTile
{
    public TileZero(int rotationAngle)
    {
        setRotationAngle(rotationAngle);
    }

    public void initializeTile(Vector position, float size)
    {
        getLinesDirection().add(new Vector(0,1));
        initializeImageButton(new ImageButton(new TextureRegionDrawable(
                new TextureRegion(MyGame.myAssets.getTexture(Assets.TILE_ZERO)))), position, size);

        for(Vector vector: getLinesDirection())
        {
            vector.rotateVector(getRotationAngle());
        }
    }

    public void initializeTile()
    {
        getLinesDirection().add(new Vector(0,1));

        for(Vector vector: getLinesDirection())
        {
            vector.rotateVector(getRotationAngle());
        }
    }

    public int rotateTile(int rotationAngle)
    {
        return getRotationAngle();
    }

    public void playSound()
    {
    }
}
