package sorenkt.class2017.kea.space_game.Space;


import sorenkt.class2017.kea.space_game.GameEngine;
import sorenkt.class2017.kea.space_game.Screen;

public class MainMenu extends GameEngine
{

    @Override
    public Screen createStartScreen()
    {
        return  new MainMenuScreen(this);
    }

    public void onPause()
    {
        super.onPause();

    }

    public void onResume()
    {
        super.onResume();
            }
}
