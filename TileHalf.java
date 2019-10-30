package com.patryk.main;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

public class TileHalf extends SquareTile
{
    public TileHalf(int rotationAngle)
    {
        setRotationAngle(rotationAngle);
    }

    public void initializeTile(Vector position, float size)
    {
        getLinesDirection().add(new Vector(0,1));
        initializeImageButton(new ImageButton(new TextureRegionDrawable(
                new TextureRegion(MyGame.myAssets.getTexture(Assets.TILE_HALF)))), position, size);

        for(Vector vector: getLinesDirection())
        {
            vector.rotateVector(getRotationAngle());
        }
    }
}