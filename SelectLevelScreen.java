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

        returnButton = new ImageButton(new TextureRegionDrawable(new TextureRegion(MyGame.myAssets.getTexture(Assets.START_BUTTON))));
        nextPage = new ImageButton(new TextureRegionDrawable(new TextureRegion(MyGame.myAssets.getTexture(Assets.PANEL))));
        previousPage = new ImageButton(new TextureRegionDrawable(new TextureRegion(MyGame.myAssets.getTexture(Assets.PANEL))));

        if(background == null)
        {
            background = MyGame.myAssets.getTexture("NebulaBlueS.png", Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        }

        returnButton.setPosition(50,50);
        returnButton.setSize(200,100);

        nextPage.setPosition(Gdx.graphics.getWidth() * 0.6f,Gdx.graphics.getHeight() * 0.13f);
        nextPage.setSize(100,100);

        previousPage.setPosition(Gdx.graphics.getWidth() * 0.3f,Gdx.graphics.getHeight() * 0.13f);
        previousPage.setSize(100,100);

        selectLevel = new SelectLevel(0);
        selectLevel.setLevelButtons();
        selectLevel.addActors(myStage);

        myStage.addActor(returnButton);
        myStage.addActor(nextPage);
        myStage.addActor(previousPage);
        Gdx.input.setInputProcessor(myStage);
    }

    @Override
    public void render(float delta)
    {
        Gdx.gl.glClearColor(0,0,1,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

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

        MyGame.batch.enableBlending();
        MyGame.batch.begin();
        MyGame.batch.draw(background, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
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
