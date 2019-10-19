package com.patryk.main;

import com.badlogic.gdx.Preferences;

public class Configuration
{
    private static final Configuration instance = new Configuration();
    public static Configuration getInstance()
    {
        return instance;
    }

    private Preferences myPreferences;

    private int volume;
    private int finishedLevel;
    private boolean sound;

    private Configuration()
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
