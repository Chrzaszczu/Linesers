package com.patryk.main;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;


import java.util.LinkedList;

public class SquareTile
{
    public static final int ROTATION_ANGLE_STEP = -90;
    public static final int NUMBER_OF_COLUMNS = 6;
    public static final int NUMBER_OF_ROWS = 1;
    public static final float FRAME_DURATION = 0.05f;

    protected Animation<TextureRegion> tileAnimation;

    protected LinkedList<Vector> linesDirection = new LinkedList<Vector>();
    protected ImageButton imageButton;

    private boolean glowing = false;
    private int rotationAngle;

    public SquareTile()
    {
    }

    public void initializeTile(Vector position, float size)
    {
    }

    public void updateImage(float stateTime)
    {
    }

    public boolean isGlowing()
    {
        return glowing;
    }

    public void setGlowing(boolean glowing)
    {
        this.glowing = glowing;
    }

    public void setRotationAngle(int rotationAngle)
    {
        this.rotationAngle = rotationAngle;
    }

    public int getRotationAngle()
    {
        return this.rotationAngle;
    }

    public LinkedList<Vector> getLinesDirection()
    {
        return linesDirection;
    }

    public ImageButton getImageButton()
    {
        return imageButton;
    }

    protected TextureRegion getAnimationFrame(float stateTime)
    {
        return this.tileAnimation.getKeyFrame(stateTime, true);
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

