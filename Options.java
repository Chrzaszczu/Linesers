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

    private float volume = 1.0f;
    private int finishedLevel;
    private boolean sound;
    private boolean music;

    private Options()
    {
    }

    public void loadConfigurationFile()
    {

    }

    public float getVolume()
    {
        return volume;
    }

    public boolean isSound()
    {
        return sound;
    }

    public boolean isMusic()
    {
        return music;
    }
}
