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

import static com.patryk.main.Menu.MINOR_BUTTONS_HEIGHT;
import static com.patryk.main.Menu.MINOR_BUTTONS_WIDTH;

public class SelectLevelScreen implements Screen
{


    private MyGame myGame;
    private Stage myStage;
    private SelectLevel selectLevel;

    private ImageButton returnButton;
    private ImageButton nextPage;
    private ImageButton previousPage;
    private ImageButton musicButton;
    private ImageButton soundButton;

    private Texture background;
    private Texture panel;
    private Texture selectLevelText;

    private int page = 0;

    public SelectLevelScreen(MyGame myGame)
    {
        this.myGame = myGame;
    }

    public SelectLevelScreen(MyGame myGame, Texture background)
    {
        this.myGame = myGame;
        this.background = background;
    }

    @Override
    public void show()
    {
        myStage = new Stage(new ScreenViewport());

        returnButton = new ImageButton(
                new TextureRegionDrawable(new TextureRegion(MyGame.myAssets.getTexture(Assets.RETURN_BUTTON, MINOR_BUTTONS_WIDTH, MINOR_BUTTONS_HEIGHT))));
        nextPage = new ImageButton(
                new TextureRegionDrawable(new TextureRegion(MyGame.myAssets.getTexture(Assets.FORWARD_BUTTON, MINOR_BUTTONS_WIDTH, MINOR_BUTTONS_HEIGHT))));
        previousPage = new ImageButton(
                new TextureRegionDrawable(new TextureRegion(MyGame.myAssets.getTexture(Assets.BACKWARD_BUTTON, MINOR_BUTTONS_WIDTH, MINOR_BUTTONS_HEIGHT))));
        musicButton = new ImageButton(
                new TextureRegionDrawable(new TextureRegion(MyGame.myAssets.getTexture(Assets.MUSIC_TEXTURE_BUTTON, MINOR_BUTTONS_WIDTH, MINOR_BUTTONS_HEIGHT))));
        soundButton = new ImageButton(
                new TextureRegionDrawable(new TextureRegion(MyGame.myAssets.getTexture(Assets.SOUND_TEXTURE_BUTTON, MINOR_BUTTONS_WIDTH, MINOR_BUTTONS_HEIGHT))));

        selectLevelText = MyGame.myAssets.getTexture(Assets.SELECT_LEVEL, (int)(0.8f * Gdx.graphics.getWidth()), (int)(0.28f * Gdx.graphics.getHeight()));

        if(background == null)
        {
            background = MyGame.myAssets.getTexture(Assets.BACKGROUND_BLUE, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        }

        selectLevel = new SelectLevel(0);
        selectLevel.setLevelButtons();
        selectLevel.addActors(myStage);

        prepareButtonsListener();
        initializeButtons();

        myStage.addActor(returnButton);
        myStage.addActor(nextPage);
        myStage.addActor(previousPage);
        myStage.addActor(musicButton);
        myStage.addActor(soundButton);
        Gdx.input.setInputProcessor(myStage);
    }

    private void prepareButtonsListener()
    {
        for(LevelButton lButton: selectLevel.getLevelButtons())
        {
            final int levelNum = lButton.getLevelNumber();

            lButton.getImageButton().addListener(new ClickListener()
            {
                @Override
                public void clicked(InputEvent event, float x, float y)
                {
                    playButtonSound();
                    dispose();
                    myGame.setScreen(new MainGame(myGame, levelNum));
                }
            });
        }

        returnButton.addListener(new ClickListener()
        {
            @Override
            public void clicked(InputEvent event, float x, float y)
            {
                playButtonSound();
                dispose();
                myGame.setScreen(new Menu(myGame, background));
            }
        });

        nextPage.addListener(new ClickListener()
        {
            @Override
            public void clicked(InputEvent event, float x, float y)
            {
                playButtonSound();
                selectLevel.nextPage();
            }
        });

        previousPage.addListener(new ClickListener()
        {
            @Override
            public void clicked(InputEvent event, float x, float y)
            {
                playButtonSound();
                selectLevel.previousPage();
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
    }

    private void playButtonSound()
    {
        if(MyGame.options.isSound())
        {
            MyGame.myAssets.getSound(Assets.SOUND_OF_BUTTON).play(MyGame.options.getSoundVolume());
        }
    }

    private void initializeButtons()
    {
        returnButton.setSize(MINOR_BUTTONS_WIDTH, MINOR_BUTTONS_HEIGHT);
        returnButton.setPosition(0.85f * Gdx.graphics.getWidth(), 0.01f * Gdx.graphics.getHeight());

        nextPage.setSize(MINOR_BUTTONS_WIDTH, MINOR_BUTTONS_HEIGHT);
        nextPage.setPosition(Gdx.graphics.getWidth() * 0.6f,Gdx.graphics.getHeight() * 0.15f);

        previousPage.setSize(MINOR_BUTTONS_WIDTH, MINOR_BUTTONS_HEIGHT);
        previousPage.setPosition(Gdx.graphics.getWidth() * 0.3f,Gdx.graphics.getHeight() * 0.15f);

        musicButton.setSize(MINOR_BUTTONS_WIDTH, MINOR_BUTTONS_HEIGHT);
        musicButton.setPosition(0.05f * Gdx.graphics.getWidth(), 0.01f * Gdx.graphics.getHeight());

        soundButton.setSize(MINOR_BUTTONS_WIDTH, MINOR_BUTTONS_HEIGHT);
        soundButton.setPosition(0.2f * Gdx.graphics.getWidth(), 0.01f * Gdx.graphics.getHeight());

        panel = MyGame.myAssets.getTexture(Assets.PANEL,(int)(0.85 * Gdx.graphics.getWidth()), (int)(0.52f * Gdx.graphics.getHeight()));
    }

    @Override
    public void render(float delta)
    {
        Gdx.gl.glClearColor(0,0,1,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        MyGame.batch.enableBlending();
        MyGame.batch.begin();
        MyGame.batch.draw(background, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        MyGame.batch.draw(panel, Gdx.graphics.getWidth()/2 - panel.getWidth()/2, 0.26f * Gdx.graphics.getHeight());
        MyGame.batch.draw(selectLevelText, Gdx.graphics.getWidth()/2 - selectLevelText.getWidth()/2, 0.75f * Gdx.graphics.getHeight(), selectLevelText.getWidth(), selectLevelText.getHeight());
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
        panel.dispose();
    }
}
