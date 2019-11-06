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

enum GameState {RUNNING, PAUSE, FINISHED, QUIT, CHANGE_LEVEL}

public class MainGame implements Screen
{
    private final static int TOUCH_COOLDOWN = 250;

    private final MyGame myGame;
    private Stage myStage = new Stage(new ScreenViewport());

    private ImageButton pauseButton = new ImageButton(
                new TextureRegionDrawable(new TextureRegion(MyGame.myAssets.getTexture(Assets.PAUSE_BUTTON, MINOR_BUTTONS_WIDTH, MINOR_BUTTONS_HEIGHT))));
    private ImageButton musicButton = new ImageButton(
            new TextureRegionDrawable(new TextureRegion(MyGame.myAssets.getTexture(Assets.MUSIC_TEXTURE_BUTTON, MINOR_BUTTONS_WIDTH, MINOR_BUTTONS_HEIGHT))));
    private ImageButton soundButton = new ImageButton(
            new TextureRegionDrawable(new TextureRegion(MyGame.myAssets.getTexture(Assets.SOUND_TEXTURE_BUTTON, MINOR_BUTTONS_WIDTH, MINOR_BUTTONS_HEIGHT))));

    private Texture background = MyGame.myAssets.getTexture(Assets.BACKGROUND_AQUA, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

    private Lattice squareLattice = new Lattice();
    private GameState gameState = GameState.RUNNING;
    private ConnectionChecker connectionChecker;
    private PauseWindow pauseWindow;
    private YouWinWindow youWinWindow;

    private long lastTouch = 0;
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
        squareLattice.setLattice(1);

        pauseWindow = new PauseWindow(this, myStage);
        pauseWindow.initializeWindow();

        youWinWindow = new YouWinWindow(this, myStage);
        youWinWindow.initializeWindow();

        connectionChecker = new ConnectionChecker(squareLattice.getSquareTiles(), squareLattice.getStartingTile(), squareLattice.getLaserPositions());

        initializeButtons();
        prepareButtonsListener();

        squareLattice.addActors(myStage);

        myStage.addActor(pauseButton);
        myStage.addActor(musicButton);
        myStage.addActor(soundButton);

        Gdx.input.setInputProcessor(myStage);

        connectionChecker.assambleConnections();
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

    @Override
    public void render(float delta)
    {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stateTime += Gdx.graphics.getDeltaTime();

        MyGame.batch.enableBlending();
        MyGame.batch.begin();
        MyGame.batch.draw(background, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        MyGame.batch.end();

        gameLogic();

        Gdx.input.setInputProcessor(myStage);
        myStage.act(Gdx.graphics.getDeltaTime());
        myStage.draw();

        onGameStateChange();
    }

    private void gameLogic()
    {
        if(gameState == GameState.RUNNING)
        {
            if (Gdx.input.isTouched())
            {
                if (System.currentTimeMillis() - lastTouch > TOUCH_COOLDOWN)
                {
                    lastTouch = System.currentTimeMillis();

                    squareLattice.onLatticeTouch();
                    connectionChecker.assambleConnections();
                    gameState = (squareLattice.isFinished()) ? GameState.FINISHED : GameState.RUNNING;
                }
            }

            squareLattice.drawLasers(stateTime);
        }
        else
        {
            squareLattice.drawLasers(0f);
        }
    }

    private void onGameStateChange()
    {
        switch(gameState)
        {
            case PAUSE:
                pauseWindow.draw();
                break;

            case FINISHED:
                youWinWindow.draw();
                break;

            case QUIT:
                dispose();
                myGame.setScreen(new Menu(myGame));
                break;

            case CHANGE_LEVEL:
                dispose();
                myGame.setScreen(new MainGame(myGame, levelNumber++));
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
        myGame.dispose();
        myStage.dispose();

        squareLattice = null;
    }
}