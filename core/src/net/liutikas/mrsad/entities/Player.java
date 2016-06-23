package net.liutikas.mrsad.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.Viewport;

import net.liutikas.mrsad.Constants;

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
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            mPosition.x -= delta * Constants.PLAYER_WALK_SPEED;
        } else if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            mPosition.x += delta * Constants.PLAYER_WALK_SPEED;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.SPACE)) {
            mPosition.y += delta * Constants.PLAYER_JUMP_SPEED;
        } else if (mPosition.y > 0) {
            mPosition.y -= delta * 80;
        }
    }

    public void render(SpriteBatch batch) {
        batch.draw(mTexture, mPosition.x, mPosition.y);
    }
}
