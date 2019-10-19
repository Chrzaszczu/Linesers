package com.patryk.main;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

public class LevelButton
{
    private int levelNumber;
    private ImageButton imageButton;

    public void setLevelNumber(int levelNumber)
    {
        this.levelNumber = levelNumber;
    }

    public int getLevelNumber()
    {
        return this.levelNumber;
    }

    public ImageButton getImageButton()
    {
        return imageButton;
    }

    public void updateImage()
    {
        this.imageButton = new ImageButton(new TextureRegionDrawable(new TextureRegion(MyGame.myAssets.get("START.png", Texture.class))));
    }

    public LevelButton(int levelNumber)
    {
        this.levelNumber = levelNumber;
    }
}
