package com.patryk.main;

public class Options {
    private static final Options instance = new Options();
    public static Options getInstance(){
        return instance;
    }

    // variables
    private int volume;
    private boolean sound;

    // methods
    public Options(){
    }

    public int getVolume(){
        return volume;
    }
    public boolean ifSound(){
        return sound;
    }
}
