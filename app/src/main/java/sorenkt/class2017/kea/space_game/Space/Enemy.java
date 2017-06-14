package sorenkt.class2017.kea.space_game.Space;


public class Enemy
{

    public static final float WIDTH = 50;
    public static final float HEIGHT = 26;
    float x = 160 - WIDTH /2;
    float y = World.MIN_Y;
    float vx = 0;
    float vy= 30;
    int type;


    //konstruktør
    public Enemy(float x, float y, int type)
    {
        this.x = x;
        this.y = y;
        this.type = type;
    }

}
