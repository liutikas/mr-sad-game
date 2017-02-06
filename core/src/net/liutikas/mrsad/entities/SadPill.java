package net.liutikas.mrsad.entities;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

import net.liutikas.mrsad.utils.Assets;
import net.liutikas.mrsad.utils.Utils;

public class SadPill {
    final Vector2 position;

    public SadPill(Vector2 position) {
        this.position = position;
    }

    public void render(SpriteBatch batch) {
        Utils.drawTextureRegion(batch, Assets.instance.sadPillAssets.sadPill, position.x, position.y);
    }
}
