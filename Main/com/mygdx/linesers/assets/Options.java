package Main.com.mygdx.linesers.assets;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import Main.com.mygdx.linesers.MyGame;

import java.util.LinkedList;
import java.util.List;

public class Options
{
    private static final String SOUND_PREFERENCE = "sound";
    private static final String MUSIC_PREFERENCE = "music";
    private static final String HIGHSCORE_PREFERENCE = "highscore";
    private static final String PROFILE_NAME = "myPreferences";

    private static final float SOUND_VOLUME = 1.0f;
    private static final float MUSIC_VOLUME = 0.33f;

    private static final Options instance = new Options();
    public static Options getInstance()
    {
        return instance;
    }

    private Preferences myPreferences;

    private final List<Integer> finishedLevels = new LinkedList<>();
    private boolean sound;
    private boolean music;
    private long highscore;

    private Options()
    {
    }

    public void loadConfigurationFile()
    {
        myPreferences = Gdx.app.getPreferences(PROFILE_NAME);
        sound = myPreferences.getBoolean(SOUND_PREFERENCE, true);
        music = myPreferences.getBoolean(MUSIC_PREFERENCE, true);
        highscore = myPreferences.getLong(HIGHSCORE_PREFERENCE, 0);

        for(int mapNumber = 0; mapNumber < MyGame.myAssets.numberOfMaps(); mapNumber++)
        {
            int finishedLevel = myPreferences.getInteger(String.valueOf(mapNumber), -1);

            if(finishedLevel != -1)
            {
                finishedLevels.add(finishedLevel);
            }
        }
    }

    public void addFinishedLevel(int levelNumber)
    {
        if(!finishedLevels.contains(levelNumber))
        {
            finishedLevels.add(levelNumber);
            myPreferences.putInteger(String.valueOf(levelNumber), levelNumber);
        }

        myPreferences.flush();
    }

    public List<Integer> getFinishedLevels()
    {
        return finishedLevels;
    }

    public float getSoundVolume()
    {
        return SOUND_VOLUME;
    }

    public float getMusicVolume()
    {
        return MUSIC_VOLUME;
    }

    public long getHighscore()
    {
        return highscore;
    }

    public boolean isSound()
    {
        return sound;
    }

    public boolean isMusic()
    {
        return music;
    }

    public void setSound(boolean sound)
    {
        this.sound = sound;
        myPreferences.putBoolean(SOUND_PREFERENCE, sound);
        myPreferences.flush();
    }

    public void setMusic(boolean music)
    {
        this.music = music;
        myPreferences.putBoolean(MUSIC_PREFERENCE, music);
        myPreferences.flush();
    }

    public void setHighscore(long highscore)
    {
        this.highscore = highscore;
        myPreferences.putLong(HIGHSCORE_PREFERENCE, highscore);
        myPreferences.flush();
    }
}
