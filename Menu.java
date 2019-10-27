package com.patryk.main;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
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

    private Texture background;
    private Texture logo;

    private Texture TEST;

    private Stage myStage;

    Animation<TextureRegion> walkAnimation;
    float stateTime;

    public Menu(MyGame myGame)
    {
        this.myGame = myGame;
    }

    public Menu(MyGame myGame, Texture background)
    {
        this.myGame = myGame;
        this.background = background;
    }

    @Override
    public void show()
    {
        myStage = new Stage(new ScreenViewport());

        startButton = new ImageButton(
                new TextureRegionDrawable(new TextureRegion(MyGame.myAssets.getTexture(Assets.START_BUTTON))));
        optionsButton = new ImageButton(
                new TextureRegionDrawable(new TextureRegion(MyGame.myAssets.getTexture(Assets.START_BUTTON))));

        logo = new Texture(Assets.LOGO2);

        if(background == null)
        {
            background = MyGame.myAssets.getTexture("NebulaBlueS.png", Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        }

        TEST = MyGame.myAssets.getTexture("StartAn.png");

        TextureRegion[][] tmp = TextureRegion.split(TEST, TEST.getWidth() / 5, TEST.getHeight() / 2);

        int k = 0;
        TextureRegion[] anim = new TextureRegion[10];
        for(int i = 0; i < 2; ++i)
        {
            for(int j = 0; j < 5; ++j)
            {
                anim[k++] = tmp[i][j];

            }

        }

        walkAnimation = new Animation<TextureRegion>(0.1f, anim);
        stateTime = 0f;

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
                myGame.setScreen(new SelectLevelScreen(myGame, background));
            }
        });

        optionsButton.addListener(new ClickListener()
        {
            @Override
            public void clicked(InputEvent event, float x, float y){
                dispose();
                myGame.setScreen(new OptionsScreen(myGame, background));
            }
        });

        stateTime += Gdx.graphics.getDeltaTime();
        TextureRegion currentFrame = walkAnimation.getKeyFrame(stateTime, true);

        MyGame.batch.enableBlending();
        MyGame.batch.begin();
        MyGame.batch.draw(background, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        MyGame.batch.draw(logo, 0.85f * Gdx.graphics.getWidth(), 0.01f * Gdx.graphics.getHeight(),0.12f * Gdx.graphics.getWidth(), 0.095f * Gdx.graphics.getHeight());
        MyGame.batch.draw(currentFrame, 0,0);
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
    }
}
