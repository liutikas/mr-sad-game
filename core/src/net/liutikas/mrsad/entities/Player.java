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
import net.liutikas.mrsad.utils.GameInputProcessor;
import net.liutikas.mrsad.utils.Utils;

/**
 * The main game character the the player will be controlling.
 */
public class Player {
    private static final int FACING_LEFT = 0;
    private static final int FACING_RIGHT = 1;

    private static final int STANDING = 0;
    private static final int WALKING = 1;

    private final Viewport mViewport;
    private final GameInputProcessor mInputProcessor;

    private Vector2 mLastFramePosition;
    // Position of the middle of player's feet.
    private Vector2 mPosition;
    private Vector2 mVelocity;
    private JumpState mJumpState;
    private int mDirection;
    private int mWalkingState;
    private long mWalkStartTime;
    private long mJumpStartTime;

    public Player(Viewport viewport, GameInputProcessor inputProcessor) {
        mViewport = viewport;
        mInputProcessor = inputProcessor;
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
        boolean jumping = false;
        if (Gdx.input.isTouched()) {
            if (mInputProcessor.shouldWalk()) {
                if (mInputProcessor.shouldWalkLeft()) {
                    moveLeft(delta);
                } else {
                    moveRight(delta);
                }
            } else {
                mWalkingState = STANDING;
            }
            if (mInputProcessor.shouldJump()) {
                jumping = true;
            }
            /*
            boolean walked = false;
            for (int i = 0; i < 20; i++) {
                if (!Gdx.input.isTouched(i)) continue;
                if (Gdx.input.getY(i) < mViewport.getScreenHeight() / 2) {
                    jumping = true;
                } else {
                    if (!walked) {
                        if (Gdx.input.getX(i) < mViewport.getScreenWidth() / 2) {
                            moveLeft(delta);
                            walked = true;
                        } else {
                            moveRight(delta);
                            walked = true;
                        }
                    }
                }
            }
            */
        } else if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            moveLeft(delta);
        } else if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            moveRight(delta);
        } else {
            mWalkingState = STANDING;
        }
        if (jumping || Gdx.input.isKeyPressed(Input.Keys.SPACE)) {
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
        } else {
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
                    region = (TextureRegion) Assets.instance.playerAssets.walkingLeftAnimation.getKeyFrame(
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
                    region = (TextureRegion) Assets.instance.playerAssets.walkingRightAnimation.getKeyFrame(
                            walkTimeSeconds);
                }
            } else {
                region = Assets.instance.playerAssets.jumpingRight;
            }
        }

        Utils.drawTextureRegion(batch, region, mPosition.x - Constants.PLAYER_TEXTURE_WIDTH / 2, mPosition.y);
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
        mJumpState = JumpState.FALLING;
        mVelocity.y -= delta * Constants.GRAVITY_ACCELERATION;
        mPosition.y += delta * mVelocity.y;
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
        if (mPosition.y < Constants.WORLD_DEATH_PLANE) {
            init();
        }
    }

    enum JumpState {
        GROUNDED, // No vertical velocity. Player is on a ground.
        JUMPING,  // Positive up velocity. Can stay in this state for up to PLAYER_MAX_JUMP_DURATION.
        FALLING   // Negative vertical velocity. Changes to GROUNDED when player reaches ground.
    }
}
