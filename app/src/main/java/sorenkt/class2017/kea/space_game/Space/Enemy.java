package sorenkt.class2017.kea.space_game.Space;

import java.util.Random;

public class Enemy
{
    Random random = new Random();
    int randomnumber = random.nextInt((int)World.MAX_X)-Player.WIDTH;
    public static final float WIDTH = 50;
    public static final float HEIGHT = 60;      // Den højeste er 60 pixel
    float x = randomnumber;
    float y = World.MIN_Y;
    float vx = 0;
    float vy= 50;
    int type;

    public Enemy()
    {
    }

    //konstruktor
    public Enemy(float x, float y, int type)
    {
        this.x = x;
        this.y = y;
        this.type = type;
    }

}
