package net.liutikas.mrsad.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Array;

import net.liutikas.mrsad.Constants;
import net.liutikas.mrsad.entities.Platform;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

/**
 * Class responsible for loading the level JSON data an creating all the needed entities for that
 * level.
 */
public class LevelLoader {
    private static final String TAG = "LevelLoader";

    public static void load(String path, Array<Platform> platformArray) {
        FileHandle file = Gdx.files.internal(path);

        JSONParser parser = new JSONParser();
        JSONObject rootJsonObject;

        try {
            rootJsonObject = (JSONObject) parser.parse(file.reader());
            JSONObject composite = (JSONObject) rootJsonObject.get(Constants.LEVEL_COMPOSITE);
            JSONArray platforms = (JSONArray) composite.get(Constants.LEVEL_9PATCHES);
            loadPlatforms(platforms, platformArray);
        } catch (Exception ex) {
            Gdx.app.log(TAG, ex.getMessage());
            Gdx.app.log(TAG, Constants.LEVEL_ERROR_MESSAGE);
        }
    }

    private static void loadPlatforms(JSONArray array, Array<Platform> platformArray) {
        for (Object object : array) {
            final JSONObject entry = (JSONObject) object;
            final Platform platform = new Platform();
            final float x = extractFloat(entry, Constants.LEVEL_X_KEY);
            final float y = extractFloat(entry, Constants.LEVEL_Y_KEY);
            final float width = extractFloat(entry, Constants.LEVEL_WIDTH_KEY);
            final float height = extractFloat(entry, Constants.LEVEL_HEIGHT_KEY);
            platform.init(x, y + height, width, height);
            platformArray.add(platform);
        }
    }

    private static float extractFloat(JSONObject object, String key) {
        Number number = (Number) object.get(key);
        return (number == null) ? 0 : number.floatValue();
    }
}
