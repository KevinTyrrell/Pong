package components;

/**
 * Created by User on 3/21/2016.
 * Class which represents a given trajectory in a 2D plane.
 */
public class Trajectory
{
    /** The current angle of this trajectory object. */
    private short angle;
    /** The maximum amount of degrees of the unit circle. */
    private final short MAX_DEGREES = 360;

    /**
     * Constructs the trajectory object,
     * then rationalizes the angle just in case it is incorrect.
     * @param angle - Starting angle.
     */
    public Trajectory(short angle)
    {
        this.angle = angle;
        rationalizeAngle(angle);
    }

    /**
     * Fixes a given angle so it represents a location on the standard unit circle.
     * For example, 480 degrees would be rationalized as 120 degrees.
     */
    private void rationalizeAngle(short angle)
    {
        // Reduces any angle down to -359 to 359.
        if (Math.abs(angle) > MAX_DEGREES)
        {
            angle = (short) (angle % MAX_DEGREES);
        }

        // Corrects negative angles by going the opposite direction on the unit circle.
        this.angle = (angle < 0) ? (short) (MAX_DEGREES - Math.abs(angle)) : angle;
    }

    /** Changes the trajectory of this object. */
    public void changeAngle(short change)
    {
        angle = change;
        rationalizeAngle(angle);
    }

    public short getAngle() { return angle; }
}