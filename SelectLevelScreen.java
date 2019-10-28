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

import java.util.List;

public class SelectLevelScreen implements Screen
{
    private MyGame myGame;
    private Stage myStage;
    private SelectLevel selectLevel;

    private ImageButton returnButton;
    private ImageButton nextPage;
    private ImageButton previousPage;

    private Texture background;
    private Texture panel;

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

        returnButton = new ImageButton(new TextureRegionDrawable(new TextureRegion(MyGame.myAssets.getTexture(Assets.RETURN_BUTTON))));
        nextPage = new ImageButton(new TextureRegionDrawable(new TextureRegion(MyGame.myAssets.getTexture(Assets.FORWARD_BUTTON))));
        previousPage = new ImageButton(new TextureRegionDrawable(new TextureRegion(MyGame.myAssets.getTexture(Assets.BACKWARD_BUTTON))));

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
                dispose();
                myGame.setScreen(new Menu(myGame, background));
            }
        });

        nextPage.addListener(new ClickListener()
        {
            @Override
            public void clicked(InputEvent event, float x, float y)
            {
                selectLevel.nextPage();
            }
        });

        previousPage.addListener(new ClickListener()
        {
            @Override
            public void clicked(InputEvent event, float x, float y)
            {
                selectLevel.previousPage();
            }
        });
    }

    private void initializeButtons()
    {
        returnButton.setSize(0.12f * Gdx.graphics.getWidth(), 0.12f * Gdx.graphics.getHeight());
        returnButton.setPosition(0.05f * Gdx.graphics.getWidth(), 0.01f * Gdx.graphics.getHeight());

        nextPage.setPosition(Gdx.graphics.getWidth() * 0.6f,Gdx.graphics.getHeight() * 0.12f);
        nextPage.setSize(0.12f * Gdx.graphics.getWidth(), 0.12f * Gdx.graphics.getHeight());

        previousPage.setPosition(Gdx.graphics.getWidth() * 0.3f,Gdx.graphics.getHeight() * 0.12f);
        previousPage.setSize(0.12f * Gdx.graphics.getWidth(), 0.12f * Gdx.graphics.getHeight());

        panel = MyGame.myAssets.getTexture(Assets.PANEL,(int)(0.8 * Gdx.graphics.getWidth()), (int)(0.6 * Gdx.graphics.getHeight()));
    }

    @Override
    public void render(float delta)
    {
        Gdx.gl.glClearColor(0,0,1,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);


        for(LevelButton lButton: selectLevel.getLevelButtons())
        {
            lButton.getImageButton().getClickListener();
        }

        returnButton.getClickListener();
        nextPage.getClickListener();
        previousPage.getClickListener();

        MyGame.batch.enableBlending();
        MyGame.batch.begin();
        MyGame.batch.draw(background, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        MyGame.batch.draw(panel, 0.1f * Gdx.graphics.getWidth(), Gdx.graphics.getHeight() / 2 - 0.85f * panel.getHeight() / 2);
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
    }
}
