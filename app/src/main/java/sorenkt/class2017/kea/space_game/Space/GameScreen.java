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
    World world;
    WorldRender worldrender;

    public GameScreen(GameEngine game)
    {
        super(game);
        background = game.loadBitmap("Space-Background-1.jpg");
        world = new World(game);
        worldrender = new WorldRender(game, world);
    }
    @Override
    public void update(float deltaTime)
    {

        game.drawBitmap(background, 0, 0);                                                          //setting the background to the screen

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
