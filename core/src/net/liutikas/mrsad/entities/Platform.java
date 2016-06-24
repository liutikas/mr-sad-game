package net.liutikas.mrsad.entities;

import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

import net.liutikas.mrsad.utils.Assets;

/**
 * A simple static single platform.
 */
public class Platform {
    private float mX;
    private float mY;
    private float mWidth;
    private float mHeight;
    private NinePatch mNinePatch;

    public Platform() {
        mNinePatch = Assets.instance.platformAssets.platformBackground;
    }

    public void init(float x, float y, float width, float height) {
        mX = x;
        mY = y;
        mWidth = width;
        mHeight = height;
    }

    public void render(SpriteBatch batch) {
        mNinePatch.draw(batch, mX, mY, mWidth, mHeight);
    }
}
