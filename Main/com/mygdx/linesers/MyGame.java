package Main.com.mygdx.linesers;

import Main.com.mygdx.linesers.assets.Assets;
import Main.com.mygdx.linesers.assets.Options;
import Main.com.mygdx.linesers.screens.Menu;
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

		options.loadConfigurationFile();
		myAssets.loadAssets();
		myAssets.playMusic();

		this.setScreen(new Menu(this));
	}
}
