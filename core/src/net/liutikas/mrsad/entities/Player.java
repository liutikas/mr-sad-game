package net.liutikas.mrsad.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;
import com.badlogic.gdx.utils.viewport.Viewport;

import net.liutikas.mrsad.Constants;
import net.liutikas.mrsad.utils.Assets;

/**
 * The main game character the the player will be controlling.
 */
public class Player {
    private static final int FACING_LEFT = 0;
    private static final int FACING_RIGHT = 1;

    private static final int STANDING = 0;
    private static final int WALKING = 1;

    private final Viewport mViewport;
    private Vector2 mLastFramePosition;
    // Position of the middle of player's feet.
    private Vector2 mPosition;
    private Vector2 mVelocity;
    private JumpState mJumpState;
    private int mDirection;
    private int mWalkingState;
    private long mWalkStartTime;
    private long mJumpStartTime;

    public Player(Viewport viewport) {
        mViewport = viewport;
    }

    public void init() {
        mLastFramePosition = new Vector2(0, 0);
        mPosition = new Vector2(mViewport.getWorldWidth() / 2, 40);
        mVelocity = new Vector2(0, 0);
        mJumpState = JumpState.FALLING;
        mDirection = FACING_RIGHT;
        mWalkingState = STANDING;
    }

    public void update(float delta, Array<Platform> platforms) {
        mLastFramePosition.set(mPosition);

        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            moveLeft(delta);
        } else if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            moveRight(delta);
        } else {
            mWalkingState = STANDING;
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
                continueFalling(delta, platforms);
            }
        } else if (mPosition.y > 0) {
            continueFalling(delta, platforms);
        }
    }

    public void render(SpriteBatch batch) {
        TextureRegion region;
        if (mDirection == FACING_LEFT) {
            if (mJumpState == JumpState.GROUNDED) {
                if (mWalkingState == STANDING) {
                    region = Assets.instance.playerAssets.standingLeft;
                } else {
                    float walkTimeSeconds =
                            MathUtils.nanoToSec * (TimeUtils.nanoTime() - mWalkStartTime);
                    region = Assets.instance.playerAssets.walkingLeftAnimation.getKeyFrame(
                            walkTimeSeconds);
                }
            } else {
                region = Assets.instance.playerAssets.jumpingLeft;
            }
        } else {
            if (mJumpState == JumpState.GROUNDED) {
                if (mWalkingState == STANDING) {
                    region = Assets.instance.playerAssets.standingRight;
                } else {
                    float walkTimeSeconds =
                            MathUtils.nanoToSec * (TimeUtils.nanoTime() - mWalkStartTime);
                    region = Assets.instance.playerAssets.walkingRightAnimation.getKeyFrame(
                            walkTimeSeconds);
                }
            } else {
                region = Assets.instance.playerAssets.jumpingRight;
            }
        }

        batch.draw(
                region.getTexture(),
                mPosition.x - Constants.PLAYER_TEXTURE_WIDTH / 2,
                mPosition.y,
                0,
                0,
                region.getRegionWidth(),
                region.getRegionHeight(),
                1,
                1,
                0,
                region.getRegionX(),
                region.getRegionY(),
                region.getRegionWidth(),
                region.getRegionHeight(),
                false,
                false);
    }

    public float getPositionX() {
        return mPosition.x;
    }

    public float getPositionY() {
        return mPosition.y;
    }

    private void moveLeft(float delta) {
        mDirection = FACING_LEFT;
        if (mJumpState == JumpState.GROUNDED && mWalkingState == STANDING) {
            mWalkStartTime = TimeUtils.nanoTime();
            mWalkingState = WALKING;
        }
        mPosition.x -= delta * Constants.PLAYER_WALK_SPEED;
    }

    private void moveRight(float delta) {
        mDirection = FACING_RIGHT;
        if (mJumpState == JumpState.GROUNDED && mWalkingState == STANDING) {
            mWalkStartTime = TimeUtils.nanoTime();
            mWalkingState = WALKING;
        }
        mPosition.x += delta * Constants.PLAYER_WALK_SPEED;
    }

    private void continueFalling(float delta, Array<Platform> platforms) {
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
        for (int i = 0; i < platforms.size; i++) {
            Platform platform = platforms.get(i);
            if (mLastFramePosition.y >= platform.top && mPosition.y <= platform.top) {
                float leftFoot = mPosition.x - Constants.PLAYER_FEET_WIDTH / 2;
                float rightFoot = mPosition.x + Constants.PLAYER_FEET_WIDTH / 2;
                boolean leftFootIn = leftFoot > platform.left && leftFoot < platform.right;
                boolean rightFootIn = rightFoot > platform.left && rightFoot < platform.right;
                if (leftFootIn || rightFootIn) {
                    mPosition.y = platform.top;
                    mVelocity.y = 0f;
                    mJumpState = JumpState.GROUNDED;
                    break;
                }
            }
        }
    }

    enum JumpState {
        GROUNDED, // No vertical velocity. Player is on a ground.
        JUMPING,  // Positive up velocity. Can stay in this state for up to PLAYER_MAX_JUMP_DURATION.
        FALLING   // Negative vertical velocity. Changes to GROUNDED when player reaches ground.
    }
}
