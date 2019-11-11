package com.mygdx.linesers;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

import java.util.stream.Collectors;

public class LevelButton
{
    private int levelNumber;
    private ImageButton imageButton = new ImageButton(new TextureRegionDrawable(
            new TextureRegion(MyGame.myAssets.getTexture(Assets.LEVEL_LOCKED))));

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

    public float getPositionX()
    {
        return imageButton.getX();
    }

    public float getPositionY()
    {
        return imageButton.getY();
    }

    public ImageButton getImageButton()
    {
        return imageButton;
    }

    public void updateImage()
    {
        if(MyGame.options.getFinishedLevels().contains(levelNumber))
        {
            this.imageButton.getStyle().imageUp = new TextureRegionDrawable(
                    new TextureRegion(MyGame.myAssets.getTexture(Assets.LEVEL_FINISHED)));
        }
        else
        {
            this.imageButton.getStyle().imageUp = new TextureRegionDrawable(
                    new TextureRegion(MyGame.myAssets.getTexture(Assets.LEVEL_LOCKED)));
        }
    }
}
