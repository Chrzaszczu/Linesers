package com.patryk.main;

import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

public class TileStart extends SquareTile
{
    public TileStart(int rotationAngle)
    {
        setRotationAngle(rotationAngle);
    }

    public void initializeTile(Vector position, float size)
    {
        getLinesDirection().add(new Vector(0,1));

        tileAnimation = MyGame.myAssets.prepareAnimation(
                Assets.TILE_START, NUMBER_OF_COLUMNS, NUMBER_OF_ROWS, FRAME_DURATION);

        initializeImageButton(new ImageButton(new TextureRegionDrawable(getAnimationFrame(0f))), position, size);

        for(Vector vector: getLinesDirection())
        {
            vector.rotateVector(getRotationAngle());
        }
    }

    public void updateImage(float stateTime)
    {
        getImageButton().getStyle().imageUp = new TextureRegionDrawable(getAnimationFrame(stateTime));
    }

    public int rotateTile(int rotationAngle)
    {
        return getRotationAngle();
    }

    public boolean isGlowing()
    {
        return true;
    }
}