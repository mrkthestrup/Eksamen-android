package sorenkt.class2017.kea.space_game.Space;

import sorenkt.class2017.kea.space_game.GameEngine;

/**
 * Created by thest on 01-05-2017.
 */

public class World
{
    public static final float MIN_X =0;
    public static final float MAX_X =1599;
    public static final float MIN_Y =0;
    public static final float MAX_Y =1199;

    GameEngine game;

    public World(GameEngine game)
    {
        this.game = game;
    }

}
