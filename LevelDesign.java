package com.mygdx.linesers;

import org.json.JSONArray;
import java.util.ArrayList;
import java.util.List;

public class LevelDesign
{
    public static List<List<SquareTile>> setLevel(int levelNumber)
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
                    temporary.add(TileFactory.getTile(type, rotation));
                }
                squareTiles.add(temporary);
            }
        }

        return squareTiles;
    }
}
