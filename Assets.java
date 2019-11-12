package com.mygdx.linesers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Assets
{
    public final static String BACKGROUND = "background.png";
    public final static String TILE_ONE = "tile_one.png";
    public final static String TILE_TWO = "tile_two.png";
    public final static String TILE_THREE = "tile_three.png";
    public final static String TILE_FOUR = "tile_four.png";
    public final static String TILE_HALF = "tile_half.png";
    public final static String TILE_START = "start.png";
    public final static String TILE_FINAL = "tmpk.png";
    public final static String LASER = "Laser.png";
    public final static String START_BUTTON = "Start_BTN.png";
    public final static String EXIT_BUTTON = "Exit_BTN.png";
    public final static String RETURN_BUTTON = "Return_BTN.png";
    public final static String PLAY_BUTTON = "Play_BTN.png";
    public final static String PAUSE_BUTTON = "Pause_BTN.png";
    public final static String OK_BUTTON = "Ok_BTN.png";
    public final static String CLOSE_BUTTON = "Close_BTN.png";
    public final static String FORWARD_BUTTON = "Forward_BTN.png";
    public final static String BACKWARD_BUTTON = "Backward_BTN.png";
    public final static String MUSIC_BUTTON = "Music_BTN.png";
    public final static String SOUND_BUTTON = "Sound_BTN.png";
    public final static String NEXT_GAME_BUTTON = "Next_level.png";
    public final static String LEVEL_LOCKED = "Star_01.png";
    public final static String LEVEL_FINISHED = "Star_02.png";
    public final static String PANEL = "Options_panel.png";
    public final static String TITLE = "Title.png";
    public final static String SELECT_LEVEL = "SelectLevel.png";
    public final static String YOU_WIN_TEXT = "YouWin.png";
    public final static String RESUME = "Resume_tex.png";
    public final static String SCREEN_DARKENING = "shadow.png";
    public final static String LOGO2 = "Logotmp.png";

    public final static String SOUND_OF_BUTTON = "sound_button.wav";
    public final static String ROTATE_LASER_SOUND = "rotate_laser.wav";
    public final static String WIN_LASER_SOUND = "win_laser.wav";

    public final static String MUSIC = "music.wav";

    private final AssetManager myAssets = new AssetManager();

    private List<String> graphicsList = new ArrayList<String>(Arrays.asList(BACKGROUND, TILE_ONE, TILE_TWO, TILE_THREE, TILE_FOUR,
            TILE_HALF, TILE_START, TILE_FINAL, LASER, START_BUTTON, EXIT_BUTTON, RETURN_BUTTON, PLAY_BUTTON, PAUSE_BUTTON,
            OK_BUTTON, CLOSE_BUTTON, FORWARD_BUTTON, BACKWARD_BUTTON, MUSIC_BUTTON, SOUND_BUTTON, NEXT_GAME_BUTTON,
            LEVEL_LOCKED, LEVEL_FINISHED, PANEL, TITLE, SELECT_LEVEL, YOU_WIN_TEXT, RESUME, SCREEN_DARKENING, LOGO2));

    private List<String> soundList = new ArrayList<String>(Arrays.asList(SOUND_OF_BUTTON, ROTATE_LASER_SOUND, WIN_LASER_SOUND));

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

    public Animation<TextureRegion> prepareAnimation(String assetName, int numberOfColumns, int numberOfRows, float frameDuration)
    {
        Texture sheet = getTexture(assetName);
        TextureRegion[][] animationFrames = TextureRegion.split(sheet,
                sheet.getWidth() / numberOfColumns, sheet.getHeight() / numberOfRows);

        TextureRegion[] preparedFrames = new TextureRegion[numberOfColumns * numberOfRows];

        int index = 0;
        for(TextureRegion[] region: animationFrames)
        {
            for(TextureRegion animationFrame: region)
            {
                preparedFrames[index++] = animationFrame;
            }
        }

        return new Animation<>(frameDuration, preparedFrames);
    }

    public FileHandle getFont()
    {
        return Gdx.files.internal("Font/pix_font.ttf");
    }

    public JSONArray parseJSONFile(String mapName)
    {
        StringBuilder content = new StringBuilder();
        FileHandle fileHandle = Gdx.files.internal(mapName);

        Scanner file = new Scanner(fileHandle.read());

        while(file.hasNextLine())
        {
            content.append(file.nextLine());
        }

        return new JSONArray(content.toString());
    }

    public int numberOfMaps()
    {
        FileHandle fileHandle = Gdx.files.internal("Maps");
        return fileHandle.list().length;
    }

    public void playSound(String assetName)
    {
        if(MyGame.options.isSound())
        {
            getSound(assetName).play(MyGame.options.getSoundVolume());
        }
    }

    public void playMusic()
    {
        if(MyGame.options.isMusic())
        {
            getMusic(Assets.MUSIC).setVolume(MyGame.options.getMusicVolume());
            getMusic(Assets.MUSIC).setLooping(true);
            getMusic(Assets.MUSIC).play();
        }
    }

    public Sound getSound(String assetName)
    {
        return myAssets.get(assetName, Sound.class);
    }

    public Music getMusic(String assetName)
    {
        return myAssets.get(assetName, Music.class);
    }

    public void loadAssets()
    {
        for(String fileName: graphicsList)
        {
            myAssets.load(fileName, Texture.class);
        }

        for(String fileName: soundList)
        {
            myAssets.load(fileName, Sound.class);
        }

        myAssets.load(MUSIC, Music.class);

        myAssets.finishLoading();
    }
}
