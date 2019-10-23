package com.patryk.main;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.PixmapLoader;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Assets
{
    public final static String TILE_ONE = "tmp1.png";
    public final static String TILE_TWO = "tmp2.png";
    public final static String TILE_THREE = "tmp3.png";
    public final static String TILE_FOUR = "tmp4.png";
    public final static String TILE_HALF = "tmp5.png";
    public final static String TILE_START = "tmps.png";
    public final static String TILE_FINAL = "tmpk.png";
    public final static String GLOWING_TILE_ONE = "tmp1g.png";
    public final static String GLOWING_TILE_TWO = "tmp2g.png";
    public final static String GLOWING_TILE_THREE = "tmp3g.png";
    public final static String GLOWING_TILE_FOUR = "tmp4g.png";
    public final static String GLOWING_TILE_HALF = "tmp5g.png";
    public final static String GLOWING_TILE_FINAL = "tmpkg.png";
    public final static String START_BUTTON = "START.png";
    public final static String PANEL = "Options.png";
    public final static String BACKGROUND_BLUE = "NebulaBlueS.png";
    public final static String BACKGROUND_AQUA = "NebulaAqua.png";
    public final static String STARS_SMALL = "StarsSmall.png";
    public final static String STARS_BIG = "StarsBig2.png";
    public final static String LOGO2 = "Logotmp.png";

    private final AssetManager myAssets = new AssetManager();

    private List<String> graphicsList = new ArrayList<String>(Arrays.asList(TILE_ONE, TILE_TWO, TILE_THREE, TILE_FOUR, TILE_HALF,
            TILE_START, TILE_FINAL, GLOWING_TILE_ONE, GLOWING_TILE_TWO, GLOWING_TILE_THREE, GLOWING_TILE_FOUR, GLOWING_TILE_HALF,
            GLOWING_TILE_FINAL, START_BUTTON, PANEL, BACKGROUND_BLUE, BACKGROUND_AQUA, STARS_SMALL, STARS_BIG, LOGO2));
    private List<String> soundList = new ArrayList<String>();

    public Texture getTexture(String textureName, int width, int height)
    {
        Texture originalTexture;
        Texture newTexture;

        originalTexture = myAssets.get(textureName, Texture.class);
        if(!originalTexture.getTextureData().isPrepared())
        {
            originalTexture.getTextureData().prepare();
        }

        Pixmap originalPixmap = originalTexture.getTextureData().consumePixmap();
        Pixmap newPixmap = new Pixmap(width, height, originalPixmap.getFormat());
        newPixmap.drawPixmap(originalPixmap,
                0,0, originalPixmap.getWidth(), originalPixmap.getHeight(),
                0, 0, newPixmap.getWidth(), newPixmap.getHeight());

        newTexture = new Texture(newPixmap);

        originalPixmap.dispose();
        newPixmap.dispose();

        return newTexture;
    }

    public Texture getTexture(String assetName)
    {
        return myAssets.get(assetName, Texture.class);
    }

    public void loadAssets()
    {
        for(String fileName: graphicsList)
        {
            myAssets.load(fileName, Texture.class);
        }

        myAssets.finishLoading();
    }
}
