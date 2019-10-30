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

public class MainGame implements Screen
{
    private final static int TOUCH_COOLDOWN = 250;
    private static final int MINOR_BUTTONS_WIDTH = (int)(0.1f * Gdx.graphics.getWidth());
    private static final int MINOR_BUTTONS_HEIGHT = (int)(0.1f * Gdx.graphics.getHeight());

    private enum GameState {RUNNING, PAUSE, FINISHED}

    private final MyGame myGame;
    private Stage myStage;

    private ImageButton returnButton;
    private ImageButton pauseButton;
    private ImageButton menuButton;
    private Texture background;
    private Texture screenDarkening;

    private Lattice squareLattice;
    private ConnectionChecker connectionChecker;
    private GameState gameState = GameState.RUNNING;

    private long lastTouch = 0;
    private int levelNumber;
    private float stateTime = 0f;

    public MainGame (MyGame myGame, int levelNumber)
    {
        this.myGame = myGame;
        this.levelNumber = levelNumber;
    }

    @Override
    public void show()
    {
        myStage = new Stage(new ScreenViewport());

        pauseButton = new ImageButton(
                new TextureRegionDrawable(new TextureRegion(MyGame.myAssets.getTexture(Assets.PAUSE_BUTTON))));
        returnButton = new ImageButton(
                new TextureRegionDrawable(new TextureRegion(MyGame.myAssets.getTexture(Assets.RETURN_BUTTON))));
        menuButton = new ImageButton(
                new TextureRegionDrawable(new TextureRegion(MyGame.myAssets.getTexture(Assets.MENU_BUTTON))));

        screenDarkening = MyGame.myAssets.getTexture(Assets.SCREEN_DARKENING);
        background = MyGame.myAssets.getTexture(Assets.BACKGROUND_AQUA, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        squareLattice = new Lattice();
        squareLattice.setLattice(1);

        connectionChecker = new ConnectionChecker(squareLattice.getSquareTiles(), squareLattice.getStartingTile(), squareLattice.getLaserPositions());

        initializeButtons();
        prepareButtonsListener();

        squareLattice.addActors(myStage);
        myStage.addActor(returnButton);
        myStage.addActor(pauseButton);
        myStage.addActor(menuButton);
        Gdx.input.setInputProcessor(myStage);

        connectionChecker.assambleConnections();
    }

    private void initializeButtons()
    {
        returnButton.setPosition(50,50);
        returnButton.setSize(MINOR_BUTTONS_WIDTH, MINOR_BUTTONS_HEIGHT);

        pauseButton.setPosition(200,50);
        pauseButton.setSize(MINOR_BUTTONS_WIDTH, MINOR_BUTTONS_HEIGHT);

        menuButton.setPosition(350,50);
        menuButton.setSize(MINOR_BUTTONS_WIDTH, MINOR_BUTTONS_HEIGHT);
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

        myStage.act(Gdx.graphics.getDeltaTime());
        myStage.draw();

        if(gameState == GameState.PAUSE)
        {
            drawMenu();
        }
    }

    private void prepareButtonsListener()
    {
        returnButton.addListener(new ClickListener()
        {
            @Override
            public void clicked(InputEvent event, float x, float y)
            {
                dispose();
                myGame.setScreen(new Menu(myGame));
            }
        });

        pauseButton.addListener(new ClickListener()
        {
            @Override
            public void clicked(InputEvent event, float x, float y)
            {
                gameState = GameState.PAUSE;
            }
        });

        menuButton.addListener(new ClickListener()
        {
            @Override
            public void clicked(InputEvent event, float x, float y)
            {
                gameState = GameState.RUNNING;
            }
        });
    }

    private void drawMenu()
    {
        Texture pauseWindow = MyGame.myAssets.getTexture(Assets.PAUSE_WINDOW, (int)(0.7 * Gdx.graphics.getWidth()), (int)(0.3 * Gdx.graphics.getHeight()));
        float windowPositionX = Gdx.graphics.getWidth()/2 - pauseWindow.getWidth()/2;
        float windowPositionY = Gdx.graphics.getHeight()/2 - pauseWindow.getHeight()/2;


        MyGame.batch.begin();
        MyGame.batch.draw(screenDarkening, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        MyGame.batch.draw(pauseWindow, windowPositionX, windowPositionY, pauseWindow.getWidth(), pauseWindow.getHeight());
        MyGame.batch.end();
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
        returnButton = null;
    }
}