package com.patryk.main;

public class GameState
{
    private boolean running;
    private boolean finished;

    private int score;
    private int numberOfTurns;
    private int timing;

    public boolean getFinished()
    {
        return finished;
    }
    public boolean getRunning()
    {
        return running;
    }

    public void setRunning(boolean R)
    {
        running = R;
    }

    public void setFinished(boolean F)
    {
        finished = F;
    }

    public GameState()
    {
        running     = true;
        finished    = false;
    }
}
