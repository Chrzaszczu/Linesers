package com.patryk.main;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Stage;

import java.util.LinkedList;
import java.util.List;

public class SelectLevel
{
    private final static int NUMBER_OF_LEVELS_PER_PAGE = 12;
    private final static int NUMBER_OF_ROWS = 3;

    private List<LevelButton> levelButtons = new LinkedList<LevelButton>();
    private float buttonSize;
    private int page;

    public SelectLevel(int page)
    {
        this.page = page;
    }

    public void nextPage()
    {

    }

    public void previousPage()
    {
        
    }

    public List<LevelButton> getLevelButtons()
    {
        return levelButtons;
    }

    public void setLevelButtons()
    {
        for(int levelNumber = 0; levelNumber < NUMBER_OF_LEVELS_PER_PAGE; ++levelNumber)
        {
                levelButtons.add(new LevelButton(levelNumber));
                levelButtons.get(levelNumber).updateImage();
        }

        setPositions();
    }

    public void addActors(Stage myStage)
    {
        for(LevelButton lButton: levelButtons)
        {
            myStage.addActor(lButton.getImageButton());
        }
    }

    private int prepareX(int indexX)
    {
        return (int)(0.1f * Gdx.graphics.getWidth() + (0.1f * Gdx.graphics.getWidth() + this.buttonSize) * indexX);
    }

    private int prepareY(int indexY)
    {
        return (int)(Gdx.graphics.getHeight()/2f + 2 * this.buttonSize - (this.buttonSize + 0.1f * Gdx.graphics.getWidth()) * indexY);
    }

    private void setPositions()
    {
        this.buttonSize = (0.8f * Gdx.graphics.getWidth()) / (NUMBER_OF_LEVELS_PER_PAGE/NUMBER_OF_ROWS);
        int indexX = 0;
        int indexY = 0;

        for(LevelButton lButtons: levelButtons)
        {
            lButtons.getImageButton().setPosition(prepareX(indexX), prepareY(indexY));
            lButtons.getImageButton().setSize(this.buttonSize, this.buttonSize);

            indexX += 1;
            if(indexX == NUMBER_OF_ROWS)
            {
                indexX = 0;
                indexY += 1;
            }
        }
    }
}
