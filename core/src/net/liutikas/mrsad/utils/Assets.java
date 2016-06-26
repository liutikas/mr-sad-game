package net.liutikas.mrsad.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetErrorListener;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;

import net.liutikas.mrsad.Constants;

/**
 * A singleton class that will load and hold all the assets for the game.
 */
public class Assets implements Disposable, AssetErrorListener {
    private static final String TAG = "Assets";

    public static final Assets instance = new Assets();
    public PlayerAssets playerAssets;
    public PlatformAssets platformAssets;

    private AssetManager mAssetManager;

    private Assets() {}

    public void init(AssetManager assetManager) {
        mAssetManager = assetManager;
        mAssetManager.setErrorListener(this);
        assetManager.load(Constants.TEXTURE_ATLAS, TextureAtlas.class);
        assetManager.finishLoading();

        TextureAtlas atlas = assetManager.get(Constants.TEXTURE_ATLAS);
        playerAssets = new PlayerAssets(atlas);
        platformAssets = new PlatformAssets(atlas);
    }

    public static class PlayerAssets {
        public final AtlasRegion standingLeft;
        public final AtlasRegion standingRight;
        public final AtlasRegion jumpingLeft;
        public final AtlasRegion jumpingRight;

        public final Animation walkingLeftAnimation;
        public final Animation walkingRightAnimation;

        public PlayerAssets(TextureAtlas atlas) {
            standingLeft = atlas.findRegion(Constants.STANDING_LEFT);
            standingRight = atlas.findRegion(Constants.STANDING_RIGHT);
            jumpingLeft = atlas.findRegion(Constants.JUMPING_LEFT);
            jumpingRight = atlas.findRegion(Constants.JUMPING_RIGHT);

            Array<AtlasRegion> walkingRightFrames = new Array<AtlasRegion>();
            walkingRightFrames.add(atlas.findRegion(Constants.WALKING_RIGHT_2));
            walkingRightFrames.add(atlas.findRegion(Constants.WALKING_RIGHT_1));
            walkingRightFrames.add(atlas.findRegion(Constants.WALKING_RIGHT_2));
            walkingRightFrames.add(atlas.findRegion(Constants.WALKING_RIGHT_3));
            walkingRightAnimation = new Animation(
                    Constants.WALK_LOOP_DURATION,
                    walkingRightFrames,
                    Animation.PlayMode.LOOP);

            Array<AtlasRegion> walkingLeftFrames = new Array<AtlasRegion>();
            walkingLeftFrames.add(atlas.findRegion(Constants.WALKING_LEFT_2));
            walkingLeftFrames.add(atlas.findRegion(Constants.WALKING_LEFT_1));
            walkingLeftFrames.add(atlas.findRegion(Constants.WALKING_LEFT_2));
            walkingLeftFrames.add(atlas.findRegion(Constants.WALKING_LEFT_3));
            walkingLeftAnimation = new Animation(
                    Constants.WALK_LOOP_DURATION,
                    walkingLeftFrames,
                    Animation.PlayMode.LOOP);
        }
    }

    @Override
    public void error(AssetDescriptor asset, Throwable throwable) {
        Gdx.app.error(TAG, "Couldn't load asset: " + asset.fileName, throwable);
    }

    @Override
    public void dispose() {
        mAssetManager.dispose();
    }

    public static class PlatformAssets {
        public final NinePatch platformBackground;

        public PlatformAssets(TextureAtlas atlas) {
            AtlasRegion region = atlas.findRegion(Constants.PLATFORM_BACKGROUND);
            platformBackground = new NinePatch(
                    region,
                    Constants.PLATFORM_EDGE,
                    Constants.PLATFORM_EDGE,
                    Constants.PLATFORM_EDGE,
                    Constants.PLATFORM_EDGE);
        }
    }
}
