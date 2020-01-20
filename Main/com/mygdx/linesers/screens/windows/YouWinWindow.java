package Main.com.mygdx.linesers.screens.windows;

import Main.com.mygdx.linesers.screens.GameScreen;
import Main.com.mygdx.linesers.GameState;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.mygdx.linesers.GameState;
import com.mygdx.linesers.MyGame;

public class YouWinWindow
{
    private static final int TEXT_WIDTH = Gdx.graphics.getWidth();
    private static final int TEXT_HEIGHT = (int)(0.13f * Gdx.graphics.getHeight());

    private static final int TEXT_POSITION_X = (int)(Gdx.graphics.getWidth()/2f);
    private static final int TEXT_POSITION_Y = (int)(0.75f * Gdx.graphics.getHeight());
    private static final int NEXT_LEVEL_POSITION_X = (int)(0.5f * Gdx.graphics.getWidth());
    private static final int NEXT_LEVEL_POSITION_Y = (int)(0.15f * Gdx.graphics.getHeight());
    private static final int MENU_POSITION_X = 0;
    private static final int MENU_POSITION_Y = (int)(0.15f * Gdx.graphics.getHeight());

    private ImageButton nextLevel = new ImageButton(
            new TextureRegionDrawable(new TextureRegion(MyGame.myAssets.getTexture(Assets.NEXT_GAME_BUTTON, Gdx.graphics.getWidth()/2, (int)(0.06f * Gdx.graphics.getHeight())))));
    private ImageButton menu = new ImageButton(
            new TextureRegionDrawable(new TextureRegion(MyGame.myAssets.getTexture(Assets.MENU_BUTTON, Gdx.graphics.getWidth()/2, (int)(0.06f * Gdx.graphics.getHeight())))));

    private final Texture youWinText = MyGame.myAssets.getTexture(Assets.YOU_WIN_TEXT, TEXT_WIDTH, TEXT_HEIGHT);

    private Stage youWinStage = new Stage(new ScreenViewport());
    private Stage myStage;
    private GameScreen gameScreen;

    public YouWinWindow(GameScreen gameScreen, Stage myStage)
    {
        this.myStage = myStage;
        this.gameScreen = gameScreen;
    }

    public void initializeWindow()
    {
        youWinStage.addActor(nextLevel);
        youWinStage.addActor(menu);

        nextLevel.setPosition(NEXT_LEVEL_POSITION_X, NEXT_LEVEL_POSITION_Y);
        menu.setPosition(MENU_POSITION_X, MENU_POSITION_Y);

        prepareButtonsListener();
    }

    private void prepareButtonsListener()
    {
        menu.addListener(new ClickListener()
        {
            @Override
            public void clicked(InputEvent event, float x, float y)
            {
                MyGame.myAssets.playSound(Assets.SOUND_OF_BUTTON);
                gameScreen.setGameState(GameState.QUIT);
                Gdx.input.setInputProcessor(myStage);
            }
        });

        nextLevel.addListener(new ClickListener()
        {
            @Override
            public void clicked(InputEvent event, float x, float y)
            {
                MyGame.myAssets.playSound(Assets.SOUND_OF_BUTTON);
                gameScreen.setGameState(GameState.CHANGE_LEVEL);
                Gdx.input.setInputProcessor(myStage);
            }
        });
    }

    public void draw()
    {
        MyGame.batch.begin();
        MyGame.batch.draw(youWinText, TEXT_POSITION_X - youWinText.getWidth()/2f, TEXT_POSITION_Y, youWinText.getWidth(), youWinText.getHeight());
        MyGame.batch.end();

        Gdx.input.setInputProcessor(youWinStage);

        youWinStage.act(Gdx.graphics.getDeltaTime());
        youWinStage.draw();
    }

    public void dispose()
    {
        youWinText.dispose();
        youWinStage.dispose();
        nextLevel = null;
        menu = null;
    }
}