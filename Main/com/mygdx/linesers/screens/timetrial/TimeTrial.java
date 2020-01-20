package Main.com.mygdx.linesers.screens.timetrial;

import Main.com.mygdx.linesers.MyGame;
import Main.com.mygdx.linesers.assets.Assets;
import Main.com.mygdx.linesers.levels.timetrial.ScoreCounter;
import Main.com.mygdx.linesers.screens.GameScreen;
import Main.com.mygdx.linesers.GameState;
import Main.com.mygdx.linesers.screens.Menu;
import Main.com.mygdx.linesers.tiles.SquareTile;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

import java.util.List;

public class TimeTrial extends GameScreen
{
    private static final int TIME_BAR_WIDTH = Gdx.graphics.getWidth();
    private static final int TIME_BAR_HEIGHT = (int)(0.04f * Gdx.graphics.getHeight());
    private static final int TEXT_WIDTH = Gdx.graphics.getWidth();
    private static final int TEXT_HEIGHT = (int)(0.1f * Gdx.graphics.getHeight());

    private static final int SHADE_WIDTH = Gdx.graphics.getWidth();
    private static final int SHADE_HEIGHT = (int)(0.2f * Gdx.graphics.getHeight());

    private static final int TIME_BAR_POSITION_X = 0;
    private static final int TIME_BAR_POSITION_Y = (int)(0.9f * Gdx.graphics.getHeight());

    private static final int TEXT_POSITION_X = 0;
    private static final int TIME_OUT_POSITION_Y = (int)(0.7f * Gdx.graphics.getHeight());
    private static final int HIGHSCORE_POSITION_Y = (int)(0.6f * Gdx.graphics.getHeight());

    private static final int SHADE_POSITION_X = 0;
    private static final int SHADE_POSITION_Y = (int)(0.6f * Gdx.graphics.getHeight());

    private static final int SCORE_FONT_POSITION_X = (int)(0.05f * Gdx.graphics.getWidth());
    private static final int SCORE_FONT_POSITION_Y = (int)(0.98f * Gdx.graphics.getHeight());
    private static final int TIME_FONT_POSITION_X = (int)(0.05f * Gdx.graphics.getWidth());
    private static final int TIME_FONT_POSITION_Y = (int)(0.93f * Gdx.graphics.getHeight());
    private static final int TARGET_WIDTH = (int)(0.06f * Gdx.graphics.getWidth());

    private static final int MENU_POSITION_X = 0;
    private static final int MENU_POSITION_Y = (int)(0.15f * Gdx.graphics.getHeight());
    private static final int RETRY_POSITION_X = (int)(0.5f * Gdx.graphics.getWidth());
    private static final int RETRY_POSITION_Y = (int)(0.15f * Gdx.graphics.getHeight());

    private Texture timeBarBorder = MyGame.myAssets.getTexture(Assets.TIME_BAR_BORDER, TIME_BAR_WIDTH, TIME_BAR_HEIGHT);
    private Texture timeBar = MyGame.myAssets.getTexture(Assets.TIME_BAR, TIME_BAR_WIDTH, TIME_BAR_HEIGHT);
    private Texture timeOut = MyGame.myAssets.getTexture(Assets.TIME_OUT, TEXT_WIDTH, TEXT_HEIGHT);
    private Texture highscore = MyGame.myAssets.getTexture(Assets.HIGHSCORE_TEXT, TEXT_WIDTH, TEXT_HEIGHT);
    private Texture shade = MyGame.myAssets.getTexture(Assets.SHADE, SHADE_WIDTH, SHADE_HEIGHT);

    private ScoreCounter scoreCounter = new ScoreCounter();

    private ImageButton menu = new ImageButton(
            new TextureRegionDrawable(new TextureRegion(MyGame.myAssets.getTexture(Assets.MENU_BUTTON, Gdx.graphics.getWidth()/2, (int)(0.06f * Gdx.graphics.getHeight())))));
    private ImageButton retry = new ImageButton(
            new TextureRegionDrawable(new TextureRegion(MyGame.myAssets.getTexture(Assets.RETRY_BUTTON, Gdx.graphics.getWidth()/2, (int)(0.06f * Gdx.graphics.getHeight())))));;

    public TimeTrial(MyGame myGame)
    {
        super(myGame);
    }

    @Override
    public void show()
    {
        getSquareLattice().prepareRandomLevelDesign();
        getSquareLattice().prepareLattice();
        initializeTilesListener();

        initializeButtons();
        prepareButtonsListener();

        super.show();
    }

    private void initializeButtons()
    {
        menu.setPosition(MENU_POSITION_X, MENU_POSITION_Y);
        retry.setPosition(RETRY_POSITION_X, RETRY_POSITION_Y);
    }

    private void prepareButtonsListener()
    {
        menu.addListener(new ClickListener()
        {
            @Override
            public void clicked(InputEvent event, float x, float y)
            {
                setGameState(GameState.QUIT);
            }
        });

        retry.addListener(new ClickListener()
        {
            @Override
            public void clicked(InputEvent event, float x, float y)
            {
                dispose();
                getMyGame().setScreen(new TimeTrial(getMyGame()));
            }
        });
    }

