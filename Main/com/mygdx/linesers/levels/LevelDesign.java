package Main.com.mygdx.linesers.levels;

import Main.com.mygdx.linesers.MyGame;
import Main.com.mygdx.linesers.levels.timetrial.RandomLevelGenerator;
import Main.com.mygdx.linesers.tiles.SquareTile;
import Main.com.mygdx.linesers.tiles.TileFactory;
import Main.com.mygdx.linesers.tiles.TileType;
import org.json.JSONArray;
import java.util.ArrayList;
import java.util.List;

public class LevelDesign
{
    private static RandomLevelGenerator randomLevelGenerator = new RandomLevelGenerator();

    public static List<List<SquareTile>> getLevel(int levelNumber)
    {
        List<List<SquareTile>> squareTiles = new ArrayList<List<SquareTile>>();
        JSONArray mapDesign = MyGame.myAssets.parseJSONFile("Maps/" + levelNumber + ".json");

        if(mapDesign != null)
        {
            for(int y = 0; y < mapDesign.length(); y++)
            {
                List<SquareTile> temporary = new ArrayList();
                for(int x = 0; x < mapDesign.getJSONArray(y).length(); x++)
                {
                    int type = (int)mapDesign.getJSONArray(y).getJSONArray(x).get(0);
                    int rotation = -(int)mapDesign.getJSONArray(y).getJSONArray(x).get(1);
                    temporary.add(TileFactory.getTile(TileType.getTileTypeOfValue(type), rotation));
                }
                squareTiles.add(temporary);
            }
        }

        return squareTiles;
    }

    public static List<List<SquareTile>> getRandomLevel()
    {
        randomLevelGenerator.clear();
        return randomLevelGenerator.generateRandomLevel();
    }
}