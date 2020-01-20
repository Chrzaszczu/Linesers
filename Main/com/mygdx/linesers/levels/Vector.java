package Main.com.mygdx.linesers.levels;

import java.util.*;
import java.util.stream.Collectors;

public class Vector
{
    private int x;
    private int y;

    public Vector()
    {
    }

    public Vector(int x, int y)
    {
        this.x = x;
        this.y = y;
    }

    public int getX()
    {
        return x;
    }

    public int getY()
    {
        return y;
    }

    public void setX(int x)
    {
        this.x = x;
    }

    public void setY(int y)
    {
        this.y = y;
    }

    public double vectorNorm()
    {
        return Math.sqrt(x * x + y * y);
    }

    public static double vectorNorm(Vector point1, Vector point2)
    {
        double x = point1.getX() - point2.getX();
        double y = point1.getY() - point2.getY();

        return Math.sqrt(x * x + y * y);
    }

    private static Vector sum(Vector point1, Vector point2)
    {
        return new Vector(point1.getX() + point2.getX(), point1.getY() + point2.getY());
    }

    public static double normOfSum(Vector point1, Vector point2)
    {
        return (sum(point1, point2)).vectorNorm();
    }

    public static boolean isSamePosition(Vector point1, Vector point2)
    {
        if(point1 == null || point2 == null)
        {
            return false;
        }

        return (point1.getX() == point2.getX() && point1.getY() == point2.getY());
    }

    public static boolean isSamePosition(Vector point1, List<Vector> points2)
    {
        for(Vector point2: points2)
        {
            if(point1 != null && point2 != null)
            {
                if (isSamePosition(point1, point2))
                {
                    return true;
                }
            }
        }

        return false;
    }

    public static boolean isSamePosition(List<Vector> points1, List<Vector> points2)
    {
        for(Vector point2: points2)
        {
            for(Vector point1: points1)
            {
                if(point1 != null && point2 != null)
                {
                    if (Vector.isSamePosition(point1, point2))
                    {
                        return true;
                    }
                }
            }
        }

        return false;
    }

    public List<Vector> getNearestPoints()
    {
        List<Vector> nearestTiles = new ArrayList<>();
        List<Vector> directions = new ArrayList<>(Arrays.asList(new Vector(1, 0), new Vector(-1, 0), new Vector(0, 1), new Vector(0, -1)));

        for(Vector dir: directions)
        {
            nearestTiles.add(new Vector(this.getX() + dir.getX(), this.getY() + dir.getY()));
        }

        return nearestTiles;
    }

    public static List<Vector> getSamePositions(List<Vector> points1, List<Vector> points2)
    {
        return points1.stream().filter(p1 -> isSamePosition(p1, points2)).collect(Collectors.toList());
    }

    public void rotateVector(int rotationAngle)
    {
        int temporaryX = 0;
        int temporaryY = 0;

        switch(rotationAngle)
        {
            case 0:
                temporaryX = this.x;
                temporaryY = this.y;
                break;

            case -90:
                temporaryX = this.y;
                temporaryY = -this.x;
                break;

            case -180:
                temporaryX = -this.x;
                temporaryY = -this.y;
                break;

            case -270:
                temporaryX = -this.y;
                temporaryY = this.x;
                break;
        }

        this.x = temporaryX;
        this.y = temporaryY;
    }
}
