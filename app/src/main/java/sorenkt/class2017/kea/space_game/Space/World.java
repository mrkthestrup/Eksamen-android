package sorenkt.class2017.kea.space_game.Space;

import android.graphics.Bitmap;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import sorenkt.class2017.kea.space_game.GameEngine;
import sorenkt.class2017.kea.space_game.State;


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
    List<Enemy> enemies = new ArrayList<>();

    float passedTime = 0;
    int points = 0;
    Bitmap explotion;
    Bitmap explotion2;
    int liv = 100;
    Missile missile;
    Laser laser;
    boolean gameOver = false;


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

        //laser slettes når den kommer udenfor skærmen
        for (int i = 0; i < lasers.size(); i++)
        {
            laser = lasers.get(i);

            if (laser.y < (int) World.MIN_Y)
            {
                lasers.remove(i);
            }
        }

        //Misilier slettes når den kommer udenfor skærme
        for (int i = 0; i < missiles.size(); i++)
        {
            missile = missiles.get(i);
            if (missile.y  >  World.MAX_Y)
            {
             missiles.remove(i);
            }
        }
        //kontrollere at liv ikke kommer uder 100
        if (liv <= 0 )
        {
            gameOver = true;
            enemies.clear();
            lasers.clear();
            missiles.clear();
            meteors.clear();
        }

        //collide stuff!
        collideLaserEnemy(deltaTime);
        collideMissilePlayer(deltaTime);
        collideEnemyPlayer();
        collideMeteorPlayer();
        collideMeteorLaser();
        collideMissileLaser();
    }

    //kontrollere hvordan laser rammer en enemy
    private boolean collideRects(float x, float y, float width, float height,
                                 float x2, float y2, float width2, float height2)
    {
        if ((x < x2 + width2) && (x + width > x2) && (y < y2 + height2) && (y+height > y2))
        {
            return true;
        }
        return false;
    }

    //Laser collisition med enemy
    private void collideLaserEnemy(float deltaTime)
    {
        boolean hits = true;
        explotion = game.loadBitmap("laserGreenShot.png");

        Laser laser;
        for (int y = 0; y < lasers.size(); y++)
        {
            laser = lasers.get(y);
            if (hits)
            {
                for (int i = 0; i < enemies.size(); i++)
                {
                    Enemy enemy = enemies.get(i);
                    if (collideRects(laser.x, laser.y, Laser.WIDTH, Laser.HEIGHT,
                            enemy.x, enemy.y, Enemy.WIDTH, Enemy.HEIGHT))
                    {
                        points = points + 10 - enemy.type;
                        game.drawBitmap(explotion, (int)enemy.x, (int)enemy.y);                     //måske ikke den bedste sted, men det virker! Collistion får mit spil til at crashe
                        enemies.remove(i);
                        lasers.remove(y);
                        i = i-1;
                        laserHitEnemy(laser, enemy);
                    }
                }
            }
        }
    }

    //Ser hvor laser rammer enemy henne!
    private void laserHitEnemy(Laser laser, Enemy enemy)
    {
        if (collideRects(laser.x, laser.y, Laser.WIDTH, Laser.HEIGHT,
                enemy.x, enemy.y + Enemy.HEIGHT, 1, 1))                                             // check the bottom left cornet of the enemy
        {
            return;
        }
        if (collideRects(laser.x, laser.y, Laser.WIDTH, Laser.HEIGHT,
                enemy.x + Enemy.WIDTH, enemy.y + Enemy.HEIGHT, 1, 1))                                // check the rigth bottom of the enemy
        {
            return;
        }

        if (collideRects(laser.x, laser.y, Laser.WIDTH, Laser.HEIGHT,
                enemy.x, enemy.y + Enemy.HEIGHT, Enemy.WIDTH, 1))                                   //check the bottom edge of the enemy
        {
            return;
        }
    }

    //Missil rammer player
    private void collideMissilePlayer(float deltaTime)
    {
        for (int i = 0; i < missiles.size(); i++)
        {
            Missile missile = missiles.get(i);
            if (collideRects(player.x, player.y, Player.WIDTH, Player.HEIGHT,
                    missile.x, missile.y, Missile.WIDTH, Missile.HEIGHT))
            {
                liv = liv - 20;
                missilesHits(missile,player);
                missiles.remove(i);
                i = i-1;
            }
        }
    }

    //Ser hvor missil rammer player henne!
    private void missilesHits(Missile missile, Player player)
    {
        if (collideRects(missile.x,missile.y, Missile.WIDTH, Missile.HEIGHT,
                player.x, player.y, 1,1))                                                             //check the top left corner of the player
        {
            return;
        }
        if (collideRects(missile.x, missile.y, Missile.WIDTH, Missile.HEIGHT,
                player.x + Player.WIDTH, player.y, 1,1))                                               // check the top rigth corner of the player
        {
            return;
        }
        if (collideRects(missile.x, missile.y, Missile.WIDTH, Missile.HEIGHT,
                player.x, player.y + Player.HEIGHT, 1 ,1))                                             // check the bottom left corner of the player
        {

            return;
        }

        if (collideRects(missile.x, missile.y, Missile.WIDTH, Missile.HEIGHT,
                player.x, player.y, Player.WIDTH, 1))                                                  //check the top edge of the player
        {
            return;
        }

        if (collideRects(missile.x, missile.y, Missile.WIDTH, Missile.HEIGHT,
                player.x, player.y, 1, Player.HEIGHT ))                                                 // check the left edge of the player
        {
            return;
        }
        if (collideRects(missile.x, missile.y, Missile.WIDTH, Missile.HEIGHT,
                player.x + Player.WIDTH, player.y, 1, Player.HEIGHT))                                   //check the right edge of the player
        {
            return;
        }
    }

    //Enemy rammer player eller player rammer enemy
    private void collideEnemyPlayer()
    {
        for (int i = 0; i < enemies.size(); i++)
        {
            Enemy enemy = enemies.get(i);
            if (collideRects(player.x, player.y, Player.WIDTH, Player.HEIGHT,
                    enemy.x, enemy.y, Enemy.WIDTH, Enemy.HEIGHT))
            {
                gameOver = true;
            }
        }
    }

    //meteor rammer player
    private void collideMeteorPlayer()
    {
        Random random = new Random();
        int max = 100;
        int min = 0;
        int randomnumber = random.nextInt(max-min) +1;
        for (int i = 0; i < meteors.size(); i++)
        {
            Meteor meteor = meteors.get(i);
            if (collideRects(player.x, player.y, Player.WIDTH, Player.HEIGHT,
                    meteor.x, meteor.y, Meteor.WIDTH, Meteor.HEIGHT))
            {
                liv = liv - randomnumber;
                meteors.remove(i);
                i = i-1;
            }
        }
    }

    // meteoer rammer laser
    private void collideMeteorLaser()
    {
        Laser laser;
        boolean hits = true;
        for (int y = 0; y < lasers.size(); y++)
        {
            laser = lasers.get(y);
            if (hits)
            {
                for (int i = 0; i < meteors.size(); i++)
                {
                    Meteor meteor = meteors.get(i);
                    if (collideRects(laser.x, laser.y, Laser.WIDTH, Laser.HEIGHT,
                            meteor.x, meteor.y, Meteor.WIDTH, Meteor.HEIGHT))
                    {
                        lasers.remove(y);
                        y = y-1;
                        meteorAbsorb(meteor, laser);
                    }
                }
            }
        }
    }

    private void meteorAbsorb(Meteor meteor, Laser laser)
    {
        if (collideRects(meteor.x, meteor.y, Meteor.WIDTH, Meteor.HEIGHT,
                laser.x, laser.y + Laser.HEIGHT, Laser.WIDTH, 1))                                   //check the bottom edge of the Meteor
        {
            return;
        }
    }

    private void collideMissileLaser()
    {
        explotion2 = game.loadBitmap("Explosion.png");
        Random random = new Random();
        int max = 5;
        int min = 0;
        int randomnumber2 = random.nextInt(max-min) +1;
        Laser laser;
        boolean hits = true;
        for (int y = 0; y < lasers.size(); y++)
        {
            laser = lasers.get(y);
            if (hits)
            {
                for (int i = 0; i < missiles.size(); i++)
                {
                    Missile missile = missiles.get(i);
                    if (collideRects(laser.x, laser.y, Laser.WIDTH, Laser.HEIGHT,
                            missile.x, missile.y, Missile.WIDTH, Missile.HEIGHT))
                    {
                        game.drawBitmap(explotion2, (int)missile.x, (int)missile.y);
                        lasers.remove(y);
                        missiles.remove(i);
                        i = i-1;
                        y = y-1;
                        points = points + randomnumber2;
                        laserToMissile(laser,missile);
                    }
                }
            }
        }
    }



    private  void laserToMissile(Laser laser, Missile missile)
    {
        if (collideRects(laser.x, laser.y, Laser.WIDTH, Laser.HEIGHT,
                missile.x, missile.y + Missile.HEIGHT, 1 ,1))                                             // check the bottom left cornet of the missile
        {
            return;
        }
        if (collideRects(laser.x, laser.y, Laser.WIDTH, Laser.HEIGHT,
                missile.x + Missile.WIDTH, missile.y + Missile.HEIGHT, 1,1))                                // check the rigth bottom of the missile
        {
            return;
        }

        if (collideRects(laser.x, laser.y, Laser.WIDTH, Laser.HEIGHT,
                missile.x, missile.y + Missile.HEIGHT, Missile.WIDTH, 1))                                   //check the bottom edge of the missile
        {
            return;
        }
    }

}
