package net.liutikas.mrsad;

import com.badlogic.gdx.Game;

public class MrSadGame extends Game {
    @Override
    public void create() {
        setScreen(new GameScreen());
    }
}
