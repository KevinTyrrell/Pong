package components;

import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 * Created by User on 3/20/2016.
 * Paddle used by the player to deflect each ball.
 */
public class Paddle extends Rectangle
{
    /** The keybinds to move the paddle up and down. */
    private KeyCode KEY_UP_KEYBIND, KEY_DOWN_KEYBIND;
    /** The amount of pixels the paddle will jump on each key press. */
    private final int PADDLE_MOVE_SPEED = 15;
    /** Variable which represents whether the Paddle is currently moving. */
    private boolean isMoving = false;

    /**
     * Constructs a Rectangular Paddle object which has a keybind,
     * telling it whether to move up or down upon an event being triggered.
     * @param x                - location of the paddle on the x axis.
     * @param y                - location of the paddle on the y axis.
     * @param width            - width of the paddle.
     * @param height           - height of the paddle.
     * @param KEY_UP_KEYBIND   - Keybind to move up.
     * @param KEY_DOWN_KEYBIND - Keybind to move down.
     * @param scene            - variable so we can add event handlers.
     */
    public Paddle(double x, double y, double width, double height, KeyCode KEY_UP_KEYBIND, KeyCode KEY_DOWN_KEYBIND, Scene scene)
    {
        super(x, y, width, height);
        this.KEY_UP_KEYBIND = KEY_UP_KEYBIND;
        this.KEY_DOWN_KEYBIND = KEY_DOWN_KEYBIND;
        setFill(Color.WHITE);

        // KeyEvent listener used for movement of the paddle.
        scene.addEventHandler(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>()
        {
            @Override
            public void handle(KeyEvent e)
            {
                if (!isMoving)
                {
                    // Only look for the keys designated for this paddle.
                    if (e.getCode() == KEY_UP_KEYBIND || e.getCode() == KEY_DOWN_KEYBIND)
                    {
                        isMoving = true;
                        startMoving(e.getCode());
                    }
                }
            }
        });

        // KeyEvent listener used to stop the paddle.
        scene.addEventHandler(KeyEvent.KEY_RELEASED, new EventHandler<KeyEvent>()
        {
            @Override
            public void handle(KeyEvent e)
            {
                if (isMoving)
                {
                    // Only look for the keys designated for this paddle.
                    if (e.getCode() == KEY_UP_KEYBIND || e.getCode() == KEY_DOWN_KEYBIND)
                    {
                        isMoving = false;
                    }
                }
            }
        });
    }

    /**
     * Loop which begins the movement of a paddle, given that
     * isMoving is still true. This method eliminates the delay
     * when a user holds down a key, before multiple key presses
     * are processed, allowing for more fluent inputs.
     * @param e KeyCode of the event.
     */
    private void startMoving(KeyCode e)
    {
        new Thread(new Runnable()
        {
            @Override
            public void run()
            {
                // Boolean variable which is false if user releases key.
                while (isMoving)
                {
                    // The location where the user would like his paddle to move.
                    int desiredY = (e == KEY_UP_KEYBIND ? -1 : 1) * PADDLE_MOVE_SPEED + (int) getY();

                    if (desiredY < Main.BORDER_SIZE)
                    {
                        setY(Main.BORDER_SIZE);
                    }
                    else if (desiredY + getHeight() > Main.SCENE_HEIGHT - Main.BORDER_SIZE)
                    {
                        setY(Main.SCENE_HEIGHT - Main.BORDER_SIZE - getHeight());
                    }
                    else
                    {
                        setY(desiredY);
                    }

                    Tools.Sleep(30);
                }
            }
        }).start();
    }
}