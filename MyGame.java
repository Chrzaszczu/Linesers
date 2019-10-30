package com.patryk.main;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
//import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class MyGame extends Game
{
    public static SpriteBatch batch;
    public static final Assets myAssets = new Assets();

    //private OrthographicCamera camera = new OrthographicCamera(100, 100);

    @Override
    public void create()
    {
        Gdx.input.setCatchBackKey(true);

        batch = new SpriteBatch();

        // i could create FitViewport viewport pass it to the batch and stage
        // it would allow me to use different screen units
        // probably waste of time
        //
        //camera.position.set(100 / 2, 100 / 2, 0);
        //camera.update();
        //batch.setProjectionMatrix(camera.combined);

        myAssets.loadAssets();

        this.setScreen(new Menu(this));
    }
}
