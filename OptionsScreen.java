package com.patryk.main;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
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

    private TextureRegion imgPanel;
    private TextureRegion background;
    private TextureRegion backgroundStars;
    private TextureRegion backgroundBigStars;

    public OptionsScreen(MyGame myGame)
    {
        this.myGame = myGame;
    }

    @Override
    public void show()
    {
        myStage = new Stage(new ScreenViewport());

        imgPanel = new TextureRegion(MyGame.myAssets.getTexture(Assets.PANEL));
        returnButton = new ImageButton(new TextureRegionDrawable(new TextureRegion(MyGame.myAssets.getTexture(Assets.START_BUTTON))));

        background = new TextureRegion(MyGame.myAssets.getTexture(Assets.BACKGROUND_BLUE));
        backgroundStars = new TextureRegion(MyGame.myAssets.getTexture(Assets.STARS_SMALL));
        backgroundBigStars = new TextureRegion(MyGame.myAssets.getTexture(Assets.STARS_BIG));

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

        MyGame.batch.enableBlending();
        MyGame.batch.begin();
        MyGame.batch.draw(background, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        MyGame.batch.draw(backgroundStars, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        MyGame.batch.draw(backgroundBigStars, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        // OGARNAC PANEL
        MyGame.batch.draw(imgPanel,(float)(Gdx.graphics.getWidth()*0.2), Gdx.graphics.getHeight() / 2 - imgPanel.getRegionHeight() / 2, Gdx.graphics.getWidth() / 2 - imgPanel.getRegionWidth() / 2, Gdx.graphics.getHeight() / 2 - imgPanel.getRegionHeight() / 2, 0.6f * Gdx.graphics.getWidth(), 0.5f * Gdx.graphics.getHeight(), (float)1, (float)1,0);
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
        background = null;
        backgroundStars = null;
        backgroundBigStars = null;
    }
}
