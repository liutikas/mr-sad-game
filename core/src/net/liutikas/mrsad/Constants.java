package net.liutikas.mrsad;

import com.badlogic.gdx.graphics.Color;

public class Constants {
    // World constants
    public static final float WORLD_SIZE = 128f;
    public static final Color WORLD_COLOR = Color.SKY;

    // Player constants
    public static final float PLAYER_WALK_SPEED = 80f;
    public static final float PLAYER_JUMP_SPEED = 120f;
    public static final float PLAYER_MAX_JUMP_DURATION = 0.3f;
    public static final float PLAYER_TEXTURE_WIDTH = 32f;
    public static final float PLAYER_FEET_WIDTH = 15f;
    public static final float GRAVITY_ACCELERATION = 500f;

    // Assets constants
    public static final String TEXTURE_ATLAS = "images/mrsad.pack.atlas";
    public static final String PLATFORM_BACKGROUND = "platform";
    public static final int PLATFORM_EDGE = 10;
    public static final String STANDING_LEFT = "player-left";
    public static final String STANDING_RIGHT = "player-right";
    public static final String JUMPING_LEFT = "player-jumping-left";
    public static final String JUMPING_RIGHT = "player-jumping-right";
    public static final float WALK_LOOP_DURATION = 0.15f;
    public static final String WALKING_RIGHT_1 = "player-walking-right-1";
    public static final String WALKING_RIGHT_2 = "player-walking-right-2";
    public static final String WALKING_RIGHT_3 = "player-walking-right-3";
    public static final String WALKING_LEFT_1 = "player-walking-left-1";
    public static final String WALKING_LEFT_2 = "player-walking-left-2";
    public static final String WALKING_LEFT_3 = "player-walking-left-3";
}
