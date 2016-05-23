package components;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;


/**
 * Created by User on 3/21/2016.
 * Class which represents the ball on the game field.
 */
public class Ball extends Circle
{
    /** Direction in which the ball is heading (0-360). */
    Trajectory angle = new Trajectory((short) 22);
    /** The speed of the ball, in pixels. */
    final double BALL_SPEED = 5;
    /** Whether the ball is currently active or not. */
    boolean active = true;

    public Ball(double centerX, double centerY, double radius)
    {
        super(centerX, centerY, radius);
        setFill(Color.WHITE);
    }

    /**
     * Moves the ball on the game field based on its trajectory and speed.
     * The trajectory is considered to be the hypotenuse of a fictional triangle,
     * and the width and height of that triangle are determined below, then
     * added to our original X and Y coordinates to simulate ball movement.
     */
    public void move()
    {
        setCenterX(getCenterX() + BALL_SPEED * Math.cos(Math.toRadians((double) angle.getAngle())));
        setCenterY(getCenterY() + BALL_SPEED * -1 * Math.sin(Math.toRadians((double) angle.getAngle())));
    }
}