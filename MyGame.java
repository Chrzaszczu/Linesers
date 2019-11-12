package com.mygdx.linesers;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class MyGame extends Game
{
	public static SpriteBatch batch;
	public static final Options options = Options.getInstance();
	public static final Assets myAssets = new Assets();
	
	@Override
	public void create ()
	{
		Gdx.input.setCatchBackKey(true);

		batch = new SpriteBatch();

		// i could create FitViewport viewport pass it to the batch and stage
		// it would let me use different screen units
		// probably waste of time
		//
		//camera.position.set(100 / 2, 100 / 2, 0);
		//camera.update();
		//batch.setProjectionMatrix(camera.combined);

		options.loadConfigurationFile();
		myAssets.loadAssets();
		myAssets.playMusic();

		this.setScreen(new Menu(this));
	}
}
