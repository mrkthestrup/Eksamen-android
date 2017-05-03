package sorenkt.class2017.kea.space_game.Space;

public class Enemy
{
    public static final float WIDTH = 50;
    public static final float HEIGHT = 60;      // Den højeste er 60 pixel
    float x = 0;
    float y;
    int type;


    //konstruktor
    public Enemy(float x, float y, int type)
    {
        this.x = x;
        this.y = y;
        this.type = type;
    }

}
