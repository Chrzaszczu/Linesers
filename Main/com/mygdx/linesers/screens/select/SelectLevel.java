package Main.com.mygdx.linesers.screens.select;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.Stage;
import Main.com.mygdx.linesers.MyGame;

import java.util.LinkedList;
import java.util.List;

public class SelectLevel
{
    private final static int NUMBER_OF_LEVELS_PER_PAGE = 12;
    private final static int NUMBER_OF_ROWS = 3;

    private List<LevelButton> levelButtons = new LinkedList<>();
    private FreeTypeFontGenerator generator = new FreeTypeFontGenerator(MyGame.myAssets.getFont());
    private FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
    private BitmapFont font;

    private float buttonSize;
    private int page;

    public SelectLevel(int page)
    {
        this.page = page;
    }

    public void nextPage()
    {
        if (page + 1 < Math.ceil((double) MyGame.myAssets.numberOfMaps() / (double) NUMBER_OF_LEVELS_PER_PAGE))
        {
            page += 1;
            for(LevelButton lButton : levelButtons)
            {
                if(lButton.getLevelNumber() + NUMBER_OF_LEVELS_PER_PAGE < MyGame.myAssets.numberOfMaps())
                {
                    lButton.enable();
                }
                else
                {
                    lButton.disable();
                }
                lButton.setLevelNumber(lButton.getLevelNumber() + NUMBER_OF_LEVELS_PER_PAGE);
                lButton.updateImage();
            }
        }
    }

    public void previousPage()
    {
        if (page > 0)
        {
            page -= 1;
            for (LevelButton lButton : levelButtons)
            {
                if (lButton.getLevelNumber() - NUMBER_OF_LEVELS_PER_PAGE >= 0)
                {
                    lButton.enable();
                }
                else
                {
                    lButton.disable();
                }
                lButton.setLevelNumber(lButton.getLevelNumber() - NUMBER_OF_LEVELS_PER_PAGE);
                lButton.updateImage();
            }
        }
    }

    public List<LevelButton> getLevelButtons()
    {
        return levelButtons;
    }

    public void setLevelButtons()
    {
        int numberOfMaps = MyGame.myAssets.numberOfMaps();
        int numberOfButtons = (NUMBER_OF_LEVELS_PER_PAGE < numberOfMaps) ? NUMBER_OF_LEVELS_PER_PAGE : numberOfMaps;

        for(int levelNumber = 0; levelNumber < numberOfButtons; levelNumber++)
        {
            levelButtons.add(new LevelButton(levelNumber));
            levelButtons.get(levelNumber).updateImage();
        }

        setFont();
        setPositions();
    }

    private void setFont()
    {
        parameter.size = (int)(0.06f * Gdx.graphics.getWidth());
        font = generator.generateFont(parameter);
        font.setColor(Color.GRAY);

        generator.dispose();
        parameter = null;
    }

    public void drawLevelNumbers()
    {
        MyGame.batch.begin();
        for(LevelButton button: levelButtons)
        {
            if(button.getImageButton().isVisible())
            {
                font.draw(MyGame.batch, String.valueOf(button.getLevelNumber() + 1), button.getPositionX(), fontPositionY(button), buttonSize, 1, false);
            }
        }
        MyGame.batch.end();
    }

    private float fontPositionY(LevelButton button)
    {
        return button.getPositionY() + button.getImageButton().getHeight()/2 + font.getXHeight()/2;
    }

    private void setPositions()
    {
        this.buttonSize = (0.6f * Gdx.graphics.getWidth()) / (NUMBER_OF_LEVELS_PER_PAGE / NUMBER_OF_ROWS);
        int indexX = 0;
        int indexY = 0;

        for (LevelButton lButtons : levelButtons)
        {
            lButtons.getImageButton().setPosition(prepareX(indexX), prepareY(indexY));
            lButtons.getImageButton().setSize(this.buttonSize, this.buttonSize);

            indexX += 1;
            if (indexX == NUMBER_OF_ROWS)
            {
                indexX = 0;
                indexY += 1;
            }
        }
    }

    private int prepareX(int indexX)
    {
        return (int) (0.175f * Gdx.graphics.getWidth() + (0.1f * Gdx.graphics.getWidth() + this.buttonSize) * indexX);
    }

    private int prepareY(int indexY)
    {
        return (int) (0.95f * Gdx.graphics.getHeight() / 2f + 2f * this.buttonSize - (this.buttonSize + 0.05f * Gdx.graphics.getWidth()) * indexY);
    }

    public void addActors(Stage myStage)
    {
        for (LevelButton lButton : levelButtons)
        {
            myStage.addActor(lButton.getImageButton());
        }
    }

    public void dispose()
    {
       font.dispose();
    }
}
