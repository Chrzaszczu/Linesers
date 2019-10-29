package com.patryk.main;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class MyGame extends Game
{
    public static SpriteBatch batch;
    public static final Assets myAssets = new Assets();

    private OrthographicCamera camera = new OrthographicCamera(1024, 768);

    @Override
    public void create()
    {
        Gdx.input.setCatchBackKey(true);

        batch = new SpriteBatch();

        camera.position.set(1024 / 2, 768 / 2, 0);
        camera.update();

        batch.setProjectionMatrix(camera.combined);

        myAssets.loadAssets();

        this.setScreen(new Menu(this));
    }
}
