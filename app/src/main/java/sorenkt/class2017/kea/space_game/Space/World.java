package sorenkt.class2017.kea.space_game.Space;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import sorenkt.class2017.kea.space_game.GameEngine;


public class World
{
    public static final float MIN_X =0;
    public static final float MAX_X =319;
    public static final float MIN_Y =40;
    public static final float MAX_Y =479;


    Player player = new Player();
    Enemy enemy = new Enemy();
    List<Laser> lasers = new ArrayList<>();
    List<Laser> lasersE = new ArrayList<>();
    float passedTime = 0;

    boolean gameOver = false;


    List<Enemy> enemies = new ArrayList<>();
    GameEngine game;

    public World(GameEngine game)
    {
        this.game = game;

    }

    public void update(float deltaTime, float accelX)
    {
        //player laser
        passedTime += deltaTime;

        if((passedTime - (int) passedTime) > 0.9f)
        {
            lasers.add(new Laser(player.x +7, player.y));
            lasers.add(new Laser(player.x +37, player.y));
            enemies.add(new Enemy());
            lasersE.add(new Laser(enemy.x, enemy.y));
            lasersE.add(new Laser(enemy.x+20, enemy.y));
            passedTime = 0;
        }
        for(Laser l: lasers)
        {
            l.y = l.y + l.vy * deltaTime;
        }


        for (Laser le:  lasersE)
        {
            le.y = le.y - le.vy * deltaTime;
        }


        for (Enemy e: enemies)
        {
            e.y = e.y + e.vy * deltaTime;
        }


        //Kigger efter Accelerometer og fart
        player.x = player.x - accelX * deltaTime * 50;

        //Kontrollere om player rammer væggen
        if (player.x < MIN_X) player.x = MIN_X;
        if (player.x + Player.WIDTH > MAX_X) player.x = MAX_X - Player.WIDTH;

    }

    public void generateEnemy()
    {

    }



}
