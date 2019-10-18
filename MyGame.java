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

    public static final AssetManager myAssets = new AssetManager(); // NIE UZYWAC STATIC NA ANDROIDZIE

    private List<String> graphicsList = new ArrayList<String>(Arrays.asList("tmp1.png", "tmp2.png", "tmp3.png", "tmp4.png", "tmp5.png",
            "tmps.png", "tmpk.png", "tmp1g.png", "tmp2g.png", "tmp3g.png", "tmp4g.png", "tmp5g.png", "tmpkg.png", "START.png", "Options.png"));
    private List<String> soundList = new ArrayList<String>();

    public void loadAssets()
    {
        if(batch == null)
        {
            batch = new SpriteBatch();
        }

        for(String fileName: graphicsList)
        {
            myAssets.load(fileName, Texture.class);
        }

        myAssets.finishLoading();
    }

    @Override
    public void create()
    {
        Gdx.input.setCatchBackKey(true);

        loadAssets();

        if(myAssets.update())
        {
            this.setScreen(new Menu(this));
        }
    }
}
