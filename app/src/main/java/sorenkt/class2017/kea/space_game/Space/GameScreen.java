package sorenkt.class2017.kea.space_game.Space;


import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Typeface;
import android.util.Log;

import java.util.List;

import sorenkt.class2017.kea.space_game.GameEngine;
import sorenkt.class2017.kea.space_game.Screen;
import sorenkt.class2017.kea.space_game.Sound;
import sorenkt.class2017.kea.space_game.TouchEvent;


public class GameScreen extends Screen
{
    enum State
    {
        Paused, Running, GameOver
    }
    State state = State.Running;
    Bitmap background;
    Bitmap gameOver;
    Bitmap resume;
    World world;
    WorldRender worldrender;
    float passedTime= 0;
    Typeface font;


    public GameScreen(GameEngine game)
    {
        super(game);
        background = game.loadBitmap("Background-4.png");
        world = new World(game);
        worldrender = new WorldRender(game, world);
        resume = game.loadBitmap("resume.png");
        gameOver = game.loadBitmap("gameover.png");

    }
    @Override
    public void update(float deltaTime)
    {
        //hvis spillet er paused og har touch, vi skifter state til running
        if(state ==  State.Paused && game.isTouchDown(0))
        {
            state = State.Running;
            resume();
        }

        //Hvis gameover, går tilbage til mainMenu
        if (state == State.GameOver)
        {
            List<TouchEvent> events = game.getTouchEvents();
            for (int i = 0; i < events.size(); i++)
            {
                if (events.get(i).type == TouchEvent.TouchEventType.Up)
                {
                    //sætter screem til mainMenu
                    game.setScreen(new MainMenuScreen(game));
                    return;
                }
            }
        }
        //hvis spillet kører og der trykkes på højre hjørne, sættes spillet på pause
        if (state == State.Running && game.isTouchDown(0) && game.getTouchX(0) > (320 -40) && game.getTouchY(0) < 40)
        {
            //Kalder pause method
            pause();
        }

        //sætter baggrund til skærmen
        game.drawBitmap(background, 0, 0);

        if (state == State.Running)
        {
            world.update(deltaTime,game.getAccelerometer()[0]);

            //Ikke sikker på at det er det rigtige sted at placere dette, men det virker!
            //Blinking flames
            passedTime += deltaTime;
            if((passedTime - (int) passedTime) > 0.5f)
            {
                game.drawBitmap(worldrender.flame,(int)world.player.x + Player.HEIGHT/ 2, (int)world.player.y + Player.WIDTH /2);
            }
        }
        game.drawText(font,"Score: " + Integer.toString(world.points), 27, 11, Color.GREEN,12);
        game.drawText(font,"Liv: " + Integer.toString(world.liv), 140, 11, Color.GREEN,12);

        //tegner alle objekterne i spillet (Enemy, player, laser, missiler, meteor)
        worldrender.render();

        //hvis pause, så tegner den Resume i midten af skærmen.
        if (state == State.Paused)
        {
            game.drawBitmap(resume, 160 - resume.getWidth()/2, 270 - resume.getHeight()/2);
        }

        //kontrollere om gameOver er sandt, og hvis ja, så stopper spillet
        if (world.gameOver) state = State.GameOver;

        //hvis gameover, så tegner den Gameover i midten af skærmen.
        if(state == State.GameOver)
        {
            game.drawBitmap(gameOver, 160 - gameOver.getWidth()/2, 270 - gameOver.getHeight()/2);
        }

    }

    @Override
    public void pause()
    {
        if(state == State.Running ) state = State.Paused;

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
