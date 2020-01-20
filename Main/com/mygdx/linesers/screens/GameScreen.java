package Main.com.mygdx.linesers.screens;

import Main.com.mygdx.linesers.GameState;
import Main.com.mygdx.linesers.assets.Assets;
import Main.com.mygdx.linesers.screens.windows.PauseWindow;
import Main.com.mygdx.linesers.screens.windows.YouWinWindow;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import Main.com.mygdx.linesers.levels.Lattice;
import Main.com.mygdx.linesers.MyGame;

import static Main.com.mygdx.linesers.screens.Menu.MINOR_BUTTONS_HEIGHT;
import static Main.com.mygdx.linesers.screens.Menu.MINOR_BUTTONS_WIDTH;

public class GameScreen implements Screen
{
    private static final int SHADE_WIDTH = Gdx.graphics.getWidth();
    private static final int SHADE_HEIGHT = (int)(0.06f * Gdx.graphics.getHeight());

    public static final int SHADE_BAR_POSITION_X = 0;
    public static final int SHADE_BAR_POSITION_Y = (int)(0.94f * Gdx.graphics.getHeight());

    public static final int PAUSE_POSITION_X = (int)(0.85f * Gdx.graphics.getWidth());
    public static final int PAUSE_POSITION_Y = (int)(0.01f * Gdx.graphics.getHeight());

    public static final int MUSIC_POSITION_X = (int)(0.05f * Gdx.graphics.getWidth());
    public static final int MUSIC_POSITION_Y = (int)(0.01f * Gdx.graphics.getHeight());

    public static final int SOUND_POSITION_X = (int)(0.2f * Gdx.graphics.getWidth());
    public static final int SOUND_POSITION_Y = (int)(0.01f * Gdx.graphics.getHeight());

    private final MyGame myGame;
    private Stage myStage = new Stage(new ScreenViewport());

    private ImageButton pauseButton = new ImageButton(
            new TextureRegionDrawable(new TextureRegion(MyGame.myAssets.getTexture(Assets.PAUSE_BUTTON, MINOR_BUTTONS_WIDTH, MINOR_BUTTONS_HEIGHT))));
    private ImageButton musicButton = new ImageButton(
            new TextureRegionDrawable(new TextureRegion(MyGame.myAssets.getTexture(Assets.MUSIC_BUTTON, MINOR_BUTTONS_WIDTH, MINOR_BUTTONS_HEIGHT))));
    private ImageButton soundButton = new ImageButton(
            new TextureRegionDrawable(new TextureRegion(MyGame.myAssets.getTexture(Assets.SOUND_BUTTON, MINOR_BUTTONS_WIDTH, MINOR_BUTTONS_HEIGHT))));

    private Texture background = MyGame.myAssets.getTexture(Assets.BACKGROUND, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
    private Texture shade = MyGame.myAssets.getTexture(Assets.SHADE, SHADE_WIDTH, SHADE_HEIGHT);

    private FreeTypeFontGenerator generator = new FreeTypeFontGenerator(MyGame.myAssets.getFont());
    private FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
    private BitmapFont font;

    private Lattice squareLattice = new Lattice();
    private GameState gameState = GameState.RUNNING;
    private PauseWindow pauseWindow;
    private YouWinWindow youWinWindow;

    private float stateTime = 0f;

    public GameScreen(MyGame myGame)
    {
        this.myGame = myGame;
    }

    @Override
    public void show()
    {
        pauseWindow = new PauseWindow(this, myStage);
        pauseWindow.initializeWindow();

        youWinWindow = new YouWinWindow(this, myStage);
        youWinWindow.initializeWindow();

        setFont();

        initializeButtons();
        prepareButtonsListener();

        addActors();
    }

    private void setFont()
    {
        parameter.size = (int)(0.06f * Gdx.graphics.getWidth());
        font = generator.generateFont(parameter);
        font.setColor(Color.GRAY);

        generator.dispose();
        parameter = null;
    }

    private void initializeButtons()
    {
        pauseButton.setSize(MINOR_BUTTONS_WIDTH, MINOR_BUTTONS_HEIGHT);
        pauseButton.setPosition(PAUSE_POSITION_X, PAUSE_POSITION_Y);

        musicButton.setSize(MINOR_BUTTONS_WIDTH, MINOR_BUTTONS_HEIGHT);
        musicButton.setPosition(MUSIC_POSITION_X, MUSIC_POSITION_Y);

        soundButton.setSize(MINOR_BUTTONS_WIDTH, MINOR_BUTTONS_HEIGHT);
        soundButton.setPosition(SOUND_POSITION_X, SOUND_POSITION_Y);
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

    protected void addActors()
    {
        squareLattice.addActors(myStage);
        myStage.addActor(pauseButton);
        myStage.addActor(musicButton);
        myStage.addActor(soundButton);

        Gdx.input.setInputProcessor(myStage);
    }

    protected void clearActors()
    {
        myStage.clear();
    }

    @Override
    public void render(float delta)
    {
    }

    public void increaseStateTime()
    {
        stateTime += Gdx.graphics.getDeltaTime();
    }

    public void setGameState(GameState gameState)
    {
        this.gameState = gameState;
    }

    public Texture getBackground()
    {
        return background;
    }

    public Texture getShade()
    {
        return shade;
    }

    public BitmapFont getFont()
    {
        return font;
    }

    public Lattice getSquareLattice()
    {
        return squareLattice;
    }

    public GameState getGameState()
    {
        return gameState;
    }

    public float getStateTime()
    {
        return stateTime;
    }

    public Stage getMyStage()
    {
        return myStage;
    }

    public MyGame getMyGame()
    {
        return myGame;
    }

    public PauseWindow getPauseWindow()
    {
        return pauseWindow;
    }

    public YouWinWindow getYouWinWindow()
    {
        return youWinWindow;
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
        shade.dispose();

        squareLattice = null;
        MyGame.myAssets.disposeTextures();
    }
}
