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
    private ImageButton returnButton = new ImageButton(
                new TextureRegionDrawable(new TextureRegion(MyGame.myAssets.getTexture(Assets.RETURN_BUTTON, MINOR_BUTTONS_WIDTH, MINOR_BUTTONS_HEIGHT))));
    private ImageButton menuButton = new ImageButton(
                new TextureRegionDrawable(new TextureRegion(MyGame.myAssets.getTexture(Assets.MENU_BUTTON, MINOR_BUTTONS_WIDTH, MINOR_BUTTONS_HEIGHT))));

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

        gameLogic();

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
        returnButton = null;
    }
}