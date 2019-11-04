package com.patryk.main;

import com.badlogic.gdx.Preferences;

public class Options
{
    private static final Options instance = new Options();
    public static Options getInstance()
    {
        return instance;
    }

    private Preferences myPreferences;

    private float soundVolume = 1.0f;
    private float musicVolume = 0.3f;

    private int finishedLevel;
    private boolean sound = true;
    private boolean music = true;

    private Options()
    {
    }

    public void loadConfigurationFile()
    {

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
    }

    public void setMusic(boolean music)
    {
        this.music = music;
    }
}
