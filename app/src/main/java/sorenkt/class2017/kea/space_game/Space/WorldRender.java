package sorenkt.class2017.kea.space_game.Space;


import android.graphics.Bitmap;


import sorenkt.class2017.kea.space_game.GameEngine;

public class WorldRender
{
    GameEngine game;
    World world;
    Bitmap spaceShip;
    Bitmap laserGreen;
    Bitmap enemy1;
    Bitmap enemy2;
    Bitmap enemy3;
    Bitmap enemy4;
    Bitmap meteorBig;
    Bitmap meteorSmall;


    public WorldRender(GameEngine game, World world)
    {
        this.game = game;
        this.world = world;
        spaceShip = game.loadBitmap("playerWithGunAndEngine.png");
        laserGreen = game.loadBitmap("laserGreen.png");
        enemy1 = game.loadBitmap("enemyShip1.png");
        enemy2 = game.loadBitmap("enemyShip.png");
        enemy3 = game.loadBitmap("enemyShip2.png");
        enemy4 = game.loadBitmap("enemyShip3.png");
        meteorBig = game.loadBitmap("meteorBigNy.png");
        meteorSmall = game.loadBitmap("meteorSmall.png");
    }

    public void render()
    {
        game.drawBitmap(enemy1, 50,240);
        game.drawBitmap(enemy2, 110,240);
        game.drawBitmap(enemy3, 160,240);
        game.drawBitmap(enemy4, 210,240);
        game.drawBitmap(meteorBig, 50,150);
        game.drawBitmap(meteorSmall, 220,150);
        game.drawBitmap(spaceShip, (int)world.player.x, (int)world.player.y);
        //laser vises ud fra guns
        game.drawBitmap(laserGreen,(int)world.player.x + 9 , (int)world.player.y);
        game.drawBitmap(laserGreen,(int)world.player.x + 38 , (int)world.player.y);

    }


}
