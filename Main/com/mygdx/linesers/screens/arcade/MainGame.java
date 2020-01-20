package Main.com.mygdx.linesers.screens.arcade;

import Main.com.mygdx.linesers.assets.Assets;
import Main.com.mygdx.linesers.screens.GameScreen;
import Main.com.mygdx.linesers.GameState;
import Main.com.mygdx.linesers.screens.Menu;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import Main.com.mygdx.linesers.MyGame;
import Main.com.mygdx.linesers.tiles.SquareTile;

import java.util.List;

public class MainGame extends GameScreen
{
    private int levelNumber;

    public MainGame (MyGame myGame, int levelNumber)
    {
        super(myGame);
        this.levelNumber = levelNumber;
    }

    @Override
    public void show()
    {
        getSquareLattice().prepareLevelDesign(levelNumber);
        getSquareLattice().prepareLattice();
        initializeTilesListener();

        super.show();
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
        getFont().draw(MyGame.batch, "Level: " + (levelNumber + 1), 0.08f * Gdx.graphics.getWidth(), 0.98f * Gdx.graphics.getHeight(), 0.1f * Gdx.graphics.getBackBufferHeight(), 1, false);
        MyGame.batch.end();

        if(getGameState() == GameState.RUNNING)
        {
            getSquareLattice().drawLasers(getStateTime());

            if(getSquareLattice().isFinished())
            {
                setGameState(GameState.FINISHED);
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

    private void gameStateLogic()
    {
        switch(getGameState())
        {
            case PAUSE:
                getPauseWindow().draw();
                break;

            case FINISHED:
                MyGame.options.addFinishedLevel(levelNumber);
                getYouWinWindow().draw();
                break;

            case CHANGE_LEVEL:
                dispose();
                if(levelNumber + 1 >= MyGame.myAssets.numberOfMaps())
                {
                    getMyGame().setScreen(new Menu(getMyGame()));
                }
                else
                {
                    getMyGame().setScreen(new MainGame(getMyGame(), ++levelNumber));
                }
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

    public void dispose()
    {
        super.dispose();
    }
}