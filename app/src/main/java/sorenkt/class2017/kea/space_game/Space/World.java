package sorenkt.class2017.kea.space_game.Space;

import android.graphics.Bitmap;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import sorenkt.class2017.kea.space_game.GameEngine;


public class World
{
    public static final float MIN_X =0;
    public static final float MAX_X =319;
    public static final float MIN_Y =40;
    public static final float MAX_Y =479;

    List<Laser> lasers = new ArrayList<>();
    List<Missile> missiles = new ArrayList<>();
    List<Meteor> meteors = new ArrayList<>();
    List<Enemy> enemies = new ArrayList<>();

    float passedTime = 0;
    float passedTime1 = 0;
    float passedTime2 = 0;

    int points = 0;
    int liv = 100;

    Bitmap explotion;
    Bitmap explotion2;

    Missile missile;
    Laser laser;
    Player player = new Player();

    boolean gameOver = false;

    GameEngine game;

    public World(GameEngine game)
    {
        this.game = game;

    }

    public void update(float deltaTime, float accelX)
    {
        passedTime += deltaTime;

        //sikre at vi bliver indenfor skærmstørrelsen og samtide med at enemy kommer random!
        int randomnumber = ThreadLocalRandom.current().nextInt((int)World.MIN_X, (int)World.MAX_X - (int)Enemy.WIDTH);

        //player laser
        if(passedTime > 0.8f)
        {
            lasers.add(new Laser(player.x +7, player.y));
            lasers.add(new Laser(player.x +37, player.y));
            passedTime = 0;
        }

        //enemy
        passedTime1 += deltaTime;
        if (passedTime1 > 0.92f)
        {
            enemies.add(new Enemy(randomnumber, World.MIN_Y, 1));
            missiles.add(new Missile(randomnumber + 12, World.MIN_Y));
            missiles.add(new Missile(randomnumber + 28, World.MIN_Y));
            passedTime1 = 0;
        }

        //metoer
        passedTime2 += deltaTime;
        if (passedTime2 > 0.99f)
        {
            meteors.add(new Meteor(randomnumber,World.MIN_Y));
            passedTime2 = 0;
        }

        //Players laser
        for(Laser l: lasers)
        {
            l.y = l.y + l.vy * deltaTime;
        }

        //enemy Missiler
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

        //Misilier slettes når den kommer udenfor skærmen
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
        collideLaserEnemy();
        collideMissilePlayer();
        collideEnemyPlayer();
        collideMeteorPlayer();
        collideMeteorLaser();
        collideMissileLaser();
    }

    //kontrollere om der er kollision mellem 2 objekter
    private boolean collide(float x, float y, float width, float height,
                            float x2, float y2, float width2, float height2)
    {
        if ((x < x2 + width2) && (x + width > x2) && (y < y2 + height2) && (y+height > y2))
        {
            return true;
        }
        return false;
    }

    //Laser kolloider med enemy
    private void collideLaserEnemy()
    {
        explotion = game.loadBitmap("laserGreenShot.png");                                          //måske ikke det bedste sted, men det virker!
        Laser laser;
        for (int y = 0; y < lasers.size(); y++)
        {
            laser = lasers.get(y);
                for (int i = 0; i < enemies.size(); i++)
                {
                    Enemy enemy = enemies.get(i);
                    if (collide(laser.x, laser.y, Laser.WIDTH, Laser.HEIGHT,
                            enemy.x, enemy.y, Enemy.WIDTH, Enemy.HEIGHT))
                    {
                        laserHitEnemy(laser, enemy);
                        points = points + 10 - enemy.type;
                        game.drawBitmap(explotion, (int)enemy.x, (int)enemy.y);                     //måske ikke det bedste sted, men det virker!
                        enemies.remove(i);
                        lasers.remove(y);
                    }
                }

        }
    }

    //kontrollere hvor laser kolloider med enemy henne!
    private void laserHitEnemy(Laser laser, Enemy enemy)
    {
        //kontrollere venstre hjørne af enemy
        if (collide(laser.x, laser.y, Laser.WIDTH, Laser.HEIGHT,
                enemy.x, enemy.y + Enemy.HEIGHT, 1, 1))
        {
            return;
        }
        //kontrollere højre hjørne af enemy
        if (collide(laser.x, laser.y, Laser.WIDTH, Laser.HEIGHT,
                enemy.x + Enemy.WIDTH, enemy.y + Enemy.HEIGHT, 1, 1))
        {
            return;
        }
        //kontrollere bunden af enemy
        if (collide(laser.x, laser.y, Laser.WIDTH, Laser.HEIGHT,
                enemy.x, enemy.y + Enemy.HEIGHT, Enemy.WIDTH, 1))
        {
            return;
        }
    }

    //Missil kolloider med player
    private void collideMissilePlayer()
    {
        for (int i = 0; i < missiles.size(); i++)
        {
            Missile missile = missiles.get(i);
            if (collide(player.x, player.y, Player.WIDTH, Player.HEIGHT,
                    missile.x, missile.y, Missile.WIDTH, Missile.HEIGHT))
            {
                liv = liv - 20;
                missilesHits(missile,player);
                missiles.remove(i);
            }
        }
    }

