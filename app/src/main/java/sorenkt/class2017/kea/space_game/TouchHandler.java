package sorenkt.class2017.kea.space_game;

//the google have it's own touchhandler and we want to make your own!
public interface TouchHandler
{
    //you may have more then 1 touch on the screen at the time
    public boolean isTouchDown(int pointer);

    public int getTouchX(int pointer);

    public int getTouchY(int pointer);
}
