package net.liutikas.mrsad.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.TimeUtils;
import com.badlogic.gdx.utils.viewport.Viewport;

import net.liutikas.mrsad.Constants;

/**
 * The main game character the the player will be controlling.
 */
public class Player {
    private static final int FACING_LEFT = 0;
    private static final int FACING_RIGHT = 1;

    private final Viewport mViewport;
    private Vector2 mPosition;
    private Vector2 mVelocity;
    private JumpState mJumpState;
    private int mDirection;
    private long mJumpStartTime;
    private Texture mTextureRight;
    private Texture mTextureLeft;

    public Player(Viewport viewport) {
        mViewport = viewport;
        mTextureRight = new Texture("player-right.png");
        mTextureLeft = new Texture("player-left.png");
    }

    public void init() {
        mPosition = new Vector2(mViewport.getWorldWidth() / 2, 40);
        mVelocity = new Vector2(0, 0);
        mJumpState = JumpState.FALLING;
    }

    public void update(float delta) {
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            moveLeft(delta);
        } else if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            moveRight(delta);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.SPACE)) {
            if (mJumpState == JumpState.GROUNDED) {
                mJumpState = JumpState.JUMPING;
                mJumpStartTime = TimeUtils.nanoTime();
            }
            float jumpDuration = MathUtils.nanoToSec * (TimeUtils.nanoTime() - mJumpStartTime);
            if (jumpDuration < Constants.PLAYER_MAX_JUMP_DURATION
                    && mJumpState == JumpState.JUMPING) {
                mPosition.y += delta * Constants.PLAYER_JUMP_SPEED;
            } else {
                continueFalling(delta);
            }
        } else if (mPosition.y > 0) {
            continueFalling(delta);
        }
    }

    public void render(SpriteBatch batch) {
        batch.draw(
                mDirection == FACING_LEFT ? mTextureLeft : mTextureRight,
                mPosition.x, mPosition.y);
    }

    private void moveLeft(float delta) {
        mDirection = FACING_LEFT;
        mPosition.x -= delta * Constants.PLAYER_WALK_SPEED;
    }

    private void moveRight(float delta) {
        mDirection = FACING_RIGHT;
        mPosition.x += delta * Constants.PLAYER_WALK_SPEED;
    }

    private void continueFalling(float delta) {
        if (mJumpState == JumpState.JUMPING) {
            mJumpState = JumpState.FALLING;
        }
        mVelocity.y -= delta * Constants.GRAVITY_ACCELERATION;
        mPosition.y += delta * mVelocity.y;
        if (mPosition.y <= 0) {
            mPosition.y = 0f;
            mVelocity.y = 0f;
            mJumpState = JumpState.GROUNDED;
        }
    }

    enum JumpState {
        GROUNDED, // No vertical velocity. Player is on a ground.
        JUMPING,  // Positive up velocity. Can stay in this state for up to PLAYER_MAX_JUMP_DURATION.
        FALLING   // Negative vertical velocity. Changes to GROUNDED when player reaches ground.
    }
}
