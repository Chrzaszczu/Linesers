package com.mygdx.linesers;

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

public class OptionsScreen implements Screen
{
    private final MyGame myGame;
    private Stage myStage;

    private ImageButton returnButton;

    private Texture imgPanel;
    private Texture background;

    public OptionsScreen(MyGame myGame, Texture background)
    {
        this.myGame = myGame;
        this.background = background;
    }

    @Override
    public void show()
    {
        myStage = new Stage(new ScreenViewport());

        imgPanel = MyGame.myAssets.getTexture(Assets.PANEL, (int)(0.8 * Gdx.graphics.getWidth()), (int)(0.6 * Gdx.graphics.getHeight()));
        returnButton = new ImageButton(new TextureRegionDrawable(new TextureRegion(MyGame.myAssets.getTexture(Assets.RETURN_BUTTON))));

        if(background == null)
        {
            background = MyGame.myAssets.getTexture(Assets.BACKGROUND, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        }

        returnButton.setSize(0.12f * Gdx.graphics.getWidth(), 0.12f * Gdx.graphics.getHeight());
        returnButton.setPosition(0.05f * Gdx.graphics.getWidth(), 0.01f * Gdx.graphics.getHeight());

        prepareButtonsListener();

        myStage.addActor(returnButton);
        Gdx.input.setInputProcessor(myStage);
    }

    private void prepareButtonsListener()
    {
        returnButton.addListener(new ClickListener()
        {
            @Override
            public void clicked(InputEvent event, float x, float y)
            {
                MyGame.myAssets.getSound(Assets.SOUND_OF_BUTTON).play(MyGame.options.getSoundVolume());
                dispose();
                myGame.setScreen(new Menu(myGame, background));
            }
        });
    }

    @Override
    public void render(float delta)
    {
        Gdx.gl.glClearColor(1,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        returnButton.getClickListener();

        MyGame.batch.enableBlending();
        MyGame.batch.begin();
        MyGame.batch.draw(background, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        MyGame.batch.draw(imgPanel, 0.1f * Gdx.graphics.getWidth(), Gdx.graphics.getHeight() / 2 - imgPanel.getHeight() / 2);
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

        imgPanel = null;
        returnButton = null;
    }
}
