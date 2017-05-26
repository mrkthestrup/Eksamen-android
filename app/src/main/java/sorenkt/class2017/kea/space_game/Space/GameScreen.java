package sorenkt.class2017.kea.space_game.Space;


import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Typeface;
import android.util.Log;

import java.util.List;

import sorenkt.class2017.kea.space_game.GameEngine;
import sorenkt.class2017.kea.space_game.Screen;
import sorenkt.class2017.kea.space_game.TouchEvent;


public class GameScreen extends Screen
{
    enum State
    {
        Paused, Running, GameOver
    }
    State state = State.Running;
    Bitmap background;
    Bitmap flame;
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
        flame = game.loadBitmap("jetFlame2.png");
        resume = game.loadBitmap("resume.png");
        gameOver = game.loadBitmap("gameover.png");
    }
    @Override
    public void update(float deltaTime)
    {
        //if the game is paused and has a touch, we change the state to running
        if(state ==  State.Paused && game.isTouchDown(0))
        {
            state = State.Running;
            resume();
        }

        //if game over, go back to the mainMenu
        if (state ==State.GameOver)
        {
            List<TouchEvent> events = game.getTouchEvents();
            for (int i = 0; i < events.size(); i++)
            {
                if (events.get(i).type == TouchEvent.TouchEventType.Up)
                {
                    game.setScreen(new MainMenuScreen(game));                                       //set the screen to mainMenu
                    return;
                }
            }
        }

        //if the game is running and has a touch down at the corner
        if (state == State.Running && game.isTouchDown(0) && game.getTouchX(0) > (320 -40) && game.getTouchY(0) < 40)
        {
            //call the pause method
            pause();
        }

        game.drawBitmap(background, 0, 0);                                                          //setting the background to the screen


        if (state == State.Running)
        {
            world.update(deltaTime,game.getAccelerometer()[0]);

            //Ikke sikker på at det er det rigtige sted at placere dette, men det virker!
            //Blinking flames
            passedTime += deltaTime;
            if((passedTime - (int) passedTime) > 0.5f)
            {
                game.drawBitmap(flame,(int)world.player.x + Player.HEIGHT/ 2, (int)world.player.y + Player.WIDTH /2);
            }
        }
        game.drawText(font,"Score: " + Integer.toString(world.points), 27, 11, Color.GREEN,12);
        game.drawText(font,"Liv: " + Integer.toString(world.liv), 140, 11, Color.GREEN,12);

        worldrender.render();

        if (world.gameOver) state = State.GameOver;

        //if paused, draw the Resume.png in the middel of the screen
        if (state == State.Paused)
        {
            game.drawBitmap(resume, 160 - resume.getWidth()/2, 270 - resume.getHeight()/2);
        }

        //if gameover, draw the gameover.png in the middel of the screen
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
