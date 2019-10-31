package com.patryk.main;

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

import static com.patryk.main.Menu.MINOR_BUTTONS_HEIGHT;
import static com.patryk.main.Menu.MINOR_BUTTONS_WIDTH;

public class PauseWindow
{
    private static final int WINDOW_WIDTH = (int)(0.7 * Gdx.graphics.getWidth());
    private static final int WINDOW_HEIGHT = (int)(0.35 * Gdx.graphics.getWidth());
    private static final int TEXT_WIDTH = (int)(0.4 * Gdx.graphics.getWidth());
    private static final int TEXT_HEIGHT = (int)(0.1 * Gdx.graphics.getWidth());

    private static ImageButton resumeButton = new ImageButton(
            new TextureRegionDrawable(new TextureRegion(MyGame.myAssets.getTexture(Assets.OK_BUTTON, MINOR_BUTTONS_WIDTH, MINOR_BUTTONS_HEIGHT))));
    private static ImageButton closeButton = new ImageButton(
            new TextureRegionDrawable(new TextureRegion(MyGame.myAssets.getTexture(Assets.CLOSE_BUTTON, MINOR_BUTTONS_WIDTH, MINOR_BUTTONS_HEIGHT))));

    private Texture pauseWindow = MyGame.myAssets.getTexture(Assets.PAUSE_WINDOW, WINDOW_WIDTH, WINDOW_HEIGHT);
    private Texture screenDarkening = MyGame.myAssets.getTexture(Assets.SCREEN_DARKENING, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
    private Texture resumeText = MyGame.myAssets.getTexture(Assets.RESUME, TEXT_WIDTH, TEXT_HEIGHT);

    private float windowPositionX;
    private float windowPositionY;
    private float textPositionX;
    private float textPositionY;
    private float resumeButtonX;
    private float resumeButtonY;
    private float closeButtonX;
    private float closeButtonY;

    private Stage myStage;
    private Stage pauseStage;
    private MainGame mainGame;
    private MyGame myGame;
    private InputMultiplexer inputMultiplexer = new InputMultiplexer();

    public PauseWindow(MyGame myGame, MainGame mainGame, Stage myStage)
    {
        this.myStage = myStage;
        this.mainGame = mainGame;
        this.myGame = myGame;
    }

    public void initializeWindow()
    {
        windowPositionX = Gdx.graphics.getWidth()/2 - pauseWindow.getWidth()/2;
        windowPositionY = Gdx.graphics.getHeight()/2 - pauseWindow.getHeight()/2;
        textPositionX = Gdx.graphics.getWidth()/2 - resumeText.getWidth()/2;
        textPositionY = Gdx.graphics.getHeight()/2 - resumeText.getHeight()/2;
        resumeButtonX = 0.7f * Gdx.graphics.getWidth();
        resumeButtonY = 0.42f * Gdx.graphics.getHeight();
        closeButtonX = 0.2f * Gdx.graphics.getWidth();
        closeButtonY = 0.42f * Gdx.graphics.getHeight();

        pauseStage = new Stage(new ScreenViewport());

        pauseStage.addActor(resumeButton);
        pauseStage.addActor(closeButton);

        inputMultiplexer.addProcessor(pauseStage);
        inputMultiplexer.addProcessor(myStage);

        resumeButton.setPosition(resumeButtonX, resumeButtonY);
        closeButton.setPosition(closeButtonX, closeButtonY);
    }

    private void prepareButtonsListener()
    {
        closeButton.addListener(new ClickListener()
        {
            @Override
            public void clicked(InputEvent event, float x, float y)
            {
                mainGame.setGameState(GameState.QUIT);
            }
        });

        resumeButton.addListener(new ClickListener()
        {
            @Override
            public void clicked(InputEvent event, float x, float y)
            {
                mainGame.setGameState(GameState.RUNNING);
            }
        });
    }

    public void draw()
    {
        MyGame.batch.begin();
        MyGame.batch.draw(screenDarkening, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        MyGame.batch.draw(pauseWindow, windowPositionX, windowPositionY, pauseWindow.getWidth(), pauseWindow.getHeight());
        MyGame.batch.draw(resumeText, textPositionX, textPositionY, resumeText.getWidth(), resumeText.getHeight());
        MyGame.batch.end();

        prepareButtonsListener();

        Gdx.input.setInputProcessor(inputMultiplexer);

        pauseStage.act(Gdx.graphics.getDeltaTime());
        pauseStage.draw();
    }
}
