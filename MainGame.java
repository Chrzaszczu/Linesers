package com.mygdx.linesers;

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

import static com.mygdx.linesers.Menu.MINOR_BUTTONS_HEIGHT;
import static com.mygdx.linesers.Menu.MINOR_BUTTONS_WIDTH;

enum GameState {RUNNING, PAUSE, FINISHED, QUIT, CHANGE_LEVEL}

public class MainGame implements Screen
{
    private final MyGame myGame;
    private Stage myStage = new Stage(new ScreenViewport());

    private ImageButton pauseButton = new ImageButton(
            new TextureRegionDrawable(new TextureRegion(MyGame.myAssets.getTexture(Assets.PAUSE_BUTTON, MINOR_BUTTONS_WIDTH, MINOR_BUTTONS_HEIGHT))));
    private ImageButton musicButton = new ImageButton(
            new TextureRegionDrawable(new TextureRegion(MyGame.myAssets.getTexture(Assets.MUSIC_BUTTON, MINOR_BUTTONS_WIDTH, MINOR_BUTTONS_HEIGHT))));
    private ImageButton soundButton = new ImageButton(
            new TextureRegionDrawable(new TextureRegion(MyGame.myAssets.getTexture(Assets.SOUND_BUTTON, MINOR_BUTTONS_WIDTH, MINOR_BUTTONS_HEIGHT))));

    private Texture background = MyGame.myAssets.getTexture(Assets.BACKGROUND, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

    private Lattice squareLattice = new Lattice();
    private GameState gameState = GameState.RUNNING;
    private PauseWindow pauseWindow;
    private YouWinWindow youWinWindow;

    private float stateTime = 0f;
    private int levelNumber;

    public MainGame (MyGame myGame, int levelNumber)
    {
        this.myGame = myGame;
        this.levelNumber = levelNumber;
    }

    @Override
    public void show()
    {
        squareLattice.setLattice(levelNumber);

        pauseWindow = new PauseWindow(this, myStage);
        pauseWindow.initializeWindow();

        youWinWindow = new YouWinWindow(this, myStage);
        youWinWindow.initializeWindow();

        initializeButtons();
        prepareButtonsListener();

        squareLattice.addActors(myStage);

        myStage.addActor(pauseButton);
        myStage.addActor(musicButton);
        myStage.addActor(soundButton);

        Gdx.input.setInputProcessor(myStage);
    }

    private void initializeButtons()
    {
        pauseButton.setSize(MINOR_BUTTONS_WIDTH, MINOR_BUTTONS_HEIGHT);
        pauseButton.setPosition(0.85f * Gdx.graphics.getWidth(), 0.01f * Gdx.graphics.getHeight());

        musicButton.setSize(MINOR_BUTTONS_WIDTH, MINOR_BUTTONS_HEIGHT);
        musicButton.setPosition(0.05f * Gdx.graphics.getWidth(), 0.01f * Gdx.graphics.getHeight());

        soundButton.setSize(MINOR_BUTTONS_WIDTH, MINOR_BUTTONS_HEIGHT);
        soundButton.setPosition(0.2f * Gdx.graphics.getWidth(), 0.01f * Gdx.graphics.getHeight());
    }

    private void prepareButtonsListener()
    {
        pauseButton.addListener(new ClickListener()
        {
            @Override
            public void clicked(InputEvent event, float x, float y)
            {
                MyGame.myAssets.playSound(Assets.SOUND_OF_BUTTON);
                gameState = GameState.PAUSE;
            }
        });

        musicButton.addListener(new ClickListener()
        {
            @Override
            public void clicked(InputEvent event, float x, float y)
            {
                if(MyGame.options.isMusic())
                {
                    MyGame.myAssets.playSound(Assets.SOUND_OF_BUTTON);
                    MyGame.options.setMusic(false);
                    MyGame.myAssets.getMusic(Assets.MUSIC).stop();
                }
                else
                {
                    MyGame.myAssets.playSound(Assets.SOUND_OF_BUTTON);
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
                    MyGame.myAssets.playSound(Assets.SOUND_OF_BUTTON);
                }
            }
        });
    }

    @Override
    public void render(float delta)
    {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stateTime += Gdx.graphics.getDeltaTime();

        MyGame.batch.enableBlending();
        MyGame.batch.begin();
        MyGame.batch.draw(background, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        MyGame.batch.end();

        if(gameState == GameState.RUNNING)
        {
            squareLattice.drawLasers(stateTime);

            if(squareLattice.isFinished())
            {
                setGameState(GameState.FINISHED);
            }
        }
        else
        {
            squareLattice.drawLasers(0f);
        }

        myStage.act(Gdx.graphics.getDeltaTime());
        myStage.draw();

        gameLogic();
    }

    private void gameLogic()
    {
        switch(gameState)
        {
            case PAUSE:
                pauseWindow.draw();
                break;

            case FINISHED:
                MyGame.options.addFinishedLevel(levelNumber);
                youWinWindow.draw();
                break;

            case CHANGE_LEVEL:
                dispose();
                if(levelNumber + 1 >= MyGame.myAssets.numberOfMaps())
                {
                    myGame.setScreen(new Menu(myGame));
                }
                else
                {
                    myGame.setScreen(new MainGame(myGame, ++levelNumber));
                }
                break;

            case QUIT:
                dispose();
                myGame.setScreen(new Menu(myGame));
                break;
        }
    }

    protected void setGameState(GameState gameState)
    {
        this.gameState = gameState;
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
        pauseWindow.dispose();
        youWinWindow.dispose();
        myStage.dispose();
        background.dispose();

        squareLattice = null;
        MyGame.myAssets.disposeTextures();
    }
}