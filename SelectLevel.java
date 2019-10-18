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

public class SelectLevel implements Screen
{
    private final MyGame myGame;
    private Stage myStage;
    private ImageButton returnButton;

    public SelectLevel(MyGame myGame)
    {
        this.myGame = myGame;
    }

    @Override
    public void show()
    {
        myStage = new Stage(new ScreenViewport());

        returnButton = new ImageButton(new TextureRegionDrawable(new TextureRegion(MyGame.myAssets.get("START.png", Texture.class))));

        returnButton.setPosition(50,50);
        returnButton.setSize(200,100);

        myStage.addActor(returnButton);
        Gdx.input.setInputProcessor(myStage);
    }

    @Override
    public void render(float delta)
    {
        Gdx.gl.glClearColor(1,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        returnButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y)
            {
                dispose();
                myGame.setScreen(new Menu(myGame));
            }
        });

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

    }
}
