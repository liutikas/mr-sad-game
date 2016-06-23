package net.liutikas.mrsad;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.ExtendViewport;

/**
 * The main game screen responsible for handling the game play.
 */
public class GameScreen implements Screen {
    private ExtendViewport mViewport;

    SpriteBatch batch;
    Texture img;

    @Override
    public void show() {
        batch = new SpriteBatch();
        img = new Texture("player-right.png");
        mViewport = new ExtendViewport(Constants.WORLD_SIZE, Constants.WORLD_SIZE);
    }

    @Override
    public void render(float delta) {
        mViewport.apply();
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.setProjectionMatrix(mViewport.getCamera().combined);
        batch.begin();
        batch.draw(img, 0, 0);
        batch.end();
    }

    @Override
    public void resize(int width, int height) {
        mViewport.update(width, height, true);
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
