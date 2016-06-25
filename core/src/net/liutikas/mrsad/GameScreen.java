package net.liutikas.mrsad;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.ExtendViewport;

import net.liutikas.mrsad.entities.Platform;
import net.liutikas.mrsad.entities.Player;
import net.liutikas.mrsad.utils.Assets;
import net.liutikas.mrsad.utils.FollowCamera;

/**
 * The main game screen responsible for handling the game play.
 */
public class GameScreen implements Screen {
    private ExtendViewport mViewport;
    private Player mPlayer;
    private Platform mPlatform;
    private FollowCamera mFollowCamera;

    SpriteBatch batch;

    @Override
    public void show() {
        AssetManager assetManager = new AssetManager();
        Assets.instance.init(assetManager);
        batch = new SpriteBatch();
        mViewport = new ExtendViewport(Constants.WORLD_SIZE, Constants.WORLD_SIZE);
        mPlayer = new Player(mViewport);
        mPlatform = new Platform();
        mFollowCamera = new FollowCamera(mViewport.getCamera(), mPlayer);
    }

    @Override
    public void render(float delta) {
        mPlayer.update(delta);
        mFollowCamera.update();

        mViewport.apply();
        Gdx.gl.glClearColor(
                Constants.WORLD_COLOR.r,
                Constants.WORLD_COLOR.g,
                Constants.WORLD_COLOR.b, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.setProjectionMatrix(mViewport.getCamera().combined);
        batch.begin();
        mPlatform.render(batch);
        mPlayer.render(batch);
        batch.end();
    }

    @Override
    public void resize(int width, int height) {
        mViewport.update(width, height, true);
        mPlayer.init();
        mPlatform.init(0, 0, mViewport.getWorldWidth(), mViewport.getWorldHeight() / 4);
    }

    @Override
    public void pause() {}

    @Override
    public void resume() {}

    @Override
    public void hide() {}

    @Override
    public void dispose() {
        Assets.instance.dispose();
        batch.dispose();
    }
}
