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
    public boolean isRunning()
    {
        return running;
    }

    public void setRunning(boolean running)
    {
        this.running = running;
    }

    public void setFinished(boolean finished)
    {
        this.finished = finished;
    }

    public GameState()
    {
        running     = true;
        finished    = false;
    }
}
