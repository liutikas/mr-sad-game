package net.liutikas.mrsad.utils;

import com.badlogic.gdx.graphics.Camera;

import net.liutikas.mrsad.entities.Player;

/**
 * Utility class that allows camera to follow the player's position.
 */
public class FollowCamera {
    private final Camera mCamera;
    private final Player mPlayer;

    public FollowCamera(Camera camera, Player player) {
        mCamera = camera;
        mPlayer = player;
    }

    public void update() {
        mCamera.position.x = mPlayer.getPositionX();
        mCamera.position.y = mPlayer.getPositionY();
    }
}
