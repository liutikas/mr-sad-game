package net.liutikas.mrsad.utils;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.NinePatch;

import net.liutikas.mrsad.Constants;

/**
 * A singleton class that will load and hold all the assets for the game.
 */
public class Assets {
    public static final Assets instance = new Assets();
    public PlayerAssets playerAssets;
    public PlatformAssets platformAssets;

    private Assets() {}

    public void init() {
        playerAssets = new PlayerAssets();
        platformAssets = new PlatformAssets();
    }

    public static class PlayerAssets {
        public final Texture standingLeft;
        public final Texture standingRight;

        public PlayerAssets() {
            standingLeft = new Texture(Constants.STANDING_LEFT);
            standingRight = new Texture(Constants.STANDING_RIGHT);
        }
    }

    public static class PlatformAssets {
        public final NinePatch platformBackground;

        public PlatformAssets() {
            Texture texture = new Texture(Constants.PLATFORM_BACKGROUND);
            platformBackground = new NinePatch(
                    texture,
                    Constants.PLATFORM_EDGE,
                    Constants.PLATFORM_EDGE,
                    Constants.PLATFORM_EDGE,
                    Constants.PLATFORM_EDGE);
        }
    }
}
