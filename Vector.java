package com.mygdx.linesers;

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

    public double vectorNorm()
    {
        return Math.sqrt(x * x + y * y);
    }

    private static Vector sum(Vector vector1, Vector vector2)
    {
        return new Vector(vector1.getX() + vector2.getX(), vector1.getY() + vector2.getY());
    }

    public static double normOfSum(Vector vector1, Vector vector2)
    {
        return (sum(vector1, vector2)).vectorNorm();
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
