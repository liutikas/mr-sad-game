package net.liutikas.mrsad;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.ExtendViewport;

import net.liutikas.mrsad.entities.Player;

/**
 * The main game screen responsible for handling the game play.
 */
public class GameScreen implements Screen {
    private ExtendViewport mViewport;
    private Player mPlayer;

    SpriteBatch batch;

    @Override
    public void show() {
        batch = new SpriteBatch();
        mViewport = new ExtendViewport(Constants.WORLD_SIZE, Constants.WORLD_SIZE);
        mPlayer = new Player(mViewport);
    }

    @Override
    public void render(float delta) {
        mPlayer.update(delta);

        mViewport.apply();
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.setProjectionMatrix(mViewport.getCamera().combined);
        batch.begin();
        mPlayer.render(batch);
        batch.end();
    }

    @Override
    public void resize(int width, int height) {
        mViewport.update(width, height, true);
        mPlayer.init();
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {
        batch.dispose();
    }

    @Override
    public void dispose() {
    }
}
