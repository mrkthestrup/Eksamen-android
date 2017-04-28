package sorenkt.class2017.kea.space_game.Space;

import android.graphics.Bitmap;

import sorenkt.class2017.kea.space_game.GameEngine;
import sorenkt.class2017.kea.space_game.Screen;

public class MainMenuScreen extends Screen
{
    Bitmap background;
    Bitmap startgame;
    float passedTime = 0;

    public MainMenuScreen(GameEngine game)
    {
        super(game);
        background = game.loadBitmap("Background-1.png");
        startgame = game.loadBitmap("xstartgame.png");
    }

    @Override
    public void update(float deltaTime)
    {
        if (game.isTouchDown(0))
        {
            game.setScreen(new GameScreen(game));
            return;
        }
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
