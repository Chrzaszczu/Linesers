package com.mygdx.linesers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import static com.mygdx.linesers.Menu.MINOR_BUTTONS_HEIGHT;
import static com.mygdx.linesers.Menu.MINOR_BUTTONS_WIDTH;

public class PauseWindow
{
    private final int WINDOW_WIDTH = (int)(0.8 * Gdx.graphics.getWidth());
    private final int WINDOW_HEIGHT = (int)(0.5 * Gdx.graphics.getWidth());
    private final int TEXT_WIDTH = (int)(0.6f * Gdx.graphics.getWidth());
    private final int TEXT_HEIGHT = (int)(0.13f * Gdx.graphics.getWidth());

    private ImageButton resume = new ImageButton(
            new TextureRegionDrawable(new TextureRegion(MyGame.myAssets.getTexture(Assets.OK_BUTTON, MINOR_BUTTONS_WIDTH, MINOR_BUTTONS_HEIGHT))));
    private ImageButton close = new ImageButton(
            new TextureRegionDrawable(new TextureRegion(MyGame.myAssets.getTexture(Assets.CLOSE_BUTTON, MINOR_BUTTONS_WIDTH, MINOR_BUTTONS_HEIGHT))));

    private Texture pauseWindow = MyGame.myAssets.getTexture(Assets.PANEL, WINDOW_WIDTH, WINDOW_HEIGHT);
    private Texture resumeText = MyGame.myAssets.getTexture(Assets.RESUME, TEXT_WIDTH, TEXT_HEIGHT);

    private float windowPositionX;
    private float windowPositionY;
    private float textPositionX;
    private float textPositionY;
    private float resumeX;
    private float resumeY;
    private float closeX;
    private float closeY;

    private Stage pauseStage = new Stage(new ScreenViewport());
    private InputMultiplexer inputMultiplexer = new InputMultiplexer();
    private Stage myStage;
    private MainGame mainGame;

    public PauseWindow(MainGame mainGame, Stage myStage)
    {
        this.myStage = myStage;
        this.mainGame = mainGame;
    }

    public void initializeWindow()
    {
        windowPositionX = Gdx.graphics.getWidth()/2 - pauseWindow.getWidth()/2;
        windowPositionY = Gdx.graphics.getHeight()/2 - pauseWindow.getHeight()/2;
        textPositionX = Gdx.graphics.getWidth()/2 - resumeText.getWidth()/2;
        textPositionY = Gdx.graphics.getHeight()/2;
        resumeX = 0.68f * Gdx.graphics.getWidth();
        resumeY = 0.4f * Gdx.graphics.getHeight();
        closeX = 0.2f * Gdx.graphics.getWidth();
        closeY = 0.4f * Gdx.graphics.getHeight();

        pauseStage.addActor(resume);
        pauseStage.addActor(close);

        inputMultiplexer.addProcessor(pauseStage);
        inputMultiplexer.addProcessor(myStage);

        resume.setPosition(resumeX, resumeY);
        close.setPosition(closeX, closeY);

        prepareButtonsListener();
    }

    private void prepareButtonsListener()
    {
        close.addListener(new ClickListener()
        {
            @Override
            public void clicked(InputEvent event, float x, float y)
            {
                MyGame.myAssets.playSound(Assets.SOUND_OF_BUTTON);
                mainGame.setGameState(GameState.QUIT);
            }
        });

        resume.addListener(new ClickListener()
        {
            @Override
            public void clicked(InputEvent event, float x, float y)
            {
                MyGame.myAssets.playSound(Assets.SOUND_OF_BUTTON);
                mainGame.setGameState(GameState.RUNNING);
            }
        });
    }

    public void draw()
    {
        MyGame.batch.begin();
        MyGame.batch.draw(pauseWindow, windowPositionX, windowPositionY, pauseWindow.getWidth(), pauseWindow.getHeight());
        MyGame.batch.draw(resumeText, textPositionX, textPositionY, resumeText.getWidth(), resumeText.getHeight());
        MyGame.batch.end();

        Gdx.input.setInputProcessor(inputMultiplexer);

        pauseStage.act(Gdx.graphics.getDeltaTime());
        pauseStage.draw();
    }

    public void dispose()
    {
        pauseWindow.dispose();
        resumeText.dispose();
        resume = null;
        close = null;
    }
}
