package com.patryk.main;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class MainGame implements Screen
{
    private final static int TOUCH_COOLDOWN = 250;

    private final MyGame myGame;
    private Stage myStage;

    private ImageButton returnButton;
    private Texture background;

    private Lattice squareLattice;
    private ConnectionChecker connectionChecker;
    private GameState gameState;

    private long lastTouch = 0;
    private int levelNumber;

    public MainGame (MyGame myGame, int levelNumber)
    {
        this.myGame = myGame;
        this.levelNumber = levelNumber;
    }

    @Override
    public void show()
    {
        returnButton = new ImageButton(new TextureRegionDrawable(
                new TextureRegion(MyGame.myAssets.getTexture(Assets.START_BUTTON))));

        if(background == null)
        {
            background = MyGame.myAssets.getTexture(Assets.BACKGROUND_AQUA, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        }

        squareLattice = new Lattice();
        squareLattice.setLattice(1);

        connectionChecker = new ConnectionChecker(squareLattice.getSquareTiles(), squareLattice.getStartingTile());

        gameState = new GameState();

        myStage = new Stage(new ScreenViewport());

        returnButton.setPosition(50,50); // ZAMIENIC NA POLOZENIE I WYMIAR ZALEZNY OD ROZMIAROW EKRANU
        returnButton.setSize(200,100);

        squareLattice.addActors(myStage);
        myStage.addActor(returnButton);
        Gdx.input.setInputProcessor(myStage);

        connectionChecker.checkConnections();
    }

    @Override
    public void render(float delta)
    {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        if(Gdx.input.isTouched())
        {
            if(System.currentTimeMillis() - lastTouch > TOUCH_COOLDOWN)
            {
                lastTouch = System.currentTimeMillis();

                squareLattice.onLatticeTouch();
                connectionChecker.checkConnections();
                gameState.setFinished(squareLattice.isFinished());
            }
        }

        returnButton.addListener(new ClickListener()
        {
            @Override
            public void clicked(InputEvent event, float x, float y)
            {
                dispose();
                myGame.setScreen(new Menu(myGame));
            }
        });

        MyGame.batch.enableBlending();
        MyGame.batch.begin();
        MyGame.batch.draw(background, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        MyGame.batch.end();

        myStage.act(Gdx.graphics.getDeltaTime());
        myStage.draw();
    }

    @Override
    public void resize(int width, int height)
    {
    }

    @Override
    public void pause()
    {
    }

    @Override
    public void resume()
    {
    }

    @Override
    public void hide()
    {
    }

    @Override
    public void dispose()
    {
        myGame.dispose();
        myStage.dispose();

        squareLattice = null;
        returnButton = null;
    }
}