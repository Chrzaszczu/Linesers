package com.patryk.main;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

public class LevelButton
{
    private int levelNumber;
    private ImageButton imageButton;

    public LevelButton(int levelNumber)
    {
        this.levelNumber = levelNumber;
    }

    public void enable()
    {
        imageButton.setVisible(true);
        imageButton.setTouchable(Touchable.enabled);
    }

    public void disable()
    {
        imageButton.setVisible(false);
        imageButton.setTouchable(Touchable.disabled);
    }

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
        this.imageButton = new ImageButton(new TextureRegionDrawable(
                new TextureRegion(MyGame.myAssets.getTexture(Assets.LEVEL_LOCKED))));
    }
}
