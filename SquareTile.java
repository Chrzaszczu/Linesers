package com.patryk.main;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import java.util.LinkedList;

enum TileType {ONE_LINE, TWO_LINES, THREE_LINES, FOUR_LINES, HALF_LINE, STARTING_TILE, FINAL_TILE}

public class SquareTile
{
    public static final int ROTATION_ANGLE_STEP = -90;

    private LinkedList<Vector> linesDirection = new LinkedList<Vector>();
    private ImageButton imageButton;
    private TileType tileType;

    private int rotationAngle;
    private boolean glowing = false;

    public boolean isGlowing()
    {
        return glowing;
    }

    public void setGlowing(boolean glowing)
    {
        this.glowing = glowing;
        updateImage(glowing);
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

    public LinkedList<Vector> getLinesDirection()
    {
        return linesDirection;
    }

    public TileType getTileType()
    {
        return tileType;
    }

    public ImageButton getImageButton()
    {
        return imageButton;
    }

    public void setSquareTile(Vector position, float size)
    {
        switch(tileType)
        {
            case ONE_LINE:
                linesDirection.add(new Vector(0,1));
                linesDirection.add(new Vector(0,-1));
                imageButton = new ImageButton(
                        new TextureRegionDrawable(new TextureRegion(MyGame.myAssets.get("tmp1.png", Texture.class))));
                break;

            case TWO_LINES:
                linesDirection.add(new Vector(0,1));
                linesDirection.add(new Vector(1,0));
                imageButton = new ImageButton(
                        new TextureRegionDrawable(new TextureRegion(MyGame.myAssets.get("tmp2.png", Texture.class))));
                break;

            case THREE_LINES:
                linesDirection.add(new Vector(0,1));
                linesDirection.add(new Vector(1,0));
                linesDirection.add(new Vector(-1,0));
                imageButton = new ImageButton(
                        new TextureRegionDrawable(new TextureRegion(MyGame.myAssets.get("tmp3.png", Texture.class))));
                break;

            case FOUR_LINES:
                linesDirection.add(new Vector(0,1));
                linesDirection.add(new Vector(1,0));
                linesDirection.add(new Vector(0,-1));
                linesDirection.add(new Vector(-1,0));
                imageButton = new ImageButton(
                        new TextureRegionDrawable(new TextureRegion(MyGame.myAssets.get("tmp4.png", Texture.class))));
                break;

            case HALF_LINE:
                linesDirection.add(new Vector(0,1));
                imageButton = new ImageButton(
                        new TextureRegionDrawable(new TextureRegion(MyGame.myAssets.get("tmp5.png", Texture.class))));
                break;

            case STARTING_TILE:
                linesDirection.add(new Vector(0,1));
                imageButton = new ImageButton(
                        new TextureRegionDrawable(new TextureRegion(MyGame.myAssets.get("tmps.png", Texture.class))));
                imageButton.setTouchable(Touchable.disabled);
                break;

            case FINAL_TILE:
                linesDirection.add(new Vector(0,1));
                imageButton = new ImageButton(
                        new TextureRegionDrawable(new TextureRegion(MyGame.myAssets.get("tmpk.png", Texture.class))));
                imageButton.setTouchable(Touchable.disabled);
                break;
        }

        imageButton.setTransform(true);
        imageButton.setSize(size, size);
        imageButton.setPosition(position.getX(), position.getY());
        imageButton.setOrigin(size/2, size/2);
        imageButton.setRotation(rotationAngle);

        for(Vector vector: linesDirection)
        {
            vector.rotateVector(rotationAngle);
        }
    }

    private void updateImage(boolean glowing)
    {
        if(glowing)
        {
            switch(tileType)
            {
                case ONE_LINE:
                    imageButton.getStyle().imageUp =
                            new TextureRegionDrawable(new TextureRegion(MyGame.myAssets.get("tmp1g.png", Texture.class)));
                    break;

                case TWO_LINES:
                    imageButton.getStyle().imageUp =
                            new TextureRegionDrawable(new TextureRegion(MyGame.myAssets.get("tmp2g.png", Texture.class)));
                    break;

                case THREE_LINES:
                    imageButton.getStyle().imageUp =
                            new TextureRegionDrawable(new TextureRegion(MyGame.myAssets.get("tmp3g.png", Texture.class)));
                    break;

                case FOUR_LINES:
                    imageButton.getStyle().imageUp =
                            new TextureRegionDrawable(new TextureRegion(MyGame.myAssets.get("tmp4g.png", Texture.class)));
                    break;

                case HALF_LINE:
                    imageButton.getStyle().imageUp =
                            new TextureRegionDrawable(new TextureRegion(MyGame.myAssets.get("tmp5g.png", Texture.class)));
                    break;

                case STARTING_TILE:
                    imageButton.getStyle().imageUp =
                            new TextureRegionDrawable(new TextureRegion(MyGame.myAssets.get("tmps.png", Texture.class)));
                    break;

                case FINAL_TILE:
                    imageButton.getStyle().imageUp =
                            new TextureRegionDrawable(new TextureRegion(MyGame.myAssets.get("tmpkg.png", Texture.class)));
                    break;
            }
        }
        else
        {
            switch(tileType)
            {
                case ONE_LINE:
                    imageButton.getStyle().imageUp =
                            new TextureRegionDrawable(new TextureRegion(MyGame.myAssets.get("tmp1.png", Texture.class)));
                    break;

                case TWO_LINES:
                    imageButton.getStyle().imageUp =
                            new TextureRegionDrawable(new TextureRegion(MyGame.myAssets.get("tmp2.png", Texture.class)));
                    break;

                case THREE_LINES:
                    imageButton.getStyle().imageUp =
                            new TextureRegionDrawable(new TextureRegion(MyGame.myAssets.get("tmp3.png", Texture.class)));
                    break;

                case FOUR_LINES:
                    imageButton.getStyle().imageUp =
                            new TextureRegionDrawable(new TextureRegion(MyGame.myAssets.get("tmp4.png", Texture.class)));
                    break;

                case HALF_LINE:
                    imageButton.getStyle().imageUp =
                            new TextureRegionDrawable(new TextureRegion(MyGame.myAssets.get("tmp5.png", Texture.class)));
                    break;

                case STARTING_TILE:
                    imageButton.getStyle().imageUp =
                            new TextureRegionDrawable(new TextureRegion(MyGame.myAssets.get("tmps.png", Texture.class)));
                    break;

                case FINAL_TILE:
                    imageButton.getStyle().imageUp =
                            new TextureRegionDrawable(new TextureRegion(MyGame.myAssets.get("tmpk.png", Texture.class)));
                    break;
            }
        }
    }

    public SquareTile(TileType tileType, int rotationAngle)
    {
        this.rotationAngle = rotationAngle;
        this.tileType = tileType;
    }
}
