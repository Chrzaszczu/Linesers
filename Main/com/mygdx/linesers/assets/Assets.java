package Main.com.mygdx.linesers.assets;

import Main.com.mygdx.linesers.MyGame;
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
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class Assets
{
    public static final String BACKGROUND = "background.png";
    public static final String TILE_ONE = "tile_one.png";
    public static final String TILE_TWO = "tile_two.png";
    public static final String TILE_THREE = "tile_three.png";
    public static final String TILE_FOUR = "tile_four.png";
    public static final String TILE_HALF = "tile_half.png";
    public static final String TILE_ZERO = "tile_zero.png";
    public static final String TILE_START = "start.png";
    public static final String TILE_FINAL = "final.png";
    public static final String LASER = "Laser.png";
    public static final String START_BUTTON = "Start_BTN.png";
    public static final String ARCADE_BUTTON = "arcade_BTN.png";
    public static final String TIME_TRIAL_BUTTON = "timeTrial_BTN.png";
    public static final String EXIT_BUTTON = "Exit_BTN.png";
    public static final String RETURN_BUTTON = "Return_BTN.png";
    public static final String PAUSE_BUTTON = "Pause_BTN.png";
    public static final String OK_BUTTON = "Ok_BTN.png";
    public static final String CLOSE_BUTTON = "Close_BTN.png";
    public static final String FORWARD_BUTTON = "Forward_BTN.png";
    public static final String BACKWARD_BUTTON = "Backward_BTN.png";
    public static final String MUSIC_BUTTON = "Music_BTN.png";
    public static final String SOUND_BUTTON = "Sound_BTN.png";
    public static final String NEXT_GAME_BUTTON = "Next_level.png";
    public static final String MENU_BUTTON = "Menu_BTN.png";
    public static final String LEVEL_LOCKED = "Star_01.png";
    public static final String LEVEL_FINISHED = "Star_02.png";
    public static final String PANEL = "Options_panel.png";
    public static final String SHADE = "shade.png";
    public static final String TITLE = "Title.png";
    public static final String SELECT_LEVEL = "SelectLevel.png";
    public static final String YOU_WIN_TEXT = "YouWin.png";
    public static final String RESUME = "Resume_tex.png";
    public static final String TIME_BAR = "TimeBar.png";
    public static final String TIME_BAR_BORDER = "TimeBarBorder.png";
    public static final String HIGHSCORE_TEXT = "Highscore.png";
    public static final String YOUR_SCORE_TEXT = "YourScore.png";
    public static final String TIME_OUT = "TimeOut.png";
    public static final String RETRY_BUTTON = "Retry.png";

    public static final String SOUND_OF_BUTTON = "sound_button.wav";
    public static final String ROTATE_LASER_SOUND = "rotate_laser.wav";
    public static final String WIN_LASER_SOUND = "win_laser.wav";

    public static final String MUSIC = "music.mp3";

    public static final String MAPS_DIRECTORY = "Maps";

    public static final String FONT = "Font/pix_font.ttf";

    private final AssetManager myAssets = new AssetManager();

    private List<String> graphicsList = new ArrayList<>(Arrays.asList(BACKGROUND, TILE_ONE, TILE_TWO, TILE_THREE, TILE_FOUR,
            TILE_HALF, TILE_ZERO, TILE_START, TILE_FINAL, LASER, START_BUTTON, ARCADE_BUTTON, TIME_TRIAL_BUTTON, EXIT_BUTTON,
            RETURN_BUTTON, PAUSE_BUTTON, OK_BUTTON, CLOSE_BUTTON, FORWARD_BUTTON, BACKWARD_BUTTON, MUSIC_BUTTON, SOUND_BUTTON,
            NEXT_GAME_BUTTON, MENU_BUTTON, LEVEL_LOCKED, LEVEL_FINISHED, PANEL, SHADE, TITLE, SELECT_LEVEL, YOU_WIN_TEXT, RESUME,
            TIME_BAR, TIME_BAR_BORDER, HIGHSCORE_TEXT, YOUR_SCORE_TEXT, TIME_OUT, RETRY_BUTTON));

    private List<String> soundList = new ArrayList<>(Arrays.asList(SOUND_OF_BUTTON, ROTATE_LASER_SOUND, WIN_LASER_SOUND));

    private List<Texture> disposables = new LinkedList<>();

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

        disposables.add(newTexture);
        originalTexture.dispose();
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
        TextureRegion[][] animationFrames = TextureRegion.split(getTexture(assetName),
                getTexture(assetName).getWidth() / numberOfColumns, getTexture(assetName).getHeight() / numberOfRows);

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
        return Gdx.files.internal(FONT);
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
        FileHandle fileHandle = Gdx.files.internal(MAPS_DIRECTORY);
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
        myAssets.clear();

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

    public void disposeTextures()
    {
        for(Texture asset: disposables)
        {
            asset.dispose();
        }
    }
}
