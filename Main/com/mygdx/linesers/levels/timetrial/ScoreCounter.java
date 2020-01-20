package Main.com.mygdx.linesers.levels.timetrial;

import java.util.Date;

public class ScoreCounter //TODO work on it
{
    private static final int BONUS_WIN_SCORE = 100;
    private static final int ROTATION_SCORE = 1000;
    private static final int STARTING_TIME = 60000;
    private static final int WIN_TIME = 15000;
    private static final int WIN_MEDIUM_TIME = 10000;
    private static final int WIN_SHORT_TIME = 8000;

    private long startingTime = new Date().getTime();
    private long remainingTime = startingTime + STARTING_TIME;
    private long pauseTime = 0;
    private int score = 0;
    private int numberOfFinishedLevels = 0;
    private int numberOfRotations = 0;

    private boolean pause = false;

    public void countScore()
    {
        score += BONUS_WIN_SCORE + rotationsScore() + 10 * numberOfFinishedLevels;
    }

    public int rotationsScore()
    {
        return ROTATION_SCORE / (numberOfRotations + 1);
    }

    public int getScore()
    {
        return score;
    }

    public void increaseNumberOfRotations()
    {
        numberOfRotations++;
    }

    public void increaseNumberOfFinishedLevels()
    {
        numberOfFinishedLevels++;
    }

    public void increaseRemainingTime()
    {
        if(numberOfFinishedLevels > 30)
        {
            remainingTime += WIN_SHORT_TIME;
        }
        else if(numberOfFinishedLevels > 10)
        {
            remainingTime += WIN_MEDIUM_TIME;
        }
        else
        {
            remainingTime += WIN_TIME;
        }
    }

    public long remainingTime()
    {
        long currentTime = new Date().getTime();
        long tempTime = remainingTime - currentTime;

        if(isPaused())
        {
            remainingTime += currentTime - pauseTime;
            pauseTime = currentTime;
        }

        if(tempTime <= 0)
        {
            return 0;
        }

        return tempTime / 1000;
    }

    public void start()
    {
        pause = false;
    }

    public void pause()
    {
        pauseTime = new Date().getTime();
        pause = true;
    }

    public boolean isPaused()
    {
        return pause;
    }

    public int getSTARTING_TIME()
    {
        return STARTING_TIME / 1000;
    }
}
