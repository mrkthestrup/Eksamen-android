package sorenkt.class2017.kea.space_game.Space;

import java.util.ArrayList;
import java.util.List;

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
    List<Enemy> enemies = new ArrayList<>();

    public World(GameEngine game)
    {
        this.game = game;
    }

    public void update(float deltaTime, float accelX)
    {
        //Kigger efter Accelerometer og fart
        player.x = player.x - accelX * deltaTime * 50;

        //Kontrollere om player rammer væggen
        if (player.x < MIN_X) player.x = MIN_X;
        if (player.x + Player.WIDTH > MAX_X) player.x = MAX_X - Player.WIDTH;

    }

}
