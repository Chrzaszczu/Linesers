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

    private int volume;
    private int finishedLevel;
    private boolean sound;

    private Options()
    {
    }

    public void loadConfigurationFile()
    {

    }

    public int getVolume()
    {
        return volume;
    }
    public boolean isSound()
    {
        return sound;
    }
}
