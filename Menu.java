package com.patryk.main;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class Menu implements Screen
{
    public static final int MAIN_BUTTONS_WIDTH = (int)(0.55f * Gdx.graphics.getWidth());
    public static final int MAIN_BUTTONS_HEIGHT = (int)(0.1f * Gdx.graphics.getHeight());
    public static final int MINOR_BUTTONS_WIDTH = (int)(0.12f * Gdx.graphics.getWidth());
    public static final int MINOR_BUTTONS_HEIGHT = (int)(0.07f * Gdx.graphics.getHeight());

    private static final int LOGO_WIDTH = (int)(0.16f * Gdx.graphics.getWidth());
    private static final int LOGO_HEIGHT = (int)(0.15f * Gdx.graphics.getHeight());

    private MyGame myGame;
    private Stage myStage = new Stage(new ScreenViewport());

    private ImageButton startButton = new ImageButton(
            new TextureRegionDrawable(new TextureRegion(MyGame.myAssets.getTexture(Assets.START_BUTTON, MAIN_BUTTONS_WIDTH, MAIN_BUTTONS_HEIGHT))));
    private ImageButton exitButton = new ImageButton(
                new TextureRegionDrawable(new TextureRegion(MyGame.myAssets.getTexture(Assets.EXIT_BUTTON, MAIN_BUTTONS_WIDTH, MAIN_BUTTONS_HEIGHT))));
    private ImageButton musicButton = new ImageButton(
                new TextureRegionDrawable(new TextureRegion(MyGame.myAssets.getTexture(Assets.MUSIC_BUTTON, MINOR_BUTTONS_WIDTH, MINOR_BUTTONS_HEIGHT))));
    private ImageButton soundButton = new ImageButton(
                new TextureRegionDrawable(new TextureRegion(MyGame.myAssets.getTexture(Assets.SOUND_BUTTON, MINOR_BUTTONS_WIDTH, MINOR_BUTTONS_HEIGHT))));
    private ImageButton logo = new ImageButton(
                new TextureRegionDrawable(new TextureRegion(MyGame.myAssets.getTexture(Assets.LOGO2, LOGO_WIDTH, LOGO_HEIGHT))));

    private Texture title = MyGame.myAssets.getTexture(Assets.TITLE, (int)(0.88f * Gdx.graphics.getWidth()), (int)(0.22f * Gdx.graphics.getHeight()));
    private Texture background;

    public Menu(MyGame myGame)
    {
        this.myGame = myGame;
    }

    public Menu(MyGame myGame, Texture background)
    {
        this.myGame = myGame;
        this.background = background;
    }

    @Override
    public void show()
    {
        if(background == null)
        {
            background = MyGame.myAssets.getTexture(Assets.BACKGROUND, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        }

        initializeButtons();
        prepareButtonsListener();

        myStage.addActor(startButton);
        myStage.addActor(soundButton);
        myStage.addActor(musicButton);
        myStage.addActor(exitButton);
        myStage.addActor(logo);
        Gdx.input.setInputProcessor(myStage);
    }

    private void initializeButtons()
    {
        startButton.setSize(MAIN_BUTTONS_WIDTH, MAIN_BUTTONS_HEIGHT);
        startButton.setPosition(Gdx.graphics.getWidth()/2 - startButton.getWidth()/2, Gdx.graphics.getHeight()/2);

        exitButton.setSize(MAIN_BUTTONS_WIDTH, MAIN_BUTTONS_HEIGHT);
        exitButton.setPosition(Gdx.graphics.getWidth()/2 - exitButton.getWidth()/2,Gdx.graphics.getHeight()/2f - 1.5f * exitButton.getHeight());

        musicButton.setSize(MINOR_BUTTONS_WIDTH, MINOR_BUTTONS_HEIGHT);
        musicButton.setPosition(0.05f * Gdx.graphics.getWidth(), 0.01f * Gdx.graphics.getHeight());

        soundButton.setSize(MINOR_BUTTONS_WIDTH, MINOR_BUTTONS_HEIGHT);
        soundButton.setPosition(0.2f * Gdx.graphics.getWidth(), 0.01f * Gdx.graphics.getHeight());

        logo.setSize(LOGO_WIDTH, LOGO_HEIGHT);
        logo.setPosition(0.83f * Gdx.graphics.getWidth(), 0.01f * Gdx.graphics.getHeight());
    }

    private void prepareButtonsListener()
    {
        startButton.addListener(new ClickListener()
        {
            @Override
            public void clicked(InputEvent event, float x, float y)
            {
                playButtonSound();
                dispose();
                myGame.setScreen(new SelectLevelScreen(myGame, background));
            }
        });

        exitButton.addListener(new ClickListener()
        {
            @Override
            public void clicked(InputEvent event, float x, float y)
            {
                playButtonSound();
                dispose();
                System.exit(0);
            }
        });

        musicButton.addListener(new ClickListener()
        {
            @Override
            public void clicked(InputEvent event, float x, float y)
            {
                if(MyGame.options.isMusic())
                {
                    playButtonSound();
                    MyGame.options.setMusic(false);
                    MyGame.myAssets.getMusic(Assets.MUSIC).stop();
                }
                else
                {
                    playButtonSound();
                    MyGame.options.setMusic(true);
                    MyGame.myAssets.getMusic(Assets.MUSIC).play();
                }
            }
        });

        soundButton.addListener(new ClickListener()
        {
            @Override
            public void clicked(InputEvent event, float x, float y)
            {
                if(MyGame.options.isSound())
                {
                    MyGame.options.setSound(false);
                }
                else
                {
                    MyGame.options.setSound(true);
                    playButtonSound();
                }
            }
        });

       /* logo.addListener(new ClickListener()
        {
            @Override
            public void clicked(InputEvent event, float x, float y)
            {
                //playButtonSound();
            }
        });*/
    }

    private void playButtonSound()
    {
        if(MyGame.options.isSound())
        {
            MyGame.myAssets.getSound(Assets.SOUND_OF_BUTTON).play(MyGame.options.getSoundVolume());
        }
    }

    @Override
    public void render(float delta)
    {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        MyGame.batch.enableBlending();
        MyGame.batch.begin();
        MyGame.batch.draw(background, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        MyGame.batch.draw(title, Gdx.graphics.getWidth()/2 - title.getWidth()/2, 0.7f * Gdx.graphics.getHeight(), title.getWidth(), title.getHeight());
        MyGame.batch.end();

        myStage.act(Gdx.graphics.getDeltaTime());
        myStage.draw();
    }

    @Override
    public void resize(int width, int height)
    {

    }

    @Override
    public void pause()
    {

    }

    @Override
    public void resume()
    {

    }

    @Override
    public void hide()
    {

    }

    @Override
    public void dispose()
    {
        myGame.dispose();
        myStage.dispose();
    }
}
