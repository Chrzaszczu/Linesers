package com.patryk.main;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

public class TileOne extends SquareTile
{
    private boolean glowing = false;

    public TileOne(int rotationAngle)
    {
        super.setRotationAngle(rotationAngle);
    }

    public void setTileProperties(Vector position, float size)
    {
        super.getLinesDirection().add(new Vector(0,1));
        super.getLinesDirection().add(new Vector(0,-1));
        super.setImageButton(new ImageButton(new TextureRegionDrawable(
                new TextureRegion(MyGame.myAssets.getTexture(Assets.TILE_ONE)))), position, size);

        for(Vector vector: super.getLinesDirection())
        {
            vector.rotateVector(getRotationAngle());
        }
    }

    public boolean isGlowing()
    {
        return glowing;
    }

    public void setGlowing(boolean glowing)
    {
        this.glowing = glowing;
        updateImage();
    }

    public void updateImage()
    {
        if(isGlowing())
        {
            super.getImageButton().getStyle().imageUp = new TextureRegionDrawable(
                    new TextureRegion(MyGame.myAssets.getTexture(Assets.GLOWING_TILE_ONE)));
        }
        else
        {
            super.getImageButton().getStyle().imageUp = new TextureRegionDrawable(
                    new TextureRegion(MyGame.myAssets.getTexture(Assets.TILE_ONE)));
        }
    }

}
