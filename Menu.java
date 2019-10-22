package com.patryk.main;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
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

    private TextureRegion background;
    private TextureRegion backgroundStars;
    private TextureRegion backgroundBigStars;

    private Texture logo;

    private Stage myStage;

    public Menu(MyGame myGame)
    {
        this.myGame = myGame;
    }

    @Override
    public void show()
    {
        myStage = new Stage(new ScreenViewport());

        startButton = new ImageButton(
                new TextureRegionDrawable(new TextureRegion(MyGame.myAssets.getTexture(Assets.START_BUTTON))));
        optionsButton = new ImageButton(
                new TextureRegionDrawable(new TextureRegion(MyGame.myAssets.getTexture(Assets.START_BUTTON))));

        background = new TextureRegion(MyGame.myAssets.getTexture(Assets.BACKGROUND_BLUE));
        backgroundStars = new TextureRegion(MyGame.myAssets.getTexture(Assets.STARS_SMALL));
        backgroundBigStars = new TextureRegion(MyGame.myAssets.getTexture(Assets.STARS_BIG));
        logo = new Texture(Assets.LOGO2);

        startButton.setPosition(Gdx.graphics.getWidth()/2f - startButton.getWidth()/2,Gdx.graphics.getHeight()/2);
        optionsButton.setPosition(Gdx.graphics.getWidth()/2f - startButton.getWidth()/2,Gdx.graphics.getHeight()/2f - 1.3f * optionsButton.getHeight());

        myStage.addActor(startButton);
        myStage.addActor(optionsButton);
        Gdx.input.setInputProcessor(myStage);
    }

    @Override
    public void render(float delta)
    {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        startButton.addListener(new ClickListener()
        {
            @Override
            public void clicked(InputEvent event, float x, float y){
                dispose();
                myGame.setScreen(new SelectLevelScreen(myGame));
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

        MyGame.batch.enableBlending();
        MyGame.batch.begin();
        MyGame.batch.draw(background, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        MyGame.batch.draw(backgroundStars, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        MyGame.batch.draw(backgroundBigStars, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        MyGame.batch.draw(logo, 0.85f * Gdx.graphics.getWidth(), 0.01f * Gdx.graphics.getHeight(),0.12f * Gdx.graphics.getWidth(), 0.095f * Gdx.graphics.getHeight());
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
        logo.dispose();

        startButton = null;
        optionsButton = null;
        background = null;
        backgroundStars = null;
        backgroundBigStars = null;
    }
}
