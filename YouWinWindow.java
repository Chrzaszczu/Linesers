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
    private final int TEXT_WIDTH = Gdx.graphics.getWidth();
    private final int TEXT_HEIGHT = (int)(0.13f * Gdx.graphics.getHeight());

    private ImageButton nextLevel = new ImageButton(
            new TextureRegionDrawable(new TextureRegion(MyGame.myAssets.getTexture(Assets.NEXT_GAME_BUTTON, Gdx.graphics.getWidth()/2, (int)(0.06f * Gdx.graphics.getHeight())))));
    private ImageButton menu = new ImageButton(
            new TextureRegionDrawable(new TextureRegion(MyGame.myAssets.getTexture(Assets.MENU_BUTTON, Gdx.graphics.getWidth()/2, (int)(0.06f * Gdx.graphics.getHeight())))));

    private Texture youWinText = MyGame.myAssets.getTexture(Assets.YOU_WIN_TEXT, TEXT_WIDTH, TEXT_HEIGHT);

    private float textPositionX = Gdx.graphics.getWidth()/2 - youWinText.getWidth()/2;
    private float textPositionY = (int)(0.75f * Gdx.graphics.getHeight());
    private float nextLevelX = 0.5f * Gdx.graphics.getWidth();
    private float nextLevelY = 0.15f * Gdx.graphics.getHeight();
    private float menuX = 0;
    private float menuY = 0.15f * Gdx.graphics.getHeight();

    private Stage youWinStage = new Stage(new ScreenViewport());
    private Stage myStage;
    private MainGame mainGame;

    public YouWinWindow(MainGame mainGame, Stage myStage)
    {
        this.myStage = myStage;
        this.mainGame = mainGame;
    }

    public void initializeWindow()
    {
        youWinStage.addActor(nextLevel);
        youWinStage.addActor(menu);

        nextLevel.setPosition(nextLevelX, nextLevelY);
        menu.setPosition(menuX, menuY);

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
                mainGame.setGameState(GameState.QUIT);
                Gdx.input.setInputProcessor(myStage);
            }
        });

        nextLevel.addListener(new ClickListener()
        {
            @Override
            public void clicked(InputEvent event, float x, float y)
            {
                MyGame.myAssets.playSound(Assets.SOUND_OF_BUTTON);
                mainGame.setGameState(GameState.CHANGE_LEVEL);
                Gdx.input.setInputProcessor(myStage);
            }
        });
    }

    public void draw()
    {
        MyGame.batch.begin();
        MyGame.batch.draw(youWinText, textPositionX, textPositionY, youWinText.getWidth(), youWinText.getHeight());
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
