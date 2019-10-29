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
    private static final int MAIN_BUTTONS_WIDTH = (int)(0.45f * Gdx.graphics.getWidth());
    private static final int MAIN_BUTTONS_HEIGHT = (int)(0.07f * Gdx.graphics.getHeight());
    private static final int MINOR_BUTTONS_WIDTH = (int)(0.1f * Gdx.graphics.getWidth());
    private static final int MINOR_BUTTONS_HEIGHT = (int)(0.1f * Gdx.graphics.getHeight());

    private MyGame myGame;

    private ImageButton startButton;
    private ImageButton exitButton;
    private ImageButton optionsButton;
    private ImageButton infoButton;

    private Texture background;
    private Texture logo;

    private Stage myStage;

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
        myStage = new Stage(new ScreenViewport());

        startButton = new ImageButton(
                new TextureRegionDrawable(new TextureRegion(MyGame.myAssets.getTexture(Assets.START_BUTTON, MAIN_BUTTONS_WIDTH, MAIN_BUTTONS_HEIGHT))));
        exitButton = new ImageButton(
                new TextureRegionDrawable(new TextureRegion(MyGame.myAssets.getTexture(Assets.EXIT_BUTTON, MAIN_BUTTONS_WIDTH, MAIN_BUTTONS_HEIGHT))));
        optionsButton = new ImageButton(
                new TextureRegionDrawable(new TextureRegion(MyGame.myAssets.getTexture(Assets.OPTIONS_BUTTON))));
        infoButton = new ImageButton(
                new TextureRegionDrawable(new TextureRegion(MyGame.myAssets.getTexture(Assets.INFO_BUTTON))));

        logo = new Texture(Assets.LOGO2);

        if(background == null)
        {
            background = MyGame.myAssets.getTexture(Assets.BACKGROUND_BLUE, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        }

        initializeButtons();
        prepareButtonsListener();

        myStage.addActor(startButton);
        myStage.addActor(optionsButton);
        myStage.addActor(exitButton);
        myStage.addActor(infoButton);
        Gdx.input.setInputProcessor(myStage);
    }

    private void initializeButtons()
    {
        startButton.setSize(MAIN_BUTTONS_WIDTH, MAIN_BUTTONS_HEIGHT);
        exitButton.setSize(MAIN_BUTTONS_WIDTH, MAIN_BUTTONS_HEIGHT);
        optionsButton.setSize(MINOR_BUTTONS_WIDTH, MINOR_BUTTONS_HEIGHT);
        infoButton.setSize(MINOR_BUTTONS_WIDTH, MINOR_BUTTONS_HEIGHT);

        startButton.setPosition(Gdx.graphics.getWidth()/2 - startButton.getWidth()/2, Gdx.graphics.getHeight()/2);
        exitButton.setPosition(Gdx.graphics.getWidth()/2 - exitButton.getWidth()/2,Gdx.graphics.getHeight()/2f - 1.3f * exitButton.getHeight());
        optionsButton.setPosition(0.05f * Gdx.graphics.getWidth(), 0.01f * Gdx.graphics.getHeight());
        infoButton.setPosition(0.1f * Gdx.graphics.getWidth() + infoButton.getWidth(), 0.01f * Gdx.graphics.getHeight());
    }

    private void prepareButtonsListener()
    {
        startButton.addListener(new ClickListener()
        {
            @Override
            public void clicked(InputEvent event, float x, float y)
            {
                dispose();
                myGame.setScreen(new SelectLevelScreen(myGame, background));
            }
        });

        exitButton.addListener(new ClickListener()
        {
            @Override
            public void clicked(InputEvent event, float x, float y)
            {
                dispose();
                System.exit(0);
            }
        });

        optionsButton.addListener(new ClickListener()
        {
            @Override
            public void clicked(InputEvent event, float x, float y)
            {
                dispose();
                myGame.setScreen(new OptionsScreen(myGame, background));
            }
        });
    }

    @Override
    public void render(float delta)
    {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        startButton.getClickListener();
        optionsButton.getClickListener();
        exitButton.getClickListener();

        MyGame.batch.enableBlending();
        MyGame.batch.begin();
        MyGame.batch.draw(background, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        MyGame.batch.draw(logo, 0.85f * Gdx.graphics.getWidth(), 0.01f * Gdx.graphics.getHeight(),0.12f * Gdx.graphics.getWidth(), 0.095f * Gdx.graphics.getHeight());
        MyGame.batch.end();

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
        logo.dispose();

        startButton = null;
        optionsButton = null;
    }
}