    //kontrollere hvor missil kolloider med player henne!
    private void missilesHits(Missile missile, Player player)
    {
        //kontrollere top venstre hjørne af player
        if (collide(missile.x,missile.y, Missile.WIDTH, Missile.HEIGHT,
                player.x, player.y, 1,1))
        {
            return;
        }
        //kontrollere top højre hjørne af player
        if (collide(missile.x, missile.y, Missile.WIDTH, Missile.HEIGHT,
                player.x + Player.WIDTH, player.y, 1,1))
        {
            return;
        }
        //kontrollere toppen af player
        if (collide(missile.x, missile.y, Missile.WIDTH, Missile.HEIGHT,
                player.x, player.y, Player.WIDTH, 1))
        {
            return;
        }
        //kontrollere bund venstre hjørne af player
        if (collide(missile.x, missile.y, Missile.WIDTH, Missile.HEIGHT,
                player.x, player.y + Player.HEIGHT, 1 ,1))
        {
            return;
        }
        //kontrollere bund højre hjørne af player
        if (collide(missile.x, missile.y, Missile.WIDTH, Missile.HEIGHT,
                player.x + Player.WIDTH, player.y + Player.HEIGHT, 1,1))
        {
            return;
        }
        //kontrollere venstre side af player
        if (collide(missile.x, missile.y, Missile.WIDTH, Missile.HEIGHT,
                player.x, player.y, 1, Player.HEIGHT ))
        {
            return;
        }
        //kontrollere højre side af player
        if (collide(missile.x, missile.y, Missile.WIDTH, Missile.HEIGHT,
                player.x + Player.WIDTH, player.y, 1, Player.HEIGHT))
        {
            return;
        }
    }

    //Enemy kolloider med player || player kolloider med enemy
    private void collideEnemyPlayer()
    {
        for (int i = 0; i < enemies.size(); i++)
        {
            Enemy enemy = enemies.get(i);
            if (collide(player.x, player.y, Player.WIDTH, Player.HEIGHT,
                    enemy.x, enemy.y, Enemy.WIDTH, Enemy.HEIGHT))
            {
                gameOver = true;
            }
        }
    }

    //kontollere om meteor kolloider med player
    private void collideMeteorPlayer()
    {
        Random random = new Random();
        int max = 100;
        int min = 0;
        int randomnumber = random.nextInt(max-min) +1;
        for (int i = 0; i < meteors.size(); i++)
        {
            Meteor meteor = meteors.get(i);
            if (collide(player.x, player.y, Player.WIDTH, Player.HEIGHT,
                    meteor.x, meteor.y, Meteor.WIDTH, Meteor.HEIGHT))
            {
                liv = liv - randomnumber;
                meteors.remove(i);
            }
        }
    }

    //meteoer kolloider med laser
    private void collideMeteorLaser()
    {
        Laser laser;
        for (int y = 0; y < lasers.size(); y++)
        {
            laser = lasers.get(y);
                for (int i = 0; i < meteors.size(); i++)
                {
                    Meteor meteor = meteors.get(i);
                    if (collide(laser.x, laser.y, Laser.WIDTH, Laser.HEIGHT,
                            meteor.x, meteor.y, Meteor.WIDTH, Meteor.HEIGHT))
                    {
                        lasers.remove(y);
                        meteorAbsorb(meteor, laser);
                    }
                }
        }
    }
    //kontrollere om meteor kolloider med laser
    private void meteorAbsorb(Meteor meteor, Laser laser)
    {
        //kontrollere bunden af meteor
        if (collide(meteor.x, meteor.y, Meteor.WIDTH, Meteor.HEIGHT,
                laser.x, laser.y + Laser.HEIGHT, Laser.WIDTH, 1))
        {
            return;
        }
    }
    //Laser kolloider med Missil
    private void collideMissileLaser()
    {
       explotion2 = game.loadBitmap("Explosion.png");                                               //måske ikke det bedste sted, men det virker!
        Random random = new Random();
        int max = 5;
        int min = 0;
        int randomnumber2 = random.nextInt(max-min) +1;
        Laser laser;

        for (int y = 0; y < lasers.size(); y++)
        {
            laser = lasers.get(y);
                for (int i = 0; i < missiles.size(); i++)
                {
                    Missile missile = missiles.get(i);
                    if (collide(laser.x, laser.y, Laser.WIDTH, Laser.HEIGHT,
                            missile.x, missile.y, Missile.WIDTH, Missile.HEIGHT))
                    {
                        game.drawBitmap(explotion2, (int)missile.x, (int)missile.y);                //måske ikke det bedste sted, men det virker!
                        lasers.remove(y);
                        missiles.remove(i);
                        points = points + randomnumber2;
                        laserToMissile(laser,missile);
                    }
                }
        }
    }
    //kontrollere hvor laser kolloider med missil henne!
    private  void laserToMissile(Laser laser, Missile missile)
    {
        //kontrollere venstre bund hjørne side af missil
        if (collide(laser.x, laser.y, Laser.WIDTH, Laser.HEIGHT,
                missile.x, missile.y + Missile.HEIGHT, 1 ,1))
        {
            return;
        }
        //kontrollere højre bund hjørne side af missil
        if (collide(laser.x, laser.y, Laser.WIDTH, Laser.HEIGHT,
                missile.x + Missile.WIDTH, missile.y + Missile.HEIGHT, 1,1))
        {
            return;
        }
        //kontrollere bunden af missil
        if (collide(laser.x, laser.y, Laser.WIDTH, Laser.HEIGHT,
                missile.x, missile.y + Missile.HEIGHT, Missile.WIDTH, 1))
        {
            return;
        }
    }

}
