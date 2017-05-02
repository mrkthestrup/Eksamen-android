package sorenkt.class2017.kea.space_game.Space;

import sorenkt.class2017.kea.space_game.GameEngine;


public class World
{
    public static final float MIN_X =0;
    public static final float MAX_X =319;
    public static final float MIN_Y =40;
    public static final float MAX_Y =479;

    GameEngine game;
    boolean gameOver = false;
    Player player = new Player();

    public World(GameEngine game)
    {
        this.game = game;
    }

    public void update(float deltaTime, float accelX)
    {
        //looks for the Accelerometer and speed
        player.x = player.x - accelX * deltaTime * 50;

        //Check if player hit the wall
        if (player.x < MIN_X) player.x = MIN_X;
        if (player.x + Player.WIDTH > MAX_X) player.x = MAX_X - Player.WIDTH;

    }

}
