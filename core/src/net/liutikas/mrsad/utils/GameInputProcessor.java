package net.liutikas.mrsad.utils;

import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.utils.viewport.Viewport;

/**
 * Receives all input events to determine whether a player should be walking and jumping.
 */
public class GameInputProcessor implements InputProcessor {
    private final Viewport mViewport;
    private float mDelta;
    private int mTouchDownX = 0;
    private int mTouchDownY = 0;
    private int mLatestDeltaX;
    private int mLatestDeltaY;

    public GameInputProcessor(Viewport viewport) {
        mViewport = viewport;
    }

    public void init() {
        mDelta = mViewport.getScreenWidth() / 20;
    }

    public boolean shouldWalk() {
        return Math.abs(mLatestDeltaX) > mDelta;
    }

    public boolean shouldWalkLeft() {
        return mLatestDeltaX > mDelta;
    }

    public boolean shouldJump() {
        return Math.abs(mLatestDeltaY) > mDelta;
    }

    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        mTouchDownX = screenX;
        mTouchDownY = screenY;
        return true;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        mTouchDownX = 0;
        mTouchDownY = 0;
        mLatestDeltaX = 0;
        mLatestDeltaY = 0;
        return true;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        mLatestDeltaX = mTouchDownX - screenX;
        mLatestDeltaY = mTouchDownY - screenY;
        return true;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }
}
