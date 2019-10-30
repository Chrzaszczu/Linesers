package com.patryk.main;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

public class TileFour extends SquareTile
{
    public TileFour(int rotationAngle)
    {
        setRotationAngle(rotationAngle);
    }

    public void initializeTile(Vector position, float size)
    {
        getLinesDirection().add(new Vector(0,1));
        getLinesDirection().add(new Vector(1,0));
        getLinesDirection().add(new Vector(0,-1));
        getLinesDirection().add(new Vector(-1,0));
        initializeImageButton(new ImageButton(new TextureRegionDrawable(
                new TextureRegion(MyGame.myAssets.getTexture(Assets.TILE_FOUR)))), position, size);

        for(Vector vector: getLinesDirection())
        {
            vector.rotateVector(getRotationAngle());
        }
    }
}