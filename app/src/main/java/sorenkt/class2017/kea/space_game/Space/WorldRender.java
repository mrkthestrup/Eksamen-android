package sorenkt.class2017.kea.space_game.Space;


import android.graphics.Bitmap;


import sorenkt.class2017.kea.space_game.GameEngine;

public class WorldRender
{
    GameEngine game;
    World world;
    Bitmap spaceShip;
    Bitmap laserGreen;
    Bitmap enemy4;
    Bitmap meteorSmall;
    Bitmap missile;
    Bitmap flame;




    public WorldRender(GameEngine game, World world)
    {
        this.game = game;
        this.world = world;
        spaceShip = game.loadBitmap("playerWithGunAndEngine.png");
        laserGreen = game.loadBitmap("laserGreen.png");
        missile = game.loadBitmap("missile.png");
        enemy4 = game.loadBitmap("enemyShip.png");
        meteorSmall = game.loadBitmap("meteorSmall.png");
        flame = game.loadBitmap("jetFlame2.png");

    }

    public void render()
    {

        for (Enemy e: world.enemies)
        {
            game.drawBitmap(enemy4, (int)e.x, (int)e.y);
        }

        game.drawBitmap(spaceShip, (int)world.player.x, (int)world.player.y);


        for(Laser l: world.lasers)
        {
            //laser vises ud fra guns
            game.drawBitmap(laserGreen, (int)l.x, (int)l.y);
        }

        for(Missile m: world.missiles)
        {
            //Missiller
            game.drawBitmap(missile, (int)m.x, (int)m.y);
        }


        for(Meteor meteor: world.meteors)
        {
            //Meteor
            game.drawBitmap(meteorSmall, (int)meteor.x, (int)meteor.y);
        }

    }


}