    @Override
    public void render(float delta)
    {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        increaseStateTime();

        MyGame.batch.enableBlending();
        MyGame.batch.begin();
        MyGame.batch.draw(getBackground(), 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        MyGame.batch.draw(getShade(), SHADE_BAR_POSITION_X, SHADE_BAR_POSITION_Y, getShade().getWidth(), getShade().getHeight());

        MyGame.batch.draw(timeBar, TIME_BAR_POSITION_X, TIME_BAR_POSITION_Y, timeBarWidth(), timeBar.getHeight());
        MyGame.batch.draw(timeBarBorder, TIME_BAR_POSITION_X, TIME_BAR_POSITION_Y, timeBarBorder.getWidth(), timeBarBorder.getHeight());

        getFont().draw(MyGame.batch, "Score " + scoreCounter.getScore(), SCORE_FONT_POSITION_X, SCORE_FONT_POSITION_Y, TARGET_WIDTH, -1, false);
        getFont().draw(MyGame.batch, "Time " + scoreCounter.remainingTime(), TIME_FONT_POSITION_X, TIME_FONT_POSITION_Y, TARGET_WIDTH, -1, false);
        MyGame.batch.end();

        if(getGameState() == GameState.RUNNING)
        {
            if(scoreCounter.isPaused())
            {
                scoreCounter.start();
            }

            if(scoreCounter.remainingTime() == 0)
            {
                if(scoreCounter.getScore() > MyGame.options.getHighscore())
                {
                    MyGame.options.setHighscore(scoreCounter.getScore());
                }

                getMyStage().addActor(menu);
                getMyStage().addActor(retry);

                setGameState(GameState.FINISHED);
            }

            getSquareLattice().drawLasers(getStateTime());

            if(getSquareLattice().isFinished())
            {
                setGameState(GameState.CHANGE_LEVEL);
            }
        }
        else
        {
            getSquareLattice().drawLasers(0f);
        }

        getMyStage().act(Gdx.graphics.getDeltaTime());
        getMyStage().draw();

        gameStateLogic();
    }

    public long timeBarWidth()
    {
        double timeBarPercentWidth = (float)scoreCounter.remainingTime()/(float)scoreCounter.getSTARTING_TIME();

        if(timeBarPercentWidth > 1)
        {
            return Gdx.graphics.getWidth();
        }
        else
        {
            return (int)(timeBarPercentWidth * Gdx.graphics.getWidth());
        }
    }

    private void gameStateLogic()
    {
        switch(getGameState())
        {
            case PAUSE:
                if(!scoreCounter.isPaused())
                {
                    scoreCounter.pause();
                }

                getPauseWindow().draw();
                break;

            case FINISHED:
                clearListeners();
                drawScore();
                break;

            case CHANGE_LEVEL:
                getSquareLattice().prepareRandomLevelDesign();
                getSquareLattice().prepareLattice();
                initializeTilesListener();

                clearActors();
                addActors();

                scoreCounter.increaseNumberOfFinishedLevels();
                scoreCounter.countScore();
                scoreCounter.increaseRemainingTime();

                setGameState(GameState.RUNNING);
                break;

            case QUIT:
                dispose();
                getMyGame().setScreen(new Menu(getMyGame()));
                break;
        }
    }

    private void initializeTilesListener()
    {
        for(List<SquareTile> tempSquareTiles: getSquareLattice().getSquareTiles())
        {
            for(SquareTile sqrTiles: tempSquareTiles)
            {
                sqrTiles.getImageButton().addListener(new ClickListener()
                {
                    @Override
                    public void clicked(InputEvent event, float x, float y)
                    {
                        sqrTiles.playSound();
                        sqrTiles.getImageButton().setRotation(getSquareLattice().rotateTile(sqrTiles));

                        scoreCounter.increaseNumberOfRotations();

                        getSquareLattice().assambleConnections();

                        if(getSquareLattice().isFinished())
                        {
                            MyGame.myAssets.playSound(Assets.WIN_LASER_SOUND);
                        }
                    }
                });
            }
        }
    }

    public void clearListeners()
    {
        for(List<SquareTile> tempSquareTiles: getSquareLattice().getSquareTiles())
        {
            for(SquareTile sqrTiles: tempSquareTiles)
            {
                sqrTiles.getImageButton().clearListeners();
            }
        }
    }

    public void drawScore()
    {
        MyGame.batch.begin();

        if(scoreCounter.getScore() >= MyGame.options.getHighscore())
        {
            MyGame.batch.draw(shade, SHADE_POSITION_X, SHADE_POSITION_Y);
            MyGame.batch.draw(highscore, TEXT_POSITION_X, HIGHSCORE_POSITION_Y);
        }

        MyGame.batch.draw(timeOut, TEXT_POSITION_X, TIME_OUT_POSITION_Y);

        MyGame.batch.end();
    }

    public void dispose()
    {
        shade.dispose();
        timeOut.dispose();
        highscore.dispose();
        timeBar.dispose();
        timeBarBorder.dispose();

        super.dispose();
    }
}