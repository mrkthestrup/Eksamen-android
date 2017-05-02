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
        background = game.loadBitmap("BackgroundMenuMain.png");
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

        game.drawBitmap(background, 0,0);

        passedTime += deltaTime;
        if((passedTime - (int) passedTime) > 0.5f)
        {
            game.drawBitmap(startgame, 150 - startgame.getWidth()/2, 200 - startgame.getHeight() /2 );
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
