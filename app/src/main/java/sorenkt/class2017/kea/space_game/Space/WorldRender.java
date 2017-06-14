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
        //tegner player på skærmen
        game.drawBitmap(spaceShip, (int)world.player.x, (int)world.player.y);

        //tegner enemy på skærmen
        for (Enemy e: world.enemies)
        {
            //enemy
            game.drawBitmap(enemy4, (int)e.x, (int)e.y);
        }

        //tegner laser på skærmen
        for(Laser l: world.lasers)
        {
            //laser vises ud fra guns position
            game.drawBitmap(laserGreen, (int)l.x, (int)l.y);
        }
        //tegner Missiler på skærmen
        for(Missile m: world.missiles)
        {
            //Missiller
            game.drawBitmap(missile, (int)m.x, (int)m.y);
        }
        //tegner Meteor på skærmen
        for(Meteor meteor: world.meteors)
        {
            //Meteor
            game.drawBitmap(meteorSmall, (int)meteor.x, (int)meteor.y);
        }
    }


}
