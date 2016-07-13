package net.liutikas.mrsad;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.ExtendViewport;

import net.liutikas.mrsad.entities.Platform;
import net.liutikas.mrsad.entities.Platforms;
import net.liutikas.mrsad.entities.Player;
import net.liutikas.mrsad.utils.Assets;
import net.liutikas.mrsad.utils.FollowCamera;
import net.liutikas.mrsad.utils.GameInputProcessor;
import net.liutikas.mrsad.utils.LevelLoader;

/**
 * The main game screen responsible for handling the game play.
 */
public class GameScreen implements Screen {
    private ExtendViewport mViewport;
    private Player mPlayer;
    private Platforms mPlatforms;
    private FollowCamera mFollowCamera;
    private GameInputProcessor mInputProcessor;

    SpriteBatch batch;

    @Override
    public void show() {
        AssetManager assetManager = new AssetManager();
        Assets.instance.init(assetManager);
        batch = new SpriteBatch();
        mViewport = new ExtendViewport(Constants.WORLD_SIZE, Constants.WORLD_SIZE);
        mInputProcessor = new GameInputProcessor(mViewport);
        mPlayer = new Player(mViewport, mInputProcessor);
        mPlatforms = new Platforms(mViewport);
        mFollowCamera = new FollowCamera(mViewport.getCamera(), mPlayer);
        Gdx.input.setInputProcessor(mInputProcessor);
    }

    @Override
    public void render(float delta) {
        mPlayer.update(delta, mPlatforms.platforms);
        mFollowCamera.update();

        mViewport.apply();
        Gdx.gl.glClearColor(
                Constants.WORLD_COLOR.r,
                Constants.WORLD_COLOR.g,
                Constants.WORLD_COLOR.b, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.setProjectionMatrix(mViewport.getCamera().combined);
        batch.begin();
        mPlatforms.render(batch);
        mPlayer.render(batch);
        batch.end();
    }

    @Override
    public void resize(int width, int height) {
        mViewport.update(width, height, true);
        mInputProcessor.init();
        mPlayer.init();
        mPlatforms.init();
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
