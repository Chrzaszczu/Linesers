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

public class YouWinWindow
{
    private static final int TEXT_WIDTH = (int)(0.9f * Gdx.graphics.getWidth());
    private static final int TEXT_HEIGHT = (int)(0.3f * Gdx.graphics.getWidth());

    private static ImageButton nextLevel = new ImageButton(
            new TextureRegionDrawable(new TextureRegion(MyGame.myAssets.getTexture(Assets.NEXT_GAME_BUTTON, MINOR_BUTTONS_WIDTH, MINOR_BUTTONS_HEIGHT))));
    private static ImageButton close = new ImageButton(
            new TextureRegionDrawable(new TextureRegion(MyGame.myAssets.getTexture(Assets.CLOSE_BUTTON, MINOR_BUTTONS_WIDTH, MINOR_BUTTONS_HEIGHT))));

    private Texture screenDarkening = MyGame.myAssets.getTexture(Assets.SCREEN_DARKENING, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
    private Texture youWinText = MyGame.myAssets.getTexture(Assets.YOU_WIN_TEXT, TEXT_WIDTH, TEXT_HEIGHT);

    private float textPositionX;
    private float textPositionY;
    private float nextLevelX;
    private float nextLevelY;
    private float closeX;
    private float closeY;

    private Stage youWinStage = new Stage(new ScreenViewport());
    private InputMultiplexer inputMultiplexer = new InputMultiplexer();
    private Stage myStage;
    private MainGame mainGame;


    public YouWinWindow(MainGame mainGame, Stage myStage)
    {
        this.myStage = myStage;
        this.mainGame = mainGame;
    }

    public void initializeWindow()
    {
        textPositionX = Gdx.graphics.getWidth()/2 - youWinText.getWidth()/2;
        textPositionY = Gdx.graphics.getHeight()/2 + youWinText.getHeight();
        nextLevelX = 0.7f * Gdx.graphics.getWidth();
        nextLevelY = 0.4f * Gdx.graphics.getHeight();
        closeX = 0.2f * Gdx.graphics.getWidth();
        closeY = 0.4f * Gdx.graphics.getHeight();

        youWinStage.addActor(nextLevel);
        youWinStage.addActor(close);

        nextLevel.setTransform(true);
        nextLevel.setOrigin(nextLevel.getWidth()/2, nextLevel.getHeight()/2);
        nextLevel.setRotation(-90);

        inputMultiplexer.addProcessor(youWinStage);
        inputMultiplexer.addProcessor(myStage);

        nextLevel.setPosition(nextLevelX, nextLevelY);
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

        nextLevel.addListener(new ClickListener()
        {
            @Override
            public void clicked(InputEvent event, float x, float y)
            {
                MyGame.myAssets.playSound(Assets.SOUND_OF_BUTTON);
                mainGame.setGameState(GameState.CHANGE_LEVEL);
            }
        });
    }

    public void draw()
    {
        MyGame.batch.begin();
        MyGame.batch.draw(screenDarkening, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        MyGame.batch.draw(youWinText, textPositionX, textPositionY, youWinText.getWidth(), youWinText.getHeight());
        MyGame.batch.end();

        Gdx.input.setInputProcessor(inputMultiplexer);

        youWinStage.act(Gdx.graphics.getDeltaTime());
        youWinStage.draw();
    }
}
