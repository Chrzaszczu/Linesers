package com.mygdx.linesers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;

import java.util.LinkedList;
import java.util.List;

public class Options
{
    private static final String SOUND_PREF = "sound";
    private static final String MUSIC_PREF = "music";

    private static final Options instance = new Options();
    public static Options getInstance()
    {
        return instance;
    }

    private Preferences myPreferences;

    private float soundVolume = 1.0f;
    private float musicVolume = 0.3f;

    private final List<Integer> finishedLevels = new LinkedList<>();
    private boolean sound = true;
    private boolean music = true;

    private Options()
    {
    }

    public void loadConfigurationFile()
    {
        myPreferences = Gdx.app.getPreferences("myPreferences");
        sound = myPreferences.getBoolean(SOUND_PREF, true);
        music = myPreferences.getBoolean(MUSIC_PREF, true);

        for(int mapNumber = 0; mapNumber < MyGame.myAssets.numberOfMaps(); mapNumber++)
        {
            int temp = myPreferences.getInteger(String.valueOf(mapNumber), -1);

            if(temp != -1)
            {
                finishedLevels.add(temp);
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
        return soundVolume;
    }

    public float getMusicVolume()
    {
        return musicVolume;
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
        myPreferences.putBoolean(SOUND_PREF, sound);
        myPreferences.flush();
    }

    public void setMusic(boolean music)
    {
        this.music = music;
        myPreferences.putBoolean(MUSIC_PREF, music);
        myPreferences.flush();
    }
}
