package view;

import javafx.animation.AnimationTimer;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import model.Collidable;

import java.awt.*;
import java.awt.geom.Point2D;

/**
 * Paddle used by the player to deflect the ball.
 */
public class Paddle extends Rectangle implements Collidable
{
    /**************************
     * Instance Variables
     **************************/

	/** The keybinds to move the paddle up and down. */
	private KeyCode UP_KEYBIND, DOWN_KEYBIND;
	/** The amount of pixels the paddle will move on each key press. */
	private final int PADDLE_MOVE_SPEED = 12;
    /** The direction this Paddle is currently heading in. */
    private boolean headingUp = true;

    /**************************
     * Constructors
     **************************/

    /**
     * Creates an enhanced Rectangle which moves up or down
     * depending on certain key presses.
     * @param x - x coordinate.
     * @param y - y coordinate.
     * @param width - rectangle width.
     * @param height - rectangle height.
     * @param UP_KEYBIND - Keybind to move the paddle up.
     * @param DOWN_KEYBIND - Keybind to move the paddle down.
     */
	public Paddle(double x, double y, double width, double height, KeyCode UP_KEYBIND, KeyCode DOWN_KEYBIND)
	{
		super(x, y, width, height);
		this.UP_KEYBIND = UP_KEYBIND;
		this.DOWN_KEYBIND = DOWN_KEYBIND;
		setFill(Color.WHITE);
	}

    /**************************
     * Methods
     **************************/

    public void initializeEvents(Stage stage)
    {
        /**
         * Animation Timer inner class to handle Paddle movement.
         */
        AnimationTimer timer = new AnimationTimer()
        {
            @Override public void handle(long now)
            {
                // The location where the user would like his paddle to move.
                int desiredY = (headingUp ? -1 : 1) * PADDLE_MOVE_SPEED + (int) getY();

                if (desiredY < GUI.BORDER_SIZE)
                    setY(GUI.BORDER_SIZE);
                else if (desiredY + getHeight() > GUI.SCENE_HEIGHT - GUI.BORDER_SIZE)
                    setY(GUI.SCENE_HEIGHT - GUI.BORDER_SIZE - getHeight());
                else
                    setY(desiredY);
            }
        };

        // KeyEvent listener used for movement of the paddle.
        stage.addEventHandler(KeyEvent.KEY_PRESSED, e ->
        {
            if(e.getCode() == UP_KEYBIND || e.getCode() == DOWN_KEYBIND)
            {
                headingUp = e.getCode() == UP_KEYBIND;
                timer.start();
            }
        });

        // KeyEvent listener used to stop the paddle.
        stage.addEventHandler(KeyEvent.KEY_RELEASED, e ->
        {
            if(e.getCode() == UP_KEYBIND || e.getCode() == DOWN_KEYBIND)
                timer.stop();
        });
    }

    /**
     * Check if this object collided with a given pixel.
     * @param pt - Point to check.
     * @return - collided or not.
     */
    @Override public boolean collided(Point2D.Double pt)
    {
        return pt.getX() >= getX() && pt.getX() <= getX() + getWidth() && pt.getY() > getY() && pt.getY() < getY() + getHeight();
    }
}