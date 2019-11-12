package com.mygdx.linesers;

import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;

import java.util.LinkedList;

public class SquareTile
{
    public static final int ROTATION_ANGLE_STEP = -90;

    private LinkedList<Vector> linesDirection = new LinkedList<Vector>();
    private ImageButton imageButton;

    private boolean glowing = false;
    private int rotationAngle;

    public SquareTile()
    {
    }

    public void initializeTile(Vector position, float size)
    {
    }

    public boolean isGlowing()
    {
        return glowing;
    }

    public void playSound()
    {
        MyGame.myAssets.playSound(Assets.ROTATE_LASER_SOUND);
    }

    public void setGlowing(boolean glowing)
    {
        this.glowing = glowing;
    }

    public LinkedList<Vector> getLinesDirection()
    {
        return linesDirection;
    }

    public ImageButton getImageButton()
    {
        return imageButton;
    }

    protected void setRotationAngle(int rotationAngle)
    {
        this.rotationAngle = rotationAngle;
    }

    protected int getRotationAngle()
    {
        return this.rotationAngle;
    }

    protected void initializeImageButton(ImageButton imageButton, Vector position, float size)
    {
        this.imageButton = imageButton;
        this.imageButton.setTransform(true);
        this.imageButton.setSize(size, size);
        this.imageButton.setPosition(position.getX(), position.getY());
        this.imageButton.setOrigin(size/2, size/2);
        this.imageButton.setRotation(getRotationAngle());
    }

    public int rotateTile(int rotationAngle)
    {
        this.rotationAngle += rotationAngle;
        if(this.rotationAngle == -360)
        {
            this.rotationAngle = 0;
        }

        for(Vector lines: linesDirection)
        {
            lines.rotateVector(rotationAngle);
        }

        return this.rotationAngle;
    }
}

