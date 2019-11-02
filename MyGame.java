package com.patryk.main;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
//import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class MyGame extends Game
{
    public static SpriteBatch batch;
    public static final Options options = Options.getInstance();
    public static final Assets myAssets = new Assets();

    //private OrthographicCamera camera = new OrthographicCamera(100, 100);

    @Override
    public void create()
    {
        Gdx.input.setCatchBackKey(true);

        batch = new SpriteBatch();

        // i could create FitViewport viewport pass it to the batch and stage
        // it would let me use different screen units
        // probably waste of time
        //
        //camera.position.set(100 / 2, 100 / 2, 0);
        //camera.update();
        //batch.setProjectionMatrix(camera.combined);

        myAssets.loadAssets();

        myAssets.getMusic(Assets.MUSIC).setVolume(0.2f);
        myAssets.getMusic(Assets.MUSIC).setLooping(true);
        myAssets.getMusic(Assets.MUSIC).play();

        this.setScreen(new Menu(this));
    }
}
