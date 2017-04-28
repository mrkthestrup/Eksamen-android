package sorenkt.class2017.kea.space_game.Space;


import android.graphics.Bitmap;

import sorenkt.class2017.kea.space_game.GameEngine;
import sorenkt.class2017.kea.space_game.Screen;

public class GameScreen extends Screen
{
    enum State
    {
        Paused, Running, GameOver
    }
    State state = State.Running;
    Bitmap background;

    public GameScreen(GameEngine game)
    {
        super(game);
    }
    @Override
    public void update(float deltaTime)
    {

    }

    @Override
    public void pause()
    {

    }

    @Override
    public void resume()
    {

    }

    @Override
    public void dispose()
    {

    }
}
