package net.liutikas.mrsad.entities;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.Viewport;

/**
 * Responsible for drawing all the individual platforms on the screen.
 */
public class Platforms {
    public Array<Platform> platforms;
    private Viewport mViewport;

    public Platforms(Viewport viewport) {
        mViewport = viewport;
    }

    public void init() {
        platforms = new Array<Platform>();
        Platform platform0 = new Platform();
        platform0.init(0, 0, mViewport.getWorldWidth(), mViewport.getWorldHeight() / 6);
        platforms.add(platform0);
        Platform platform1 = new Platform();
        platform1.init(mViewport.getWorldWidth() / 4, mViewport.getWorldHeight() / 6,
                mViewport.getWorldWidth() / 4, mViewport.getWorldHeight() / 6);
        platforms.add(platform1);
        Platform platform2 = new Platform();
        platform2.init(mViewport.getWorldWidth() / 2, mViewport.getWorldHeight() / 3,
                mViewport.getWorldWidth() / 4, mViewport.getWorldHeight() / 6);
        platforms.add(platform2);
        Platform platform3 = new Platform();
        platform3.init(3 * mViewport.getWorldWidth() / 4, mViewport.getWorldHeight() / 2,
                mViewport.getWorldWidth() / 4, mViewport.getWorldHeight() / 6);
        platforms.add(platform3);
        Platform platform4 = new Platform();
        platform4.init(5 * mViewport.getWorldWidth() / 4, mViewport.getWorldHeight() / 2,
                mViewport.getWorldWidth() / 4, mViewport.getWorldHeight() / 6);
        platforms.add(platform4);
    }

    public void render(SpriteBatch batch) {
        for (int i = 0; i < platforms.size; i++) {
            platforms.get(i).render(batch);
        }
    }
}
