package net.liutikas.mrsad.entities;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.Viewport;

import net.liutikas.mrsad.Constants;
import net.liutikas.mrsad.utils.LevelLoader;

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
        LevelLoader.load(Constants.LEVEL_1, platforms);
    }

    public void render(SpriteBatch batch) {
        for (int i = 0; i < platforms.size; i++) {
            platforms.get(i).render(batch);
        }
    }
}
