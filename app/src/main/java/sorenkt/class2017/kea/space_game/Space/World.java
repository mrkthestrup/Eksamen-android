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


    Player player = new Player();
    //Laser laserGreen = new Laser();
    List<Laser> lasers = new ArrayList<>();
    float passedTime = 0;

    boolean gameOver = false;

    List<Enemy> enemies = new ArrayList<>();
    GameEngine game;

    public World(GameEngine game)
    {
        this.game = game;
        //generateenemies();
    }

    public void update(float deltaTime, float accelX)
    {
        passedTime += deltaTime;
     /*   if (enemies.size() == 0)
        {
            generateenemies();

        }*/


        if((passedTime - (int) passedTime) > 0.9f)                       //skal lige have noget hjælp her med at den kun skal skyde 1 gang og ikke 10!!!
        {
            lasers.add(new Laser(player.x +7, player.y));
            lasers.add(new Laser(player.x +37, player.y));
        }
        for(Laser l: lasers)
        {
            l.y = l.y + l.vy * deltaTime;
        }
       // if (lasers.size() >= 100)            //sikker mig at array ikke tager for meget ram og hastighed
       // {
       //     lasers.clear();
       // }


        //Kigger efter Accelerometer og fart
        player.x = player.x - accelX * deltaTime * 50;

        //Kontrollere om player rammer væggen
        if (player.x < MIN_X) player.x = MIN_X;
        if (player.x + Player.WIDTH > MAX_X) player.x = MAX_X - Player.WIDTH;

    }

    private void generateenemies()
    {
        enemies.clear();                            //kontrollere at den altid er tom

        for (int y = 10, type = 0; y < MAX_Y; y = y +(int)Enemy.HEIGHT + 2, type ++)
        {
            for (int x = 0; x < MAX_X - Enemy.WIDTH; x = x +(int)Enemy.WIDTH + 2)
            {
                enemies.add(new Enemy(x,y,type));
            }
        }
    }

}
