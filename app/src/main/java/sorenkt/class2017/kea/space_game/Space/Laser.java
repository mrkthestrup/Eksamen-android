package sorenkt.class2017.kea.space_game.Space;


public class Laser
{
    World world;
    public static final float WIDTH = 5;
    public static final float HEIGHT = 18;
    float x = 160 - WIDTH /2;
    float x2 = 160 + WIDTH /2;
    float y = World.MAX_Y - 50;
    float vx = 0;
    float vy= -10;
}
