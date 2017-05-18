package sorenkt.class2017.kea.space_game.Space;

public class Missile
{

    public static final float WIDTH = 13;
    public static final float HEIGHT = 29;
    float x = 160 - WIDTH /2;
    float y = World.MIN_Y-45;
    float vx = 0;
    float vy= -78;

    //konstruktør
    public Missile (float x, float y)     //tager x og y oppe fra
    {
        this.x = x;
        this.y = y;
    }
}
