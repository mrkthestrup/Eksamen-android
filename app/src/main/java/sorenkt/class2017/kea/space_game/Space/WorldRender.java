package sorenkt.class2017.kea.space_game.Space;


import android.graphics.Bitmap;

import sorenkt.class2017.kea.space_game.GameEngine;

public class WorldRender
{
    GameEngine game;
    World world;
    Bitmap spaceShip;
    Bitmap lazer;



    public WorldRender(GameEngine game, World world)
    {
        this.game = game;
        this.world = world;
        spaceShip = game.loadBitmap("playerWithGunAndEngine.png");
        //lazer = game.loadBitmap()
    }

    public void render()
    {
        game.drawBitmap(spaceShip, (int)world.player.x, (int)world.player.y);
    }

}
