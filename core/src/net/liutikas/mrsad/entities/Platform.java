package net.liutikas.mrsad.entities;

import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import net.liutikas.mrsad.utils.Assets;

/**
 * A simple static single platform.
 */
public class Platform {
    float top;
    float bottom;
    float left;
    float right;
    float width;
    float height;
    private NinePatch mNinePatch;

    public Platform() {
        mNinePatch = Assets.instance.platformAssets.platformBackground;
    }

    public void init(float left, float top, float width, float height) {
        this.top = top;
        this.left = left;
        this.bottom = top - height;
        this.right = left + width;
        this.height = height;
        this.width = width;
    }

    public void render(SpriteBatch batch) {
        mNinePatch.draw(batch, left, bottom, width, height);
    }
}
