package sorenkt.class2017.kea.space_game.Space;


import sorenkt.class2017.kea.space_game.GameEngine;
import sorenkt.class2017.kea.space_game.Screen;

public class SpaceGame extends GameEngine
{
    @Override
    public Screen createStartScreen()
    {
        return new MainMenuScreen(this);
    }
}
