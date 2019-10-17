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

public class Menu implements Screen
{
    private MyGame myGame;

    private ImageButton startButton;
    private ImageButton optionsButton;

    private Stage myStage;

    @Override
    public void show()
    {
        myStage = new Stage(new ScreenViewport());

        startButton = new ImageButton(
                new TextureRegionDrawable(new TextureRegion(MyGame.myAssets.get("START.png", Texture.class))));
        optionsButton = new ImageButton(
                new TextureRegionDrawable(new TextureRegion(MyGame.myAssets.get("START.png", Texture.class))));

        startButton.setPosition(Gdx.graphics.getWidth()/2f - startButton.getWidth()/2,Gdx.graphics.getHeight()/2);
        optionsButton.setPosition(Gdx.graphics.getWidth()/2f - startButton.getWidth()/2,Gdx.graphics.getHeight()/2f - 1.3f * optionsButton.getHeight());

        myStage.addActor(startButton);
        myStage.addActor(optionsButton);
        Gdx.input.setInputProcessor(myStage);
    }

    @Override
    public void render(float delta)
    {
        Gdx.gl.glClearColor(1,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        startButton.addListener(new ClickListener()
        {
            @Override
            public void clicked(InputEvent event, float x, float y){
                dispose();
                myGame.setScreen(new MainGame(myGame));
            }
        });

        optionsButton.addListener(new ClickListener()
        {
            @Override
            public void clicked(InputEvent event, float x, float y){
                dispose();
                myGame.setScreen(new OptionsScreen(myGame));
            }
        });

        myStage.draw();
    }

    public Menu(MyGame myGame)
    {
        this.myGame = myGame;
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

        startButton = null;
        optionsButton = null;
    }
}
