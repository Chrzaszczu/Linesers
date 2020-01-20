package Main.com.mygdx.linesers.levels;

public class LaserPosition
{
    private Vector startingPoint;
    private Vector endingPoint;

    public LaserPosition(Vector startingPoint, Vector endingPoint)
    {
        this.startingPoint = startingPoint;
        this.endingPoint = endingPoint;
    }

    public Vector getStartingPoint()
    {
        return startingPoint;
    }

    public Vector getEndingPoint()
    {
        return endingPoint;
    }
}
