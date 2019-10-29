package com.patryk.main;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

public class TileTwo extends SquareTile
{
    public TileTwo(int rotationAngle)
    {
        setRotationAngle(rotationAngle);
    }

    public void initializeTile(Vector position, float size)
    {
        getLinesDirection().add(new Vector(0,1));
        getLinesDirection().add(new Vector(1,0));
        initializeImageButton(new ImageButton(new TextureRegionDrawable(
                new TextureRegion(MyGame.myAssets.getTexture(Assets.TILE_TWO)))), position, size);

        tileAnimation = MyGame.myAssets.prepareAnimation(
                Assets.GLOWING_TILE_TWO, NUMBER_OF_COLUMNS, NUMBER_OF_ROWS, FRAME_DURATION);

        for(Vector vector: getLinesDirection())
        {
            vector.rotateVector(getRotationAngle());
        }
    }

    public void updateImage(float stateTime)
    {
        if(isGlowing())
        {
            getImageButton().getStyle().imageUp = new TextureRegionDrawable(getAnimationFrame(stateTime));
        }
        else
        {
            getImageButton().getStyle().imageUp = new TextureRegionDrawable(
                    new TextureRegion(MyGame.myAssets.getTexture(Assets.TILE_TWO)));
        }
    }
}