package Main.com.mygdx.linesers.screens;

import Main.com.mygdx.linesers.MyGame;
import Main.com.mygdx.linesers.assets.Assets;
import Main.com.mygdx.linesers.screens.select.SelectLevelScreen;
import Main.com.mygdx.linesers.screens.timetrial.TimeTrial;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
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

public class Menu implements Screen
{
    public static final int MAIN_BUTTONS_WIDTH = (int)(0.55f * Gdx.graphics.getWidth());
    public static final int MAIN_BUTTONS_HEIGHT = (int)(0.1f * Gdx.graphics.getHeight());
    public static final int MINOR_BUTTONS_WIDTH = (int)(0.12f * Gdx.graphics.getWidth());
    public static final int MINOR_BUTTONS_HEIGHT = (int)(0.07f * Gdx.graphics.getHeight());

    private static final int HIGHSCORE_POSITION_X = (int)(0.05f * Gdx.graphics.getWidth());
    private static final int HIGHSCORE_POSITION_Y = (int)(0.97f * Gdx.graphics.getHeight());

    private MyGame myGame;
    private Stage myStage = new Stage(new ScreenViewport());

    private ImageButton arcadeButton = new ImageButton(
                new TextureRegionDrawable(new TextureRegion(MyGame.myAssets.getTexture(Assets.ARCADE_BUTTON, MAIN_BUTTONS_WIDTH, MAIN_BUTTONS_HEIGHT))));
    private ImageButton timeTrialButton = new ImageButton(
                new TextureRegionDrawable(new TextureRegion(MyGame.myAssets.getTexture(Assets.TIME_TRIAL_BUTTON, MAIN_BUTTONS_WIDTH, MAIN_BUTTONS_HEIGHT))));
    private ImageButton exitButton = new ImageButton(
                new TextureRegionDrawable(new TextureRegion(MyGame.myAssets.getTexture(Assets.EXIT_BUTTON, MAIN_BUTTONS_WIDTH, MAIN_BUTTONS_HEIGHT))));
    private ImageButton musicButton = new ImageButton(
                new TextureRegionDrawable(new TextureRegion(MyGame.myAssets.getTexture(Assets.MUSIC_BUTTON, MINOR_BUTTONS_WIDTH, MINOR_BUTTONS_HEIGHT))));
    private ImageButton soundButton = new ImageButton(
                new TextureRegionDrawable(new TextureRegion(MyGame.myAssets.getTexture(Assets.SOUND_BUTTON, MINOR_BUTTONS_WIDTH, MINOR_BUTTONS_HEIGHT))));

    private Texture title = MyGame.myAssets.getTexture(Assets.TITLE, Gdx.graphics.getWidth(), (int)(0.13f * Gdx.graphics.getHeight()));
    private Texture background = MyGame.myAssets.getTexture(Assets.BACKGROUND, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

    private FreeTypeFontGenerator generator = new FreeTypeFontGenerator(MyGame.myAssets.getFont());
    private FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
    private BitmapFont font;

    public Menu(MyGame myGame)
    {
        this.myGame = myGame;
    }

    @Override
    public void show()
    {
        initializeButtons();
        prepareButtonsListener();

        setFont();

        myStage.addActor(arcadeButton);
        myStage.addActor(timeTrialButton);
        myStage.addActor(soundButton);
        myStage.addActor(musicButton);
        myStage.addActor(exitButton);
        Gdx.input.setInputProcessor(myStage);
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
        arcadeButton.setSize(MAIN_BUTTONS_WIDTH, MAIN_BUTTONS_HEIGHT);
        arcadeButton.setPosition(Gdx.graphics.getWidth()/2 - arcadeButton.getWidth()/2, Gdx.graphics.getHeight()/2);

        timeTrialButton.setSize(MAIN_BUTTONS_WIDTH, MAIN_BUTTONS_HEIGHT);
        timeTrialButton.setPosition(Gdx.graphics.getWidth()/2 - timeTrialButton.getWidth()/2, Gdx.graphics.getHeight()/2 - 1.5f * timeTrialButton.getHeight());

        exitButton.setSize(MAIN_BUTTONS_WIDTH, MAIN_BUTTONS_HEIGHT);
        exitButton.setPosition(Gdx.graphics.getWidth()/2 - exitButton.getWidth()/2,Gdx.graphics.getHeight()/2f - 3f * exitButton.getHeight());

        musicButton.setSize(MINOR_BUTTONS_WIDTH, MINOR_BUTTONS_HEIGHT);
        musicButton.setPosition(0.05f * Gdx.graphics.getWidth(), 0.01f * Gdx.graphics.getHeight());

        soundButton.setSize(MINOR_BUTTONS_WIDTH, MINOR_BUTTONS_HEIGHT);
        soundButton.setPosition(0.2f * Gdx.graphics.getWidth(), 0.01f * Gdx.graphics.getHeight());
    }

    private void prepareButtonsListener()
    {
        arcadeButton.addListener(new ClickListener()
        {
            @Override
            public void clicked(InputEvent event, float x, float y)
            {
                MyGame.myAssets.playSound(Assets.SOUND_OF_BUTTON);
                dispose();
                myGame.setScreen(new SelectLevelScreen(myGame));
            }
        });

        timeTrialButton.addListener(new ClickListener()
        {
            @Override
            public void clicked(InputEvent event, float x, float y)
            {
                MyGame.myAssets.playSound(Assets.SOUND_OF_BUTTON);
                dispose();
                myGame.setScreen(new TimeTrial(myGame));
            }
        });

        exitButton.addListener(new ClickListener()
        {
            @Override
            public void clicked(InputEvent event, float x, float y)
            {
                MyGame.myAssets.playSound(Assets.SOUND_OF_BUTTON);
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

        MyGame.batch.enableBlending();
        MyGame.batch.begin();
        MyGame.batch.draw(background, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        MyGame.batch.draw(title, Gdx.graphics.getWidth()/2 - title.getWidth()/2, 0.75f * Gdx.graphics.getHeight(), title.getWidth(), title.getHeight());
        font.draw(MyGame.batch, "Highscore: " + MyGame.options.getHighscore(), HIGHSCORE_POSITION_X, HIGHSCORE_POSITION_Y, (int)(0.06f * Gdx.graphics.getWidth()), -1, false);
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
        myStage.clear();
        myStage.dispose();
        title.dispose();
        background.dispose();

        arcadeButton = null;
        timeTrialButton = null;
        exitButton = null;
        musicButton = null;
        soundButton = null;

        MyGame.myAssets.disposeTextures();
    }
}
