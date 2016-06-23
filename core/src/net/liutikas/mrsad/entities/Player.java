package net.liutikas.mrsad.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.Viewport;

/**
 * The main game character the the player will be controlling.
 */
public class Player {
    private final Viewport mViewport;
    private Vector2 mPosition;
    private Texture mTexture;

    public Player(Viewport viewport) {
        mViewport = viewport;
        mTexture = new Texture("player-right.png");
    }

    public void init() {
        mPosition = new Vector2(mViewport.getWorldWidth() / 2, 0);
    }

    public void update(float delta) {
    }

    public void render(SpriteBatch batch) {
        Gdx.app.log("Player", "Position " + mViewport.getWorldWidth());
        batch.draw(mTexture, mPosition.x, mPosition.y);
    }
}
