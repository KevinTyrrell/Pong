package components;

/**
 * Created by User on 3/20/2016.
 * Class for various developer tools.
 */
public abstract class Tools
{
    /**
     * Sleep function which handles the InterruptedException.
     * @param milis - Duration to sleep.
     */
    public static void Sleep(long milis)
    {
        try
        {
            Thread.sleep(milis);
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
        }
    }
}
