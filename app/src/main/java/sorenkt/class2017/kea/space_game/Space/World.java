package sorenkt.class2017.kea.space_game.Space;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import sorenkt.class2017.kea.space_game.GameEngine;


public class World
{
    public static final float MIN_X =0;
    public static final float MAX_X =319;
    public static final float MIN_Y =40;
    public static final float MAX_Y =479;


    Player player = new Player();
    List<Laser> lasers = new ArrayList<>();
    List<Missile> missiles = new ArrayList<>();
    List<Meteor> meteors = new ArrayList<>();
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
        passedTime += deltaTime;

        int randomnumber = ThreadLocalRandom.current().nextInt((int)World.MIN_X, (int)World.MAX_X - (int)Enemy.WIDTH); //sikre at vi bliver indenfor skærmstørrelsen og samtide med at det kommer random!
        //player laser
        if((passedTime - (int) passedTime) > 0.9f)
        {
            lasers.add(new Laser(player.x +7, player.y));
            lasers.add(new Laser(player.x +37, player.y));
            passedTime = 0;
        }

        //enemy
        passedTime += deltaTime;
        if ((passedTime + (int) passedTime) > 0.92f)
        {
            enemies.add(new Enemy(randomnumber, World.MIN_Y, 1));
            missiles.add(new Missile(randomnumber + 12, World.MIN_Y));
            missiles.add(new Missile(randomnumber + 28, World.MIN_Y));
            passedTime = 0;
        }

        //metoer
        passedTime += deltaTime;
        if ((passedTime + (int) passedTime) > 0.97)
        {
            meteors.add(new Meteor(randomnumber,World.MIN_Y));
            passedTime = 0;
        }

        //player laser
        for(Laser l: lasers)
        {
            l.y = l.y + l.vy * deltaTime;
        }

        //enemy lasers
        for (Missile m: missiles)
        {
            m.y = m.y - (10+m.vy) * deltaTime;
        }

        //emeny
        for (Enemy e: enemies)
        {
            e.y = e.y + e.vy * deltaTime;
        }
        //meteor
        for (Meteor m: meteors)
        {
            m.y = m.y - m.vy * deltaTime;
        }

        //Kigger efter Accelerometer og fart
        player.x = player.x - accelX * deltaTime * 50;

        //Kontrollere om player rammer væggen
        if (player.x < MIN_X) player.x = MIN_X;
        if (player.x + Player.WIDTH > MAX_X) player.x = MAX_X - Player.WIDTH;

    }




}
