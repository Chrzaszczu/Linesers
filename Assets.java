package com.patryk.main;


import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Assets
{
    public final static String TILE_ONE = "tile_one.png";
    public final static String TILE_TWO = "tile_two.png";
    public final static String TILE_THREE = "tile_three.png";
    public final static String TILE_FOUR = "tile_four.png";
    public final static String TILE_HALF = "tile_half.png";
    public final static String TILE_START = "starting.png";
    public final static String TILE_FINAL = "tmpk.png";
    public final static String GLOWING_TILE_ONE = "glowing_one.png";
    public final static String GLOWING_TILE_TWO = "glowing_two.png";
    public final static String GLOWING_TILE_THREE = "glowing_three.png";
    public final static String GLOWING_TILE_FOUR = "glowing_four.png";
    public final static String GLOWING_TILE_HALF = "glowing_half.png";
    public final static String GLOWING_TILE_FINAL = "final.png";
    public final static String START_BUTTON = "START.png";
    public final static String PANEL = "Options.png";
    public final static String BACKGROUND_BLUE = "NebulaBlueS.png";
    public final static String BACKGROUND_AQUA = "NebulaAqua.png";
    public final static String LOGO2 = "Logotmp.png";

    private final AssetManager myAssets = new AssetManager();

    private List<String> graphicsList = new ArrayList<String>(Arrays.asList(TILE_ONE, TILE_TWO, TILE_THREE, TILE_FOUR, TILE_HALF,
            TILE_START, TILE_FINAL, GLOWING_TILE_ONE, GLOWING_TILE_TWO, GLOWING_TILE_THREE, GLOWING_TILE_FOUR, GLOWING_TILE_HALF,
            GLOWING_TILE_FINAL, START_BUTTON, PANEL, BACKGROUND_BLUE, BACKGROUND_AQUA, LOGO2));
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

    public Animation<TextureRegion> prepareAnimation(Texture texture, int numberOfColumns, int numberOfRows, float frameDuration)
    {
        TextureRegion[][] temporary = TextureRegion.split(texture,
                texture.getWidth() / numberOfColumns, texture.getHeight() / numberOfRows);

        TextureRegion[] preparedFrames = new TextureRegion[numberOfColumns * numberOfRows];

        int index = 0;
        for(int row = 0; row < numberOfRows; ++row)
        {
            for(int column = 0; column < numberOfColumns; ++column)
            {
                preparedFrames[index++] = temporary[row][column];
            }
        }

        return new Animation<TextureRegion>(frameDuration, preparedFrames);
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
