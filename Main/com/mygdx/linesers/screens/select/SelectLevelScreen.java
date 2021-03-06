package Main.com.mygdx.linesers.screens.select;

import Main.com.mygdx.linesers.assets.Assets;
import Main.com.mygdx.linesers.screens.arcade.MainGame;
import Main.com.mygdx.linesers.screens.Menu;
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
import Main.com.mygdx.linesers.MyGame;

import static Main.com.mygdx.linesers.screens.Menu.MINOR_BUTTONS_HEIGHT;
import static Main.com.mygdx.linesers.screens.Menu.MINOR_BUTTONS_WIDTH;

public class SelectLevelScreen implements Screen
{
    private MyGame myGame;
    private SelectLevel selectLevel;

    private Stage myStage = new Stage(new ScreenViewport());

    private ImageButton returnButton = new ImageButton(
                new TextureRegionDrawable(new TextureRegion(MyGame.myAssets.getTexture(Assets.RETURN_BUTTON, MINOR_BUTTONS_WIDTH, MINOR_BUTTONS_HEIGHT))));
    private ImageButton nextPage = new ImageButton(
                new TextureRegionDrawable(new TextureRegion(MyGame.myAssets.getTexture(Assets.FORWARD_BUTTON, MINOR_BUTTONS_WIDTH, MINOR_BUTTONS_HEIGHT))));
    private ImageButton previousPage = new ImageButton(
                new TextureRegionDrawable(new TextureRegion(MyGame.myAssets.getTexture(Assets.BACKWARD_BUTTON, MINOR_BUTTONS_WIDTH, MINOR_BUTTONS_HEIGHT))));
    private ImageButton musicButton = new ImageButton(
                new TextureRegionDrawable(new TextureRegion(MyGame.myAssets.getTexture(Assets.MUSIC_BUTTON, MINOR_BUTTONS_WIDTH, MINOR_BUTTONS_HEIGHT))));
    private ImageButton soundButton = new ImageButton(
                new TextureRegionDrawable(new TextureRegion(MyGame.myAssets.getTexture(Assets.SOUND_BUTTON, MINOR_BUTTONS_WIDTH, MINOR_BUTTONS_HEIGHT))));

    private Texture panel = MyGame.myAssets.getTexture(Assets.PANEL,(int)(0.85 * Gdx.graphics.getWidth()), (int)(0.52f * Gdx.graphics.getHeight()));
    private Texture background = MyGame.myAssets.getTexture(Assets.BACKGROUND, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
    private Texture selectLevelText = MyGame.myAssets.getTexture(Assets.SELECT_LEVEL, Gdx.graphics.getWidth(), (int)(0.13f * Gdx.graphics.getHeight()));

    private int page = 0;

    public SelectLevelScreen(MyGame myGame)
    {
        this.myGame = myGame;
    }

    @Override
    public void show()
    {
        selectLevel = new SelectLevel(page);
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
            lButton.getImageButton().addListener(new ClickListener()
            {
                @Override
                public void clicked(InputEvent event, float x, float y)
                {
                    MyGame.myAssets.playSound(Assets.SOUND_OF_BUTTON);
                    dispose();
                    myGame.setScreen(new MainGame(myGame, lButton.getLevelNumber()));
                }
            });
        }

        returnButton.addListener(new ClickListener()
        {
            @Override
            public void clicked(InputEvent event, float x, float y)
            {
                MyGame.myAssets.playSound(Assets.SOUND_OF_BUTTON);
                dispose();
                myGame.setScreen(new Menu(myGame));
            }
        });

        nextPage.addListener(new ClickListener()
        {
            @Override
            public void clicked(InputEvent event, float x, float y)
            {
                MyGame.myAssets.playSound(Assets.SOUND_OF_BUTTON);
                selectLevel.nextPage();
            }
        });

        previousPage.addListener(new ClickListener()
        {
            @Override
            public void clicked(InputEvent event, float x, float y)
            {
                MyGame.myAssets.playSound(Assets.SOUND_OF_BUTTON);
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
        MyGame.batch.draw(selectLevelText, Gdx.graphics.getWidth()/2 - selectLevelText.getWidth()/2, 0.8f * Gdx.graphics.getHeight(), selectLevelText.getWidth(), selectLevelText.getHeight());
        MyGame.batch.end();

        myStage.draw();

        selectLevel.drawLevelNumbers();
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
        panel.dispose();
        background.dispose();
        selectLevelText.dispose();
        selectLevel.dispose();

        returnButton = null;
        nextPage = null;
        previousPage = null;
        musicButton = null;
        soundButton = null;
        MyGame.myAssets.disposeTextures();
    }
}
