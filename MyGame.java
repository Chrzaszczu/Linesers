package com.patryk.main;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MyGame extends Game
{
    public static SpriteBatch batch;
    public static final Assets myAssets = new Assets();

    @Override
    public void create()
    {
        Gdx.input.setCatchBackKey(true);

        batch = new SpriteBatch();

        myAssets.loadAssets();

        this.setScreen(new Menu(this));
    }
}
